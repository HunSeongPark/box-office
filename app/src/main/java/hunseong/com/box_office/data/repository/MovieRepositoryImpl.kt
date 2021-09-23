package hunseong.com.box_office.data.repository

import android.util.Log
import hunseong.com.box_office.data.network.MovieApiService
import hunseong.com.box_office.domain.model.Movie

class MovieRepositoryImpl(
    private val movieApiService: MovieApiService
) : MovieRepository {

    override suspend fun getMovies() : List<Movie> {
        val response = movieApiService.getMovies()
        if(response.isSuccessful) {
            return response.body()!!
        }
        return emptyList()
    }
}