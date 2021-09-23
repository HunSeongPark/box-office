package hunseong.com.box_office.screen.detail

import androidx.lifecycle.viewModelScope
import hunseong.com.box_office.data.local.dao.LikeMovieDao
import hunseong.com.box_office.data.local.entity.LikeMovieEntity
import hunseong.com.box_office.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailViewModel(
    private val likeMovieDao: LikeMovieDao
) : BaseViewModel() {

    fun insert(likeMovieEntity: LikeMovieEntity) = viewModelScope.launch {
        likeMovieDao.insert(likeMovieEntity)
    }

    fun delete(likeMovieEntity: LikeMovieEntity) = viewModelScope.launch {
        likeMovieDao.deleteLikeMovie(likeMovieEntity.id)
    }
}