package com.nech9ev.fifthelement.internal.data

import android.os.Build
import com.nech9ev.fifthelement.internal.domain.DeviceConfig
import java.util.*

internal object DeviceConfigUtils {

    fun getDeviceConfig() =
        DeviceConfig(
            id = getDeviceUid(),
            model = getDeviceModel()
        )

    private fun getDeviceUid(): String = UUID.randomUUID().toString()

    private fun getDeviceModel(): String {
        val manufacturer: String = Build.MANUFACTURER
        val model: String = Build.MODEL
        return if (model.lowercase(Locale.getDefault()).startsWith(manufacturer.lowercase(Locale.getDefault()))) {
            capitalizeDeviceModel(model)
        } else {
            capitalizeDeviceModel(manufacturer) + " " + model
        }
    }

    private fun capitalizeDeviceModel(s: String?): String {
        if (s == null || s.isEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            first.uppercaseChar().toString() + s.substring(1)
        }
    }
}