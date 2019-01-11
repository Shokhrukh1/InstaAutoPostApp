package tk.instaautopostapp.ui.post.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import cn.jzvd.Jzvd
import com.bumptech.glide.Glide
import tk.instaautopostapp.R
import tk.instaautopostapp.ui.base.view.BaseActivity
import tk.instaautopostapp.ui.post.presenter.PostMVPPresenter
import tk.instaautopostapp.util.AppConstants
import tk.instaautopostapp.util.extension.closeKeyboard
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.tbruyelle.rxpermissions2.RxPermissions
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.activity_post.*
import java.util.*
import javax.inject.Inject

class PostActivity : BaseActivity(), PostMVPView {

    @Inject
    lateinit var presenter: PostMVPPresenter<PostMVPView>
    @Inject
    lateinit var rxPermissions: RxPermissions
    var date: Calendar? = null
    var filePath: String? = null
    var originalFilePath: String? = null
    var isPhoto = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        showToolbar()
        setListeners()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.logout -> {
                presenter.logout()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (Jzvd.backPress())
            return

        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppConstants.REQUEST_CODE_IMAGE_PICKER && resultCode == Activity.RESULT_OK) {
            val data = PictureSelector.obtainMultipleResult(data)[0]

            if (PictureMimeType.isVideo(data.pictureType)) {
                isPhoto = false
                filePath = data.path
                showVideo(filePath!!)
            } else {
                isPhoto = true
                filePath = data.cutPath
                originalFilePath = data.path
                showImage(filePath!!)
            }
        }
    }

    private fun setListeners() {
        btnSelectFile.setOnClickListener {
            closeKeyboard()

            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    .subscribe { granted ->
                        if (granted) {
                            openGallery()
                        } else {
                            showToast(R.string.allow_read_access_to_files)
                        }
                    }
        }

        btnSelectTime.setOnClickListener {
            closeKeyboard()
            showDatePicker(Calendar.getInstance())
        }

        btnDone.setOnClickListener {
            closeKeyboard()

            if (filePath == null) {
                showToast(R.string.select_file)
                return@setOnClickListener
            }

            if (date == null) {
                showToast(R.string.set_time)
                return@setOnClickListener
            }

            if (isPhoto) {
                presenter.uploadPhoto(originalFilePath!!,
                        filePath!!,
                        etCaption.text.toString(),
                        date!!.timeInMillis)
            } else {
                presenter.uploadVideo(filePath!!,
                        etCaption.text.toString(),
                        date!!.timeInMillis)
            }
        }
    }

    private fun openGallery() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .imageSpanCount(3)
                .enableCrop(true)
                .circleDimmedLayer(false)
                .freeStyleCropEnabled(false)
                .showCropFrame(true)
                .showCropGrid(false)
                .scaleEnabled(true)
                .rotateEnabled(true)
                .hideBottomControls(false)
                .withAspectRatio(1, 1)
                .isCamera(true)
                .forResult(AppConstants.REQUEST_CODE_IMAGE_PICKER)
    }

    private fun showImage(path: String) {
        videoPlayer.visibility = View.GONE
        ivImage.visibility = View.VISIBLE

        Glide.with(this)
                .load(path)
                .into(ivImage)
    }

    private fun showVideo(path: String) {
        ivImage.visibility = View.GONE
        videoPlayer.visibility = View.VISIBLE
        videoPlayer.batteryLevel.visibility = View.GONE
        videoPlayer.fullscreenButton.visibility = View.GONE
        videoPlayer.setUp(path, null, Jzvd.SCREEN_WINDOW_NORMAL)
    }

    private fun showDatePicker(date: Calendar) {
        val dialog = DatePickerDialog.newInstance(
                { view, year, monthOfYear, dayOfMonth ->
                    date.set(Calendar.YEAR, year)
                    date.set(Calendar.MONTH, monthOfYear)
                    date.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    showTimePicker(date)
                },
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))

        dialog.minDate = date
        dialog.show(supportFragmentManager, "DatePickerDialog")
    }

    private fun showTimePicker(date: Calendar) {
        TimePickerDialog.newInstance(
                { view, hourOfDay, minute, second ->
                    date.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    date.set(Calendar.MINUTE, minute)
                    date.set(Calendar.SECOND, second)

                    this.date = date
                },
                date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE),
                true)
                .show(supportFragmentManager, "TimePickerDialog")
    }
}
