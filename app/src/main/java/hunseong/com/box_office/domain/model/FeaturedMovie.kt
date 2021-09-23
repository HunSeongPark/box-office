package hunseong.com.box_office.domain.model

data class FeaturedMovie(
    val averageScore: String,
    val country: String,
    val director: String,
    val isFeatured: Boolean,
    val numberOfScore: Int,
    val posterUrl: String,
    val rating: String,
    val releaseYear: String,
    val runtime: String,
    val title: String,
    val actors: String,
    val description: String?,
    val id: String,
) {
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
