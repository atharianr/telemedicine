package com.atharianr.telemedicine.di

import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.network.ApiService
import com.atharianr.telemedicine.data.source.remote.network.FcmApiService
import com.atharianr.telemedicine.ui.landing.login.LoginViewModel
import com.atharianr.telemedicine.ui.landing.register.RegisterViewModel
import com.atharianr.telemedicine.ui.landing.verify.VerifyViewModel
import com.atharianr.telemedicine.ui.main.MainViewModel
import com.atharianr.telemedicine.ui.main.article.ArticleViewModel
import com.atharianr.telemedicine.ui.main.consultation.doctor.DoctorViewModel
import com.atharianr.telemedicine.ui.main.consultation.message.MessageViewModel
import com.atharianr.telemedicine.ui.main.consultation.message.chatroom.ChatViewModel
import com.atharianr.telemedicine.ui.main.home.HomeViewModel
import com.atharianr.telemedicine.ui.main.profile.InputProfileViewModel
import com.atharianr.telemedicine.ui.main.profile.ProfileViewModel
import com.atharianr.telemedicine.utils.Constant
import com.google.firebase.database.FirebaseDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.API_FCM_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(FcmApiService::class.java)
    }

    single {
        FirebaseDatabase.getInstance()
    }
}

val remoteDataSourceModule = module {
    factory { RemoteDataSource(get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { VerifyViewModel(get()) }
    viewModel { InputProfileViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { DoctorViewModel(get()) }
    viewModel { ArticleViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { ChatViewModel(get()) }
    viewModel { MessageViewModel(get()) }
}