package com.fakhry.android_architechture.network

class ApiResponse<T>(val status: ResponseStatus, val body: T, val message: String?) {
    companion object {
        fun <T> success(body: T): ApiResponse<T> = ApiResponse(ResponseStatus.SUCCESS, body, null)

        fun <T> empty(msg: String, body: T): ApiResponse<T> = ApiResponse(ResponseStatus.EMPTY, body, msg)

        fun <T> error(msg: String, body: T): ApiResponse<T> = ApiResponse(ResponseStatus.ERROR, body, msg)
    }
}

enum class ResponseStatus {
    SUCCESS,
    EMPTY,
    ERROR
}
