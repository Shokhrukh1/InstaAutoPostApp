package tk.instaautopostapp.data.preferences

interface PreferenceHelper {
    fun setUserId(id: Long)
    fun getUserId(): Long
    fun getUserName(): String
    fun setUserName(userName: String?)
    fun getPassword(): String
    fun setPassword(password: String?)
    fun isUserLoggedIn(): Boolean
    fun setUserLoggedIn(isUserLoggedIn: Boolean)
}