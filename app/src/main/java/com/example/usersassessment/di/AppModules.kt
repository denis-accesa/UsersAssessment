package com.example.usersassessment.di

import androidx.room.Room
import com.example.usersassessment.data.db.AppDatabase
import com.example.usersassessment.data.network.api.UsersApi
import com.example.usersassessment.data.repository.UserRepository
import com.example.usersassessment.domain.usecase.FetchUsersUseCase
import com.example.usersassessment.domain.usecase.GetUsersByNickName
import com.example.usersassessment.domain.usecase.SaveAllUsersUseCase
import com.example.usersassessment.ui.screen.main.vm.MainViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.github.com/"
private const val API_TIMEOUT = 30L
private const val BEARER_TOKEN = "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"

val appModule = module {
    viewModelOf(::MainViewModel)
}

val networkModule = module {
    single<Interceptor> {
        Interceptor { chain ->
            val request: Request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $BEARER_TOKEN")
                .build()
            chain.proceed(request)
        }
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(get<Interceptor>())
            .build()
    }

    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .baseUrl(BASE_URL)
            .build()
    }

    single<UsersApi> {
        get<Retrofit>().create(UsersApi::class.java)
    }
}

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "user_db"
        ).build()
    }
    single {
        get<AppDatabase>().userDao()
    }

    singleOf(::UserRepository)
}

val useCasesModule = module {
    factoryOf(::FetchUsersUseCase)
    factoryOf(::SaveAllUsersUseCase)
    factoryOf(::GetUsersByNickName)
}