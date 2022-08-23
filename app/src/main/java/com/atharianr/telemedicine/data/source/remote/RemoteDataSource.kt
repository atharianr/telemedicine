package com.atharianr.telemedicine.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atharianr.telemedicine.data.source.remote.network.ApiService
import com.atharianr.telemedicine.data.source.remote.request.InputProfileRequest
import com.atharianr.telemedicine.data.source.remote.request.LoginRequest
import com.atharianr.telemedicine.data.source.remote.request.RegisterRequest
import com.atharianr.telemedicine.data.source.remote.response.*
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(private val apiService: ApiService) {
    fun register(registerRequest: RegisterRequest): LiveData<ApiResponse<RegisterResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<RegisterResponse>>()

        apiService.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Register Success")
                    resultResponse.postValue(ApiResponse.success(response.body()))
                } else {
                    try {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val jObjError = JSONObject(errorBody.string())
                            Log.d(TAG, jObjError.getString("message"))
                            resultResponse.postValue(ApiResponse.error(jObjError.getString("message")))
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "${e.message}")
                        resultResponse.postValue(ApiResponse.error(e.message))
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                resultResponse.postValue(ApiResponse.error(t.message.toString()))
            }

        })

        return resultResponse
    }

    fun login(loginRequest: LoginRequest): LiveData<ApiResponse<LoginResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<LoginResponse>>()

        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Login Success")
                    resultResponse.postValue(ApiResponse.success(response.body()))
                } else {
                    try {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val jObjError = JSONObject(errorBody.string())
                            Log.d(TAG, jObjError.getString("message"))
                            resultResponse.postValue(ApiResponse.error(jObjError.getString("message")))
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "${e.message}")
                        resultResponse.postValue(ApiResponse.error(e.message))
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                resultResponse.postValue(ApiResponse.error(t.message.toString()))
            }

        })

        return resultResponse
    }

    fun verifyEmail(token: String): LiveData<ApiResponse<VerifyEmailResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<VerifyEmailResponse>>()

        apiService.verifyEmail(token).enqueue(object : Callback<VerifyEmailResponse> {
            override fun onResponse(
                call: Call<VerifyEmailResponse>,
                response: Response<VerifyEmailResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Verify Link Sent")
                    resultResponse.postValue(ApiResponse.success(response.body()))
                } else {
                    try {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val jObjError = JSONObject(errorBody.string())
                            Log.d(TAG, jObjError.getString("message"))
                            resultResponse.postValue(ApiResponse.error(jObjError.getString("message")))
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "${e.message}")
                        resultResponse.postValue(ApiResponse.error(e.message))
                    }
                }
            }

            override fun onFailure(call: Call<VerifyEmailResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                resultResponse.postValue(ApiResponse.error(t.message.toString()))
            }

        })

        return resultResponse
    }

    fun getUserDetail(token: String): LiveData<ApiResponse<UserResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<UserResponse>>()

        apiService.getUserDetail(token).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "User Detail Fetched")
                    resultResponse.postValue(ApiResponse.success(response.body()))
                } else {
                    try {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val jObjError = JSONObject(errorBody.string())
                            Log.d(TAG, jObjError.getString("message"))
                            resultResponse.postValue(ApiResponse.error(jObjError.getString("message")))
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "${e.message}")
                        resultResponse.postValue(ApiResponse.error(e.message))
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                resultResponse.postValue(ApiResponse.error(t.message.toString()))
            }
        })

        return resultResponse
    }

    fun inputProfile(
        token: String,
        inputProfileRequest: InputProfileRequest
    ): LiveData<ApiResponse<UserResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<UserResponse>>()

        apiService.inputProfile(token, inputProfileRequest)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "Input Success")
                        resultResponse.postValue(ApiResponse.success(response.body()))
                    } else {
                        try {
                            val errorBody = response.errorBody()
                            if (errorBody != null) {
                                val jObjError = JSONObject(errorBody.string())
                                Log.d(TAG, jObjError.getString("message"))
                                resultResponse.postValue(ApiResponse.error(jObjError.getString("message")))
                            }
                        } catch (e: Exception) {
                            Log.d(TAG, "${e.message}")
                            resultResponse.postValue(ApiResponse.error(e.message))
                        }
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d(TAG, t.message.toString())
                    resultResponse.postValue(ApiResponse.error(t.message.toString()))
                }
            })

        return resultResponse
    }

    fun getAllDoctors(): LiveData<ApiResponse<DoctorResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<DoctorResponse>>()

        apiService.getAllDoctors().enqueue(object : Callback<DoctorResponse> {
            override fun onResponse(
                call: Call<DoctorResponse>,
                response: Response<DoctorResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "All Doctors Fetched.")
                    resultResponse.postValue(ApiResponse.success(response.body()))
                } else {
                    try {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val jObjError = JSONObject(errorBody.string())
                            Log.d(TAG, jObjError.getString("message"))
                            resultResponse.postValue(ApiResponse.error(jObjError.getString("message")))
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "${e.message}")
                        resultResponse.postValue(ApiResponse.error(e.message))
                    }
                }
            }

            override fun onFailure(call: Call<DoctorResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                resultResponse.postValue(ApiResponse.error(t.message.toString()))
            }

        })

        return resultResponse
    }

    fun getSearchDoctors(keyword: String, filter: String): LiveData<ApiResponse<DoctorResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<DoctorResponse>>()

        apiService.getSearchDoctors(keyword, filter).enqueue(object : Callback<DoctorResponse> {
            override fun onResponse(
                call: Call<DoctorResponse>,
                response: Response<DoctorResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Search Doctors Fetched.")
                    resultResponse.postValue(ApiResponse.success(response.body()))
                } else {
                    try {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val jObjError = JSONObject(errorBody.string())
                            Log.d(TAG, jObjError.getString("message"))
                            resultResponse.postValue(ApiResponse.error(jObjError.getString("message")))
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "${e.message}")
                        resultResponse.postValue(ApiResponse.error(e.message))
                    }
                }
            }

            override fun onFailure(call: Call<DoctorResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                resultResponse.postValue(ApiResponse.error(t.message.toString()))
            }

        })

        return resultResponse
    }

    fun getDoctorDetail(doctorId: String): LiveData<ApiResponse<DoctorDetailResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<DoctorDetailResponse>>()

        apiService.getDoctorDetail(doctorId).enqueue(object : Callback<DoctorDetailResponse> {
            override fun onResponse(
                call: Call<DoctorDetailResponse>,
                response: Response<DoctorDetailResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "All Doctors Fetched.")
                    resultResponse.postValue(ApiResponse.success(response.body()))
                } else {
                    try {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val jObjError = JSONObject(errorBody.string())
                            Log.d(TAG, jObjError.getString("message"))
                            resultResponse.postValue(ApiResponse.error(jObjError.getString("message")))
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "${e.message}")
                        resultResponse.postValue(ApiResponse.error(e.message))
                    }
                }
            }

            override fun onFailure(call: Call<DoctorDetailResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                resultResponse.postValue(ApiResponse.error(t.message.toString()))
            }

        })

        return resultResponse
    }

    fun getAllArticle(): LiveData<ApiResponse<ArticleResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<ArticleResponse>>()

        apiService.getAllArticles().enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "All Articles Fetched.")
                    resultResponse.postValue(ApiResponse.success(response.body()))
                } else {
                    try {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val jObjError = JSONObject(errorBody.string())
                            Log.d(TAG, jObjError.getString("message"))
                            resultResponse.postValue(ApiResponse.error(jObjError.getString("message")))
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "${e.message}")
                        resultResponse.postValue(ApiResponse.error(e.message))
                    }
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                resultResponse.postValue(ApiResponse.error(t.message.toString()))
            }

        })

        return resultResponse
    }

    fun getSearchArticles(keyword: String): LiveData<ApiResponse<ArticleResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<ArticleResponse>>()

        apiService.getSearchArticles(keyword).enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Search Articles Fetched.")
                    resultResponse.postValue(ApiResponse.success(response.body()))
                } else {
                    try {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val jObjError = JSONObject(errorBody.string())
                            Log.d(TAG, jObjError.getString("message"))
                            resultResponse.postValue(ApiResponse.error(jObjError.getString("message")))
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "${e.message}")
                        resultResponse.postValue(ApiResponse.error(e.message))
                    }
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                resultResponse.postValue(ApiResponse.error(t.message.toString()))
            }

        })

        return resultResponse
    }

    companion object {
        private val TAG = RemoteDataSource::class.java.simpleName
    }
}