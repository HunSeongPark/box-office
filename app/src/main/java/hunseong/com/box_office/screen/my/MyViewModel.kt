package hunseong.com.box_office.screen.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hunseong.com.box_office.data.local.dao.LikeMovieDao
import hunseong.com.box_office.data.preference.PreferenceManager
import hunseong.com.box_office.screen.base.BaseViewModel
import hunseong.com.box_office.screen.home.HomeFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyViewModel(
    private val likeMovieDao: LikeMovieDao,
    private val preferenceManager: PreferenceManager
    ) : BaseViewModel() {

    private val _likeMoviesLiveData = MutableLiveData<MyState>(MyState.Uninitialized)
    val likeMoviesLiveData: LiveData<MyState> = _likeMoviesLiveData

    private val _nameLiveData = MutableLiveData<String?>(null)
    val nameLiveData: LiveData<String?> = _nameLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        _likeMoviesLiveData.postValue(MyState.Loading)
        try {
            val likeMovies = likeMovieDao.getAllLikeMovies()
            _likeMoviesLiveData.postValue(MyState.Success(likeMovies))
        } catch (e: Exception) {
            _likeMoviesLiveData.postValue(MyState.Error)
        }
    }

    fun fetchName(): Job = viewModelScope.launch {
        val userName = preferenceManager.getName(PREFERENCE_NAME_KEY)
        _nameLiveData.postValue(userName)
    }
}