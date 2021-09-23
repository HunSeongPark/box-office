package hunseong.com.box_office.data.network

import hunseong.com.box_office.domain.model.Movie
import retrofit2.Response
import retrofit2.http.GET

interface MovieApiService {

    @GET("api/v1/movies")
    suspend fun getMovies() : Response<List<Movie>>
}