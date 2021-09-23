package hunseong.com.box_office.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import hunseong.com.box_office.data.local.dao.LikeMovieDao
import hunseong.com.box_office.data.local.entity.LikeMovieEntity

@Database(
    entities = [LikeMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "AppDatabase.db"
    }

    abstract fun likeMovieDao() : LikeMovieDao
}