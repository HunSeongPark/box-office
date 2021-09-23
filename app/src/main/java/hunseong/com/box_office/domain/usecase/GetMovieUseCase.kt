package hunseong.com.box_office.domain.usecase

import hunseong.com.box_office.data.repository.MovieRepository
import hunseong.com.box_office.domain.model.Movie

class GetMovieUseCase(
    private val movieRepository : MovieRepository
) {

    suspend operator fun invoke() : List<Movie> {
        return movieRepository.getMovies()
    }
}