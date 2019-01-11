package tk.instaautopostapp.ui.base.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tk.instaautopostapp.R
import tk.instaautopostapp.ui.login.view.LoginActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.progress_bar.*
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseActivity : AppCompatActivity(), MVPView {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun showProgress() {
        flProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        flProgressBar.visibility = View.GONE
    }

    override fun showToast(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun closeActivity() {
        finish()
    }

    override fun showToolbar() {
        setSupportActionBar(toolbar)
    }

    override fun openLoginActivity() {
        Intent(this, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(this)
        }
    }
}