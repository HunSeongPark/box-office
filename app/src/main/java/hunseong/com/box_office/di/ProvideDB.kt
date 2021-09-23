package hunseong.com.box_office.di

import android.content.Context
import androidx.room.Room
import hunseong.com.box_office.data.local.ApplicationDatabase

fun provideDB(context: Context): ApplicationDatabase =
    Room.databaseBuilder(context, ApplicationDatabase::class.java, ApplicationDatabase.DB_NAME).build()

fun provideLikeMovieDao(database: ApplicationDatabase) = database.likeMovieDao()