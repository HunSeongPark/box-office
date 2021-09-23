package hunseong.com.box_office.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hunseong.com.box_office.data.local.entity.LikeMovieEntity
import hunseong.com.box_office.databinding.*
import hunseong.com.box_office.domain.model.FeaturedMovie
import hunseong.com.box_office.domain.model.Movie

class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<HomeData> = emptyList()
    var onClickMovie: ((Movie) -> Unit)? = null
    var onClickFeaturedMovie : ((FeaturedMovie) -> Unit)? = null
    var onClickLike: ((LikeMovieEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(ItemHeaderBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
            TYPE_FEATURED -> FeaturedViewHolder(ItemFeaturedBinding.inflate(LayoutInflater.from(
                parent.context), parent, false))
            TYPE_LIKE -> LikeViewHolder(ItemRecyclerLikeBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
            TYPE_EMPTY_LIKE -> EmptyLikeViewHolder(ItemEmptyLikeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            else -> NormalViewHolder(ItemMoviesBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            (holder is HeaderViewHolder && data[position].value is String) -> holder.bind(data[position].value as String)
            (holder is FeaturedViewHolder && data[position].value is FeaturedMovie) -> holder.bind(
                data[position].value as FeaturedMovie)
            (holder is NormalViewHolder && data[position].value is Movie) -> holder.bind(data[position].value as Movie)
            (holder is LikeViewHolder) -> holder.bind(data[position].values!!)
            else -> Unit
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return when (data[position].value) {
            is String -> TYPE_HEADER
            is FeaturedMovie -> TYPE_FEATURED
            is Movie -> TYPE_NORMAL
            else -> {
                if(data[position].values.isNullOrEmpty()) {
                    TYPE_EMPTY_LIKE
                } else TYPE_LIKE
            }
        }
    }

    inner class HeaderViewHolder(private val binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(header: String) {
            binding.title.text = header
        }
    }

    inner class FeaturedViewHolder(private val binding: ItemFeaturedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(featuredMovie: FeaturedMovie) {
            binding.root.setOnClickListener {
                onClickFeaturedMovie?.invoke(featuredMovie)
            }
            Glide.with(binding.root)
                .load(featuredMovie.posterUrl)
                .into(binding.featuredMovieImageView)
            binding.titleTextView.text = featuredMovie.title
            binding.detailTextView.text = "${featuredMovie.country} ' ${featuredMovie.releaseYear}"
        }
    }

    inner class NormalViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.root.setOnClickListener {
                onClickMovie?.invoke(movie)
            }
            Glide.with(binding.root)
                .load(movie.posterUrl)
                .into(binding.posterImageView)

            binding.titleTextView.text = movie.title
            binding.detailTextView.text = "${movie.country} ' ${movie.releaseYear}"
        }
    }

    inner class LikeViewHolder(private val binding: ItemRecyclerLikeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.recyclerView.apply {
                adapter = LikeAdapter()
                layoutManager =
                    LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
            }
        }

        fun bind(likeMovies: List<LikeMovieEntity>) {
            binding.emptyLikeTextView.isGone = true
            binding.recyclerView.isVisible = true
            (binding.recyclerView.adapter as LikeAdapter).apply {
                addLikeMovie(likeMovies)
                notifyDataSetChanged()
                onClickLikeMovie = onClickLike
            }
        }
    }

    inner class EmptyLikeViewHolder(private val binding: ItemEmptyLikeBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    fun setData(
        featuredMovie: FeaturedMovie,
        movies: List<Movie>,
        likeMovies: List<LikeMovieEntity>,
        userName: String,
    ) {
        val newData = mutableListOf<HomeData>()

        newData += HomeData("오늘의 추천 영화 🍿", null)
        newData += HomeData(featuredMovie, null)

        newData += HomeData("낭만파 감독들의 신작! 🔥", null)
        movies.map {
            newData += HomeData(it, null)
        }

        newData += HomeData("${userName}님이 찜한 영화 🌟", null)
        newData += HomeData(null, likeMovies)
        data = newData

    }

    data class HomeData(val value: Any?, val values: List<LikeMovieEntity>?)

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_FEATURED = 1
        const val TYPE_NORMAL = 2
        const val TYPE_LIKE = 3
        const val TYPE_EMPTY_LIKE = 4
    }
}