package hunseong.com.box_office.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import hunseong.com.box_office.domain.model.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class LikeMovieEntity(
    val averageScore: String?,
    val country: String?,
    val director: String?,
    val isFeatured: Boolean?,
    val numberOfScore: Int?,
    val posterUrl: String?,
    val rating: String?,
    val releaseYear: String?,
    val runtime: String?,
    val title: String?,
    val actors: String?,
    val description: String?,
    @PrimaryKey val id: String,
) : Parcelable {
    fun toMovie(): Movie = Movie(
        averageScore,
        country,
        director,
        isFeatured,
        numberOfScore,
        posterUrl,
        rating,
        releaseYear,
        runtime,
        title,
        actors,
        description,
        id
    )
}
