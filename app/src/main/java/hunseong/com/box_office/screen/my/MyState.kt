package hunseong.com.box_office.screen.my

import hunseong.com.box_office.data.local.entity.LikeMovieEntity

sealed class MyState {
    object Uninitialized : MyState()

    object Loading : MyState()

    data class Success(
        val likeMovies: List<LikeMovieEntity>,
    )  : MyState()

    object Error : MyState()
}
