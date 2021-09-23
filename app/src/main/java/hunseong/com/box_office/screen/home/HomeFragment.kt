package hunseong.com.box_office.screen.home

import android.content.Intent
import android.util.Log
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hunseong.com.box_office.databinding.FragmentHomeBinding
import hunseong.com.box_office.domain.model.Movie
import hunseong.com.box_office.screen.base.BaseFragment
import hunseong.com.box_office.screen.detail.DetailActivity
import hunseong.com.box_office.screen.home.HomeAdapter.Companion.TYPE_EMPTY_LIKE
import hunseong.com.box_office.screen.home.HomeAdapter.Companion.TYPE_FEATURED
import hunseong.com.box_office.screen.home.HomeAdapter.Companion.TYPE_HEADER
import hunseong.com.box_office.screen.home.HomeAdapter.Companion.TYPE_LIKE
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private val adapter: HomeAdapter by lazy {
        HomeAdapter()
    }

    override fun initViews() = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = recyclerView.createGridLayoutManager()
    }

    override fun observeData() = viewModel.movieLiveData.observe(viewLifecycleOwner) {
        when(it) {
            is MovieState.Uninitialized -> Unit
            is MovieState.Loading -> handleLoadingState()
            is MovieState.Success -> handleSuccessState(it)
            is MovieState.Error -> handleErrorState()
        }
    }

    private fun handleLoadingState() = with(binding) {
        errorTextView.isGone = true
        recyclerView.isVisible = true
        progressBar.isVisible = true
    }

    private fun handleSuccessState(state: MovieState.Success) = with(binding) {
        errorTextView.isGone = true
        progressBar.isGone = true
        recyclerView.isVisible = true

        (recyclerView.adapter as HomeAdapter).apply {
            setData(state.featuredMovie, state.movies, state.likeMovies, state.userName)
            onClickMovie = {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                val isLiked = !state.likeMovies.filter { likeMovie ->
                    likeMovie.id == it.id
                }.isNullOrEmpty()

                intent.putExtra(INTENT_MOVIE, it)
                intent.putExtra(INTENT_LIKE, isLiked)
                startActivityForResult(intent, REQUEST_CODE)
            }
            onClickFeaturedMovie = {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                val isLiked = !state.likeMovies.filter { likeMovie ->
                    likeMovie.id == it.id
                }.isNullOrEmpty()
                intent.putExtra(INTENT_MOVIE, it.toMovie())
                intent.putExtra(INTENT_LIKE, isLiked)
                startActivityForResult(intent, REQUEST_CODE)
            }
            onClickLike = {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(INTENT_MOVIE, it.toMovie())
                intent.putExtra(INTENT_LIKE, true)
                startActivityForResult(intent, REQUEST_CODE)
            }
            notifyDataSetChanged()
        }
    }

    private fun handleErrorState() = with(binding) {
        recyclerView.isGone = true
        progressBar.isGone = true
        errorTextView.isVisible = true
    }

    private fun RecyclerView.createGridLayoutManager(): GridLayoutManager =
        GridLayoutManager(context, 3, RecyclerView.VERTICAL, false).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    when (adapter?.getItemViewType(position)) {
                        TYPE_HEADER, TYPE_FEATURED, TYPE_LIKE, TYPE_EMPTY_LIKE -> spanCount
                        else -> 1
                    }
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.fetchData()
    }

    companion object {
        fun newInstance() : HomeFragment = HomeFragment()
        const val TAG = "homeFragment"
        const val INTENT_MOVIE = "intent_movie"
        const val INTENT_LIKE = "intent_like"
        const val REQUEST_CODE = 100
    }
}