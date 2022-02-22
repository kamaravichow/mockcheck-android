package me.aravi.mockcheck

import android.content.Context
import android.provider.Settings

object DevOpts {
    fun developerEnabled(context: Context): Boolean {
        return Settings.Secure.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
        ) != 0

    }
}