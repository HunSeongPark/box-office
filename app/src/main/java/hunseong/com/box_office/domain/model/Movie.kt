package hunseong.com.box_office.domain.model

import android.os.Parcelable
import hunseong.com.box_office.data.local.entity.LikeMovieEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
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
    val id: String,
) : Parcelable {
    fun toEntity() = LikeMovieEntity(
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
