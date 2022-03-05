package com.shifthackz.android.attacker.presentation.activity

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import com.shifthackz.android.attacker.R
import com.shifthackz.android.attacker.base.BaseActivity
import com.shifthackz.android.attacker.databinding.ActivityMainBinding
import com.shifthackz.android.attacker.extensions.observeNonNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId = R.layout.activity_main

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var wakeLock: PowerManager.WakeLock? = null
        viewModel.isRunning.observeNonNull(this) { isRunning ->
            when (isRunning) {
                true -> {
                    wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager)
                        .run {
                            newWakeLock(
                                PowerManager.PARTIAL_WAKE_LOCK,
                                "App::DDoSWakelockTag"
                            ).apply {
                                acquire(3600 * 60 * 1000L)
                            }
                        }
                }
                else -> wakeLock?.release()
            }
        }
    }
}