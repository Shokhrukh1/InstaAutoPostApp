package tk.instaautopostapp.ui.base.presenter

import tk.instaautopostapp.ui.base.view.MVPView

interface MVPPresenter<V: MVPView> {
    fun logout()
    fun onDestroy()
}