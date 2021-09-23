package hunseong.com.box_office.screen.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hunseong.com.box_office.data.local.entity.LikeMovieEntity
import hunseong.com.box_office.databinding.ItemEmptyLikeBinding
import hunseong.com.box_office.databinding.ItemMyBinding
import hunseong.com.box_office.domain.model.Movie

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var likeMovies : List<LikeMovieEntity> = emptyList()

    var onClick : ((LikeMovieEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        return ViewHolder(ItemMyBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.bind(likeMovies[position])
    }

    override fun getItemCount(): Int = likeMovies.size

    inner class ViewHolder(private val binding: ItemMyBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: LikeMovieEntity) {

            binding.root.setOnClickListener {
                onClick?.invoke(movie)
            }

            Glide.with(binding.root)
                .load(movie.posterUrl)
                .into(binding.posterImageView)
        }
    }
}