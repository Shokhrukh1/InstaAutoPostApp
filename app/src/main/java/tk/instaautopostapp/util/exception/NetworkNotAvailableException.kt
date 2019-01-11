package com.quiztestapp.util.exception

class NetworkNotAvailableException : Throwable () {
    override val message: String?
        get() = "Network not available"
}