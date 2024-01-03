package com.x.log

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat.getSystemService
import com.x.log.utils.timestampToDateTime
import java.util.Locale

data class HeaderInfo(
    val createTime: String = timestampToDateTime(System.currentTimeMillis()),
    val deviceBrand: String = Build.BOARD,
    val deviceOsVersion: Int = Build.VERSION.SDK_INT,
    val manufacturer: String = Build.MANUFACTURER,
    val language: String = Locale.getDefault().language,
) {
    override fun toString(): String {
        val headerStringBuffer = StringBuffer()
        headerStringBuffer.append("\n ----------------------------------------------- \n")
        headerStringBuffer.append("createTime $createTime \n")
        headerStringBuffer.append("deviceBrand $deviceBrand  \n")
        headerStringBuffer.append("deviceOsVersion $deviceOsVersion  \n")
        headerStringBuffer.append("manufacturer $manufacturer  \n")
        headerStringBuffer.append("language $language  \n")
        headerStringBuffer.append("-----------------------------------------------\n")
        return headerStringBuffer.toString()
    }
}
