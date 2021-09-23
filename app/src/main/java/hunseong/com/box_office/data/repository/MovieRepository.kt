package hunseong.com.box_office.data.repository

import hunseong.com.box_office.domain.model.Movie

interface MovieRepository {

    suspend fun getMovies() : List<Movie>
}