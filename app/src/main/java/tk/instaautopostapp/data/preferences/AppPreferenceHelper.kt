package tk.instaautopostapp.data.preferences

import android.content.Context
import tk.instaautopostapp.di.PreferenceInfo
import javax.inject.Inject

class AppPreferenceHelper @Inject constructor(context: Context, @PreferenceInfo prefFileName: String) : PreferenceHelper {
    companion object {
        private const val PREF_KEY_USER_ID = "PREF_KEY_USER_ID"
        private const val PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME"
        private const val PREF_KEY_PASSWORD = "PREF_KEY_PASSWORD"
        private const val PREF_IS_USER_LOGGED_IN = "PREF_IS_USER_LOGGED_IN"
    }

    private val prefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun setUserId(id: Long) {
        prefs.edit().putLong(PREF_KEY_USER_ID, id).apply()
    }

    override fun getUserId() = prefs.getLong(PREF_KEY_USER_ID, -1)

    override fun getUserName() = prefs.getString(PREF_KEY_USER_NAME, "")

    override fun setUserName(userName: String?) {
        prefs.edit().putString(PREF_KEY_USER_NAME, userName).apply()
    }

    override fun getPassword() = prefs.getString(PREF_KEY_PASSWORD, "")

    override fun setPassword(password: String?) {
        prefs.edit().putString(PREF_KEY_PASSWORD, password).apply()
    }

    override fun isUserLoggedIn() = prefs.getBoolean(PREF_IS_USER_LOGGED_IN, false)

    override fun setUserLoggedIn(isUserLoggedIn: Boolean) {
        prefs.edit().putBoolean(PREF_IS_USER_LOGGED_IN, isUserLoggedIn).apply()
    }
}