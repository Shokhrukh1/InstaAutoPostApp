package tk.instaautopostapp.ui.taskList.view

import androidx.lifecycle.LiveData
import tk.instaautopostapp.data.database.repository.content.Content
import tk.instaautopostapp.ui.base.view.MVPView

interface TaskListMVPView : MVPView {
    fun showContents(contents: LiveData<List<Content>>)
}