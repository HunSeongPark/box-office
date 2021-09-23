package hunseong.com.box_office.screen.home

import hunseong.com.box_office.data.local.entity.LikeMovieEntity
import hunseong.com.box_office.domain.model.FeaturedMovie
import hunseong.com.box_office.domain.model.Movie

sealed class MovieState {
    object Uninitialized : MovieState()

    object Loading : MovieState()

    data class Success(
        val movies : List<Movie>,
        val featuredMovie : FeaturedMovie,
        val likeMovies : List<LikeMovieEntity>,
        val userName : String
    ) : MovieState()

    object Error : MovieState()
}
