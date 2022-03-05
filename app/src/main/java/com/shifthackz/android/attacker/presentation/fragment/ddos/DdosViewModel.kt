package com.shifthackz.android.attacker.presentation.fragment.ddos

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.shifthackz.android.attacker.attack.contract.AttackParam
import com.shifthackz.android.attacker.attack.contract.AttackProtocol
import com.shifthackz.android.attacker.attack.contract.AttackType
import com.shifthackz.android.attacker.attack.coordinator.AttackCoordinator
import com.shifthackz.android.attacker.base.BaseViewModel
import com.shifthackz.android.attacker.database.entity.LogEntity
import com.shifthackz.android.attacker.extensions.onPropertyChangedCallback
import com.shifthackz.android.attacker.logging.LogCollector
import com.shifthackz.android.attacker.preference.PreferenceManager
import com.shifthackz.android.attacker.utils.SingleLiveEvent
import com.shifthackz.android.attacker.validator.Validation
import com.shifthackz.android.attacker.validator.ValidationError
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class DdosViewModel(
    private val coordinator: AttackCoordinator,
    private val preference: PreferenceManager,
    private val log: LogCollector,
    private val validation: Validation
) : BaseViewModel() {

    val bindUiAttacks = ObservableField<List<String>>()
    val bindUiProtocols = ObservableField<List<String>>()

    val bindUiTarget = ObservableField<String>()
    val bindUiPort = ObservableField<String>()
    val bindUiThreads = ObservableField<String>()
    val bindUiAttack = ObservableField<AttackType>()
    val bindUiIsRunning = ObservableBoolean(false)

    val bindUiProtocol = ObservableField<AttackProtocol>()
    val bindUiTimeout = ObservableField<String>()
    val bindUiFloodMessage = ObservableField<String>()

    val bindUiPackets = ObservableField("0")
    val bindUiPps = ObservableField("0")

    val bindTargetError = ObservableField(ValidationError.NONE)
    val bindPortError = ObservableField(ValidationError.NONE)
    val bindThreadsError = ObservableField(ValidationError.NONE)
    val bindTimeoutError = ObservableField(ValidationError.NONE)

    val logsLiveData = MutableLiveData<List<LogEntity>>()
    val ppsLiveData = SingleLiveEvent<Int>()
    val scrollTopEvent = SingleLiveEvent<Unit>()

    private val targetCallback = onPropertyChangedCallback(bindUiTarget) { target ->
        validation.validate(
            param = AttackParam.TARGET,
            value = target,
            onValid = {
                coordinator.setTarget(target)
                preference.target = target
                bindTargetError.set(ValidationError.NONE)
            },
            onError = bindTargetError::set
        )
    }

    private val portCallback = onPropertyChangedCallback(bindUiPort) { port ->
        validation.validate(
            param = AttackParam.PORT,
            value = port,
            onValid = {
                coordinator.setPort(port)
                preference.port = port
                bindPortError.set(ValidationError.NONE)
            },
            onError = bindPortError::set
        )
    }

    private val threadsCallback = onPropertyChangedCallback(bindUiThreads) { threads ->
        validation.validate(
            param = AttackParam.THREADS,
            value = threads,
            onValid = {
                threads.toIntOrNull()?.let {
                    coordinator.setThreadsCount(it)
                    preference.threads = "$it"
                }
                bindThreadsError.set(ValidationError.NONE)
            },
            onError = bindThreadsError::set
        )
    }

    private val timeoutCallback = onPropertyChangedCallback(bindUiTimeout) { tm ->
        validation.validate(
            param = AttackParam.TIMEOUT,
            value = tm,
            onValid = {
                tm.toLongOrNull()?.let {
                    coordinator.setConnectTimeout(it)
                    preference.timeout = it
                }
                bindTimeoutError.set(ValidationError.NONE)
            },
            onError = bindTimeoutError::set
        )
    }

    private val floodMessageCallback = onPropertyChangedCallback(bindUiFloodMessage) { msg ->
        coordinator.setFloodMessage(msg)
        preference.floodMessage = msg
    }

    init {
        proceedPreferenceLoad()
        loadChangeCallbacks()
        logsLiveData.value = mutableListOf()

        !log.observePackets()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ count ->
                bindUiPackets.set("$count")
            }, Throwable::printStackTrace)

        !log.observePps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ pps ->
                bindUiPps.set("$pps")
                ppsLiveData.value = pps
            }, Throwable::printStackTrace)

        !log.observeLog()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ entry ->
                logsLiveData.postValue(ArrayList(entry))
                scrollTopEvent.value = Unit
            }, Throwable::printStackTrace)

        !coordinator.observeState()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                bindUiIsRunning.set(it)
            }, Throwable::printStackTrace)
    }

    override fun onCleared() {
        unloadChangeCallbacks()
        super.onCleared()
    }

    fun start() {
        val isValid = bindTargetError.get() == ValidationError.NONE
                && bindPortError.get() == ValidationError.NONE
                && bindThreadsError.get() == ValidationError.NONE
                && bindTimeoutError.get() == ValidationError.NONE
        if (isValid) !log.reset()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ attack() }, Throwable::printStackTrace)
    }

    fun stop() {
        coordinator.stop()
        scrollTopEvent.postValue(Unit)
    }

    fun setAttackType(value: AttackType) {
        bindUiAttack.set(value)
        coordinator.setType(value)
        preference.attack = value.attackName
    }

    fun setProtocol(value: AttackProtocol) {
        bindUiProtocol.set(value)
        coordinator.setProtocol(value)
        preference.protocol = value.protocol
    }

    private fun attack() = with(coordinator) {
        start()
        !attack().subscribe({ /*println("$it")*/ }, Throwable::printStackTrace)
    }

    private fun proceedPreferenceLoad() {
        bindUiAttacks.set(AttackType.values().map { it.attackName })
        setAttackType(AttackType.byName(preference.attack))
        bindUiProtocols.set(AttackProtocol.values().map { it.protocol })
        setProtocol(AttackProtocol.byName(preference.protocol))
        bindUiTarget.set(preference.target)
        bindUiPort.set(preference.port)
        bindUiThreads.set(preference.threads)
        bindUiTimeout.set("${preference.timeout}")
        bindUiFloodMessage.set(preference.floodMessage)
    }

    private fun loadChangeCallbacks() {
        bindUiTarget.addOnPropertyChangedCallback(targetCallback)
        bindUiPort.addOnPropertyChangedCallback(portCallback)
        bindUiThreads.addOnPropertyChangedCallback(threadsCallback)
        bindUiTimeout.addOnPropertyChangedCallback(timeoutCallback)
        bindUiFloodMessage.addOnPropertyChangedCallback(floodMessageCallback)
    }

    private fun unloadChangeCallbacks() {
        bindUiTarget.removeOnPropertyChangedCallback(targetCallback)
        bindUiPort.removeOnPropertyChangedCallback(portCallback)
        bindUiThreads.removeOnPropertyChangedCallback(threadsCallback)
        bindUiTimeout.removeOnPropertyChangedCallback(timeoutCallback)
        bindUiFloodMessage.removeOnPropertyChangedCallback(floodMessageCallback)
    }
}