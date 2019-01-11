package tk.instaautopostapp.ui.post.presenter

import tk.instaautopostapp.ui.base.presenter.MVPPresenter
import tk.instaautopostapp.ui.post.view.PostMVPView

interface PostMVPPresenter<V: PostMVPView> : MVPPresenter<V> {
    fun uploadPhoto(path: String, cutPath: String, caption: String, milliseconds: Long)
    fun uploadVideo(path: String, caption: String, milliseconds: Long)
}