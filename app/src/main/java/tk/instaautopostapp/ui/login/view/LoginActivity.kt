package tk.instaautopostapp.ui.login.view

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import tk.instaautopostapp.R
import tk.instaautopostapp.ui.base.view.BaseActivity
import tk.instaautopostapp.ui.login.presenter.LoginMVPPresenter
import tk.instaautopostapp.ui.taskList.view.TaskListActivity
import tk.instaautopostapp.util.extension.closeKeyboard
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginMVPView, Validator.ValidationListener {

    @Inject
    lateinit var presenter: LoginMVPPresenter<LoginMVPView>
    @NotEmpty
    @BindView(R.id.etLogin)
    lateinit var etLogin: EditText
    @NotEmpty
    @BindView(R.id.etPassword)
    lateinit var etPassword: EditText
    lateinit var validator: Validator
    lateinit var unbinder: Unbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        unbinder = ButterKnife.bind(this)

        init()
        setListeners()
        presenter.checkUserLoggedIn()
    }

    override fun onDestroy() {
        unbinder.unbind()
        super.onDestroy()
    }

    override fun openTaskListActivity() {
        Intent(this, TaskListActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(this)
        }
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        for (error in errors!!.iterator()) {
            val view = error.view
            val message = error.getCollatedErrorMessage(this)

            if (view is EditText) {
                when (view.id) {
                    R.id.etLogin -> view.error = message
                    R.id.etPassword -> view.error = message
                }
            }
        }
    }

    override fun onValidationSucceeded() {
        presenter.login(etLogin.text.toString(), etPassword.text.toString())
    }

    private fun init() {
        validator = Validator(this)
        validator.setValidationListener(this)
    }

    private fun setListeners() {
        etPassword.setOnEditorActionListener { _, actionId, _ ->
            closeKeyboard()

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                validator.validate()
                true
            } else {
                false
            }
        }

        btnLogin.setOnClickListener {
            closeKeyboard()
            validator.validate()
        }
    }
}
