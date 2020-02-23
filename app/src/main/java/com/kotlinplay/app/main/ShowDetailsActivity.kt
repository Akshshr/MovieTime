package com.kotlinplay.app.main

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.widget.TextView
import androidx.core.text.parseAsHtml
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.kotlinplay.R
import com.kotlinplay.api.model.response.Cast
import com.kotlinplay.api.model.response.Season
import com.kotlinplay.api.model.response.Show
import com.kotlinplay.app.base.BaseActivity
import com.kotlinplay.app.main.adpater.CastsAdapter
import com.kotlinplay.app.main.adpater.SeasonsAdapter
import com.kotlinplay.app.main.viewModels.ShowDetailsViewModel
import com.kotlinplay.app.util.NA
import com.kotlinplay.app.util.SHOW_DETAILS
import com.kotlinplay.app.util.STATUS_UNKNOWN
import com.kotlinplay.app.util.openURL
import com.kotlinplay.databinding.ActivityShowDetailsBinding
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ShowDetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityShowDetailsBinding
    private var show: Show? = null
    lateinit var castsAdapter: CastsAdapter
    lateinit var seasonsAdapter: SeasonsAdapter
    private lateinit var showDetailsViewModel: ShowDetailsViewModel

    companion object {
        private var TAG = ShowDetailsActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_details)
        showDetailsViewModel = ViewModelProvider(this).get(ShowDetailsViewModel::class.java)

        castsAdapter = CastsAdapter(mutableListOf<Cast>() as ArrayList<Cast>)
        seasonsAdapter = SeasonsAdapter(mutableListOf<Season>() as ArrayList<Season>)

        show = intent!!.extras!!.get(SHOW_DETAILS) as Show?
        showDetailsViewModel.burstFetchShowDetails(show?.id.toString())

        initShow()
    }

    private fun initShow() {
        binding.loadingCast= true
        binding.loadingSeasons = true

        showDetailsViewModel.seasonsData.observe(::getLifecycle, ::onSeasonsData)
        showDetailsViewModel.castData.observe(::getLifecycle, ::onCastsData)
        showDetailsViewModel.isLoading.observe(::getLifecycle, ::doneLoading)
        showDetailsViewModel.containsError.observe(::getLifecycle, ::onError)

        binding.title.text = show?.name ?: NA
        binding.description.text = show?.summary?.parseAsHtml() ?: NA
        binding.ratingValue.text = show?.rating?.average?.toString() ?: NA

        binding.showStatus.text = if(show?.status!=null)
            String.format(resources.getString(R.string.show_details_status), show!!.status) else
            STATUS_UNKNOWN

        Glide.with(this)
            .load(show?.image?.original)
            .transition(GenericTransitionOptions.with(R.anim.anim_fadein))
            .fallback(R.drawable.logo_splash)
            .into(binding.headerImage)
        val generes = show?.genres
        for (i in generes?.indices!!) {
            val view = layoutInflater.inflate(R.layout.row_genere, null)
            (view.findViewById<View>(R.id.authorName) as TextView).text = generes[i].toString()
            binding.genereList.addView(view)
        }

        binding.visitShowWebsite.setOnClickListener {openURL(show!!.officialSite)}
        binding.scrollShortCut.setOnClickListener{ binding.seasonsRecyclerView.smoothScrollToPosition(seasonsAdapter.itemCount - 1) }
        binding.visitWebsite.setOnClickListener { openURL(show!!.url) }

    }

    private fun onError(throwable: Throwable) {
        doneLoading(true)
        handleError(throwable)
    }

    private fun onSeasonsData(seasonsList: ArrayList<Season>) {
        TransitionManager.beginDelayedTransition(binding.seasonsRecyclerView)
        binding.seasonsRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        seasonsAdapter = SeasonsAdapter(seasonsList)
        binding.seasonsRecyclerView.adapter = seasonsAdapter
        binding.showSeasonsLink = binding.seasonsRecyclerView.adapter!!.itemCount > 2
    }


    private fun onCastsData(castsList: ArrayList<Cast>) {
        TransitionManager.beginDelayedTransition(binding.castRecyclerview)
        binding.castRecyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        castsAdapter = CastsAdapter(castsList)
        binding.castRecyclerview.adapter = castsAdapter
    }

    private fun doneLoading(isLoading: Boolean?) {
        binding.loadingCast= isLoading
        binding.loadingSeasons = isLoading
        setupAdapterListeners()
    }

    private fun setupAdapterListeners() {

        seasonsAdapter.getSeasonClickObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .takeUntil(getLifecycleEvents(ActivityEvent.DESTROY))
            .subscribe(fun(season: Season) {
                openURL(season.url)
            }, this::handleError)

        castsAdapter.getCastObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .takeUntil(getLifecycleEvents(ActivityEvent.DESTROY))
            .subscribe(fun(cast: Cast) {
                openURL(cast.person!!.url)
            }, this::handleError)
    }

    override fun onStart() {
        super.onStart()
        setupAdapterListeners()
    }

}
