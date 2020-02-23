package com.kotlinplay.app.main

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.kotlinplay.R
import com.kotlinplay.api.model.response.SearchTermResponse
import com.kotlinplay.api.model.response.Show
import com.kotlinplay.app.base.BaseActivity
import com.kotlinplay.app.main.adpater.ShowsAdapter
import com.kotlinplay.app.main.viewModels.MainActivityViewModel
import com.kotlinplay.app.util.SHOW_DETAILS
import com.kotlinplay.app.util.giveSerchableString
import com.kotlinplay.app.util.hideKeyboard
import com.kotlinplay.app.util.showToast
import com.kotlinplay.databinding.ActivityMainBinding
import rx.android.schedulers.AndroidSchedulers


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var adapter:ShowsAdapter
    private lateinit var mainActivityViewModel: MainActivityViewModel

    companion object{
        private var TAG = MainActivity::class.java.simpleName
        var allShows: ArrayList<Show> = ArrayList()
        var displayingShows : Boolean = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        isLoading(true)
        mainActivityViewModel.getShows()
        mainActivityViewModel.showsMLiveData.observe(::getLifecycle, ::onAllShows)
        mainActivityViewModel.searchTermMLiveData.observe(::getLifecycle, ::onSearchResponse)
        mainActivityViewModel.isLoading.observe(::getLifecycle, ::isLoading)
        mainActivityViewModel.containsError.observe(::getLifecycle, ::onError)

        adapter = ShowsAdapter(mutableListOf<Show>() as ArrayList<Show>)
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset == -binding.appBar.getHeight() + binding.appBar.getHeight()) binding.fab.hide() else binding.fab.show()
        })

        binding.fab.setOnClickListener {
            binding.nestedScrollView.scrollTo(0,0)
            binding.appBar.setExpanded(true,true)
        }

        binding.showsRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.searchView.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(binding.searchView.text.toString().isNotEmpty() || binding.searchView.text.toString().isNotBlank()) {
                    mainActivityViewModel.searchQuery(giveSerchableString(binding.searchView.getText().toString().trim()))
                    handled = true
                }else{
                    showToast(resources.getString(R.string.search_error))
                }
            }
            handled
        }
    }

    private fun isLoading(isLoading: Boolean?) {
        binding.loading = isLoading
    }

    private fun onError(throwable: Throwable) {
        isLoading(false)
        handleError(throwable)
    }

    private fun onSearchResponse(it: ArrayList<SearchTermResponse>) {
        if(it.size<1){
            showToast(resources.getString(R.string.no_results_error))
            return
        }
        val searhShowList: ArrayList<Show> = ArrayList()
        it.forEach {
            searhShowList.add(it.show!!)
        }
        hideKeyboard()
        adapter = ShowsAdapter(searhShowList)
        binding.showsRecyclerView.adapter = adapter
        setUpAdapterListeners()
    }


    private fun onAllShows(it: ArrayList<Show>) {
            TransitionManager.beginDelayedTransition(binding.showsRecyclerView)
            displayingShows = true
            allShows = it
            adapter = ShowsAdapter(it)
            binding.showsRecyclerView.adapter = adapter
            setUpAdapterListeners()
    }

    private fun setUpAdapterListeners() {
        adapter.getShowObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .takeUntil(getLifecycleEvents(ActivityEvent.STOP))
            .subscribe(fun(show: Show) {
                onShowDetails(show)
            },
                this::handleError)
    }

    private fun onShowDetails(show: Show?){
        val intent = Intent(this@MainActivity, ShowDetailsActivity::class.java)
        intent.putExtra(SHOW_DETAILS, show)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        setUpAdapterListeners()
    }

    override fun onBackPressed() {
        if(!displayingShows){
            binding.searchView.text.clear()
            onAllShows(allShows)
        }else{
            super.onBackPressed()
        }
    }





}
