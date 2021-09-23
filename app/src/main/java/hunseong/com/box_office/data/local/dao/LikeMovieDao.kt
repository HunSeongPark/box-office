package hunseong.com.box_office.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hunseong.com.box_office.data.local.entity.LikeMovieEntity

@Dao
interface LikeMovieDao {

    @Query("SELECT * FROM LikeMovieEntity")
    suspend fun getAllLikeMovies() : List<LikeMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(likeMovieEntity: LikeMovieEntity)

    @Query("DELETE FROM LikeMovieEntity WHERE id=:id")
    suspend fun deleteLikeMovie(id: String)
}