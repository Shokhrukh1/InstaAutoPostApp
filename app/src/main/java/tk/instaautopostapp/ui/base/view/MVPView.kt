package tk.instaautopostapp.ui.base.view

import androidx.annotation.StringRes

interface MVPView {
    fun showProgress()
    fun hideProgress()
    fun showToast(@StringRes message: Int)
    fun showToast(message: String)
    fun showToolbar()
    fun closeActivity()
    fun openLoginActivity()
}