package tk.instaautopostapp.ui.login.presenter

import tk.instaautopostapp.ui.base.presenter.MVPPresenter
import tk.instaautopostapp.ui.login.view.LoginMVPView

interface LoginMVPPresenter<V: LoginMVPView> : MVPPresenter<V> {
    fun checkUserLoggedIn()
    fun login(userName: String, password: String)
}