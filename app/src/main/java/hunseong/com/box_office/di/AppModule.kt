package hunseong.com.box_office.di

import android.content.Context
import hunseong.com.box_office.data.preference.PreferenceManager
import hunseong.com.box_office.data.preference.SharedPreferenceManager
import hunseong.com.box_office.data.repository.MovieRepository
import hunseong.com.box_office.data.repository.MovieRepositoryImpl
import hunseong.com.box_office.domain.usecase.GetMovieUseCase
import hunseong.com.box_office.screen.detail.DetailViewModel
import hunseong.com.box_office.screen.my.MyViewModel
import hunseong.com.box_office.screen.home.HomeViewModel
import hunseong.com.box_office.screen.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //Dispatcher
    single { Dispatchers.IO }

    //ViewModel
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { MyViewModel(get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }

    //Api
    single { provideGsonConverterFactory() }
    single { provideOkHttpClient() }
    single { provideMovieRetrofit(get(), get()) }
    single { provideMovieApiService(get()) }

    //DB
    single { provideDB(androidContext()) }
    single { provideLikeMovieDao(get()) }

    //UseCase
    factory { GetMovieUseCase(get()) }

    //Repository
    single<MovieRepository> { MovieRepositoryImpl(get()) }

    //Preference
    single { androidContext().getSharedPreferences("Profile",Context.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get()) }
}