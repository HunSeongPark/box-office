package hunseong.com.box_office.screen.my

import android.content.Intent
import android.util.Log
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hunseong.com.box_office.databinding.FragmentMyBinding
import hunseong.com.box_office.screen.base.BaseFragment
import hunseong.com.box_office.screen.detail.DetailActivity
import hunseong.com.box_office.screen.home.HomeAdapter
import hunseong.com.box_office.screen.home.HomeFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MyFragment : BaseFragment<MyViewModel, FragmentMyBinding>() {

    override val viewModel by viewModel<MyViewModel>()

    override fun getViewBinding(): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

    private val myAdapter: MyAdapter by lazy {
        MyAdapter()
    }

    companion object {
        fun newInstance(): MyFragment = MyFragment()
        const val TAG = "myFragment"
    }

    override fun initViews() {
        viewModel.fetchName()
        binding.recyclerView.apply {
            adapter = myAdapter
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        }
    }

    override fun observeData() {
        viewModel.likeMoviesLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is MyState.Uninitialized -> Unit
                is MyState.Loading -> handleLoadingState()
                is MyState.Success -> handleSuccessState(it)
                is MyState.Error -> handleErrorState()
            }
        }
        viewModel.nameLiveData.observe(viewLifecycleOwner) {
            if(!it.isNullOrBlank() && it != "unknown") {
                binding.toolbar.text = "${it}님의 찜 목록"
            }
        }
    }

    private fun handleLoadingState() = with(binding) {
        progressBar.isVisible = true
        recyclerView.isVisible = true
        errorTextView.isGone = true
        emptyLikeTextView.isGone = true
    }

    private fun handleSuccessState(state: MyState.Success) = with(binding) {
        progressBar.isGone = true
        errorTextView.isGone = true
        if(state.likeMovies.isNullOrEmpty()) {
            recyclerView.isGone = true
            emptyLikeTextView.isVisible = true
        } else {
            (recyclerView.adapter as MyAdapter).apply {
                likeMovies = state.likeMovies
                onClick = {
                    val intent = Intent(requireContext(), DetailActivity::class.java)
                    intent.putExtra(HomeFragment.INTENT_MOVIE, it.toMovie())
                    intent.putExtra(HomeFragment.INTENT_LIKE, true)
                    startActivityForResult(intent, HomeFragment.REQUEST_CODE)
                }
                notifyDataSetChanged()
            }
        }

    }

    private fun handleErrorState() = with(binding) {
        errorTextView.isVisible = true
        recyclerView.isGone = true
        progressBar.isGone = true
        emptyLikeTextView.isGone = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.fetchData()
    }
}