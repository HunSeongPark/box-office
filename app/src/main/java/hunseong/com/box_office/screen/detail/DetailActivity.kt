package hunseong.com.box_office.screen.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.view.MenuItem
import com.bumptech.glide.Glide
import hunseong.com.box_office.R
import hunseong.com.box_office.databinding.ActivityDetailBinding
import hunseong.com.box_office.domain.model.Movie
import hunseong.com.box_office.extension.personFormat
import hunseong.com.box_office.screen.base.BaseActivity
import hunseong.com.box_office.screen.home.HomeFragment
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<DetailViewModel, ActivityDetailBinding>() {

    override val viewModel by viewModel<DetailViewModel>()

    override fun getViewBinding(): ActivityDetailBinding =
        ActivityDetailBinding.inflate(layoutInflater)

    private var isLiked = false

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun initViews() {
        overridePendingTransition(R.anim.left_enter, R.anim.left_exit)
        val movie = intent.getParcelableExtra<Movie>(HomeFragment.INTENT_MOVIE)
        isLiked = intent.getBooleanExtra(HomeFragment.INTENT_LIKE, false)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }

        binding.toolbarBackButton.setOnClickListener {
            finish()
        }

        binding.toolbarTitleTextView.text = movie?.title ?: "Unknown"

        Glide.with(binding.root)
            .load(movie?.posterUrl)
            .into(binding.posterImageView)

        binding.titleTextView.text = movie?.title
        binding.countryTextView.text = "${movie?.country} ' ${movie?.rating} / ${movie?.runtime}분}"
        binding.directorTextView.text = "감독 : ${movie?.director}"
        binding.actorTextView.text = "출연 : ${movie?.actors}"
        binding.releaseTextView.text = movie?.releaseYear
        binding.ratingTextView.text = movie?.averageScore?.format("0.0")
        binding.ratingBar.rating = movie?.averageScore?.toFloat()!!
        binding.numberOfPersonTextView.text = movie.numberOfScore?.personFormat()

        if(isLiked) {
            binding.likeButton.setBackgroundResource(R.drawable.like)
        } else {
            binding.likeButton.setBackgroundResource(R.drawable.empty_like)

        }

        binding.likeButton.setOnClickListener {
            if(isLiked) {
                it.setBackgroundResource(R.drawable.empty_like)
                viewModel.delete(movie.toEntity())
            } else {
                it.setBackgroundResource(R.drawable.like)
                viewModel.insert(movie.toEntity())
            }

            isLiked = !isLiked
        }
    }

    override fun observeData() = Unit

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_enter, R.anim.right_exit)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_enter, R.anim.right_exit)
    }
}