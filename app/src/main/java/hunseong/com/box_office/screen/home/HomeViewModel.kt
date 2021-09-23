package hunseong.com.box_office.screen.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hunseong.com.box_office.data.local.dao.LikeMovieDao
import hunseong.com.box_office.data.local.entity.LikeMovieEntity
import hunseong.com.box_office.data.preference.PreferenceManager
import hunseong.com.box_office.domain.model.FeaturedMovie
import hunseong.com.box_office.domain.model.Movie
import hunseong.com.box_office.domain.usecase.GetMovieUseCase
import hunseong.com.box_office.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(
    private val preference: PreferenceManager,
    private val getMovieUseCase: GetMovieUseCase,
    private val likeMovieDao: LikeMovieDao,
) : BaseViewModel() {

    private val _movieLiveData = MutableLiveData<MovieState>(MovieState.Uninitialized)
    val movieLiveData: LiveData<MovieState> = _movieLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        _movieLiveData.postValue(MovieState.Loading)
        try {
            val userName = preference.getName(PREFERENCE_NAME_KEY)
            val movies = getMovieUseCase()
            val randomFeaturedMovie = movies.filter {
                it.isFeatured == true
            }.random()
            val filterMovies = movies.filter {
                it.id != randomFeaturedMovie.id
            }

            val featuredMovie = FeaturedMovie(
                averageScore = randomFeaturedMovie.averageScore!!,
                country = randomFeaturedMovie.country!!,
                director = randomFeaturedMovie.director!!,
                isFeatured = randomFeaturedMovie.isFeatured!!,
                numberOfScore = randomFeaturedMovie.numberOfScore!!,
                posterUrl = randomFeaturedMovie.posterUrl!!,
                rating = randomFeaturedMovie.rating!!,
                releaseYear = randomFeaturedMovie.releaseYear!!,
                runtime = randomFeaturedMovie.runtime!!,
                title = randomFeaturedMovie.title!!,
                actors = randomFeaturedMovie.actors!!,
                description = randomFeaturedMovie.description!!,
                id = randomFeaturedMovie.id,
            )

            val likeMovies = likeMovieDao.getAllLikeMovies()

            _movieLiveData.postValue(MovieState.Success(filterMovies,
                featuredMovie,
                likeMovies,
                userName!!))
        } catch (e: Exception) {
            e.printStackTrace()
            _movieLiveData.postValue(MovieState.Error)
        }
    }

    fun insert(likeMovieEntity: LikeMovieEntity) = viewModelScope.launch {
        likeMovieDao.insert(likeMovieEntity)
    }
}