package tk.instaautopostapp.ui.taskList.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_task_list.*
import tk.instaautopostapp.R
import tk.instaautopostapp.data.database.repository.content.Content
import tk.instaautopostapp.ui.base.view.BaseActivity
import tk.instaautopostapp.ui.post.view.PostActivity
import tk.instaautopostapp.ui.taskList.adapter.ContentAdapter
import tk.instaautopostapp.ui.taskList.presenter.TaskListMVPPresenter
import javax.inject.Inject

class TaskListActivity : BaseActivity(), TaskListMVPView {

    @Inject
    lateinit var presenter: TaskListMVPPresenter<TaskListMVPView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        init()
        setListeners()
        presenter.getContents()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.logout -> {
                presenter.logout()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showContents(contents: LiveData<List<Content>>) {
        contents.observe(this,
            Observer<List<Content>> { contents ->
                rvContents.adapter = ContentAdapter(contents)
            })
    }

    private fun init() {
        showToolbar()
        rvContents.layoutManager = LinearLayoutManager(this)
    }

    private fun setListeners() {
        fab.setOnClickListener {
            startActivity(Intent(this@TaskListActivity, PostActivity::class.java))
        }
    }
}
