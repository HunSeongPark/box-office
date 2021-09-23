package hunseong.com.box_office.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hunseong.com.box_office.data.local.entity.LikeMovieEntity
import hunseong.com.box_office.databinding.ItemLikeBinding

class LikeAdapter : RecyclerView.Adapter<LikeAdapter.ViewHolder>() {

    var likeMovies: List<LikeMovieEntity> = emptyList()
    var onClickLikeMovie: ((LikeMovieEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeAdapter.ViewHolder {
        return ViewHolder(ItemLikeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: LikeAdapter.ViewHolder, position: Int) {
        holder.bind(likeMovies[position])
    }

    override fun getItemCount(): Int = likeMovies.size

    inner class ViewHolder(private val binding: ItemLikeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: LikeMovieEntity) {
            binding.root.setOnClickListener {
                onClickLikeMovie?.invoke(movie)
            }
            Glide.with(binding.root)
                .load(movie.posterUrl)
                .into(binding.posterImageView)
        }
    }

    fun addLikeMovie(likeMovieEntity: List<LikeMovieEntity>) {
        likeMovies = likeMovieEntity
        notifyDataSetChanged()
    }
}