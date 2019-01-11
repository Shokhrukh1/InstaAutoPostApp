package tk.instaautopostapp.util

object AppConstants {
    const val APP_DB_NAME = "insta_db"
    const val PREF_NAME = "insta_pref"
    const val NULL_INDEX = -1L
    const val RC_SIGN_IN = 9001

    const val REQUEST_CODE_IMAGE_PICKER = 1

    const val STATUS_OK = "ok"

    enum class ContentType(val type: Int) {
        IMAGE(0),
        VIDEO(1)
    }

    enum class UploadStatus(val status: Int) {
        NONE(0),
        FAILED(1),
        SUCCESSED(2)
    }
}