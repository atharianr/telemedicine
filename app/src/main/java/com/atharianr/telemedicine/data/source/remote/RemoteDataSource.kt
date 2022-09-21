package com.atharianr.telemedicine.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atharianr.telemedicine.data.source.remote.network.ApiService
import com.atharianr.telemedicine.data.source.remote.request.InputProfileRequest
import com.atharianr.telemedicine.data.source.remote.request.LoginRequest
import com.atharianr.telemedicine.data.source.remote.request.RegisterRequest
import com.atharianr.telemedicine.data.source.remote.request.firebase.Chat
import com.atharianr.telemedicine.data.source.remote.response.*
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse
import com.atharianr.telemedicine.utils.Constant
import com.atharianr.telemedicine.utils.getCurrentTimeStamp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(
    private val apiService: ApiService,
    private val firebaseDatabase: FirebaseDatabase
) {
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

    fun getAllDoctors(token: String): LiveData<ApiResponse<DoctorResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<DoctorResponse>>()

        apiService.getAllDoctors(token).enqueue(object : Callback<DoctorResponse> {
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

    fun getSearchDoctors(
        token: String,
        keyword: String,
        filter: String
    ): LiveData<ApiResponse<DoctorResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<DoctorResponse>>()

        apiService.getSearchDoctors(token, keyword, filter)
            .enqueue(object : Callback<DoctorResponse> {
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

    fun getDoctorDetail(
        token: String,
        doctorId: String
    ): LiveData<ApiResponse<DoctorDetailResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<DoctorDetailResponse>>()

        apiService.getDoctorDetail(token, doctorId)
            .enqueue(object : Callback<DoctorDetailResponse> {
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

    fun getAllArticle(token: String): LiveData<ApiResponse<ArticleResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<ArticleResponse>>()

        apiService.getAllArticles(token).enqueue(object : Callback<ArticleResponse> {
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

    fun getSearchArticles(token: String, keyword: String): LiveData<ApiResponse<ArticleResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<ArticleResponse>>()

        apiService.getSearchArticles(token, keyword).enqueue(object : Callback<ArticleResponse> {
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

    fun getArticleDetail(
        token: String,
        articleId: String
    ): LiveData<ApiResponse<ArticleDetailResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<ArticleDetailResponse>>()

        apiService.getArticleDetail(token, articleId)
            .enqueue(object : Callback<ArticleDetailResponse> {
                override fun onResponse(
                    call: Call<ArticleDetailResponse>,
                    response: Response<ArticleDetailResponse>
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

                override fun onFailure(call: Call<ArticleDetailResponse>, t: Throwable) {
                    Log.d(TAG, t.message.toString())
                    resultResponse.postValue(ApiResponse.error(t.message.toString()))
                }

            })

        return resultResponse
    }

    fun createChatRoom(doctorId: String, userId: String) {
        firebaseDatabase.getReference(Constant.CHATROOM).child("$doctorId-$userId").apply {
            child(Constant.DOCTOR_ID).setValue(doctorId)
            child(Constant.USER_ID).setValue(userId)
            child(Constant.CHATROOM).setValue(null)
        }
    }

    fun sendChat(doctorId: String, userId: String, chatBody: String) {
        firebaseDatabase.getReference(Constant.CHATROOM)
            .child("$doctorId-$userId")
            .child(Constant.CHAT)
            .apply {
                val chatId = push().key
                chatId?.let {
                    val message = Chat("user", chatBody, getCurrentTimeStamp())
                    child(it).setValue(message)
                }
            }
    }

    fun getChat(doctorId: String, userId: String): LiveData<ApiResponse<List<Chat>>> {
        val resultResponse = MutableLiveData<ApiResponse<List<Chat>>>()

        firebaseDatabase.getReference(Constant.CHATROOM).child("$doctorId-$userId").child("chat")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d(TAG, snapshot.value.toString())
                    val list = mutableListOf<Chat>()
                    for (d in snapshot.children) {
                        val data = d.getValue(Chat::class.java)
                        data?.let { list.add(it) }
                    }
                    Log.d(TAG, list.toString())
                    resultResponse.postValue(ApiResponse.success(list))
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "loadPost:onCancelled", error.toException())
                    resultResponse.postValue(ApiResponse.error(error.toException().message))
                }

            })

        return resultResponse
    }

    companion object {
        private val TAG = RemoteDataSource::class.java.simpleName
    }
}