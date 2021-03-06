package com.kotlinplay.app.main.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kotlinplay.R
import com.kotlinplay.api.model.response.Season
import com.kotlinplay.api.model.response.Show
import com.kotlinplay.app.util.NA
import com.kotlinplay.databinding.RowSeasonBinding
import rx.Observable
import rx.subjects.PublishSubject

class SeasonsAdapter(val seasonsList: ArrayList<Season>) : RecyclerView.Adapter<SeasonsAdapter.ViewHolder>() {

    private val onSeasonClickSubject = PublishSubject.create<Season>()

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val v = DataBindingUtil.inflate<RowSeasonBinding>(layoutInflater, R.layout.row_season, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(seasonsList[position], position)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return seasonsList.size
    }

    inner class ViewHolder(private val binding: RowSeasonBinding) : RecyclerView.ViewHolder(binding.getRoot()) {

        fun bind(season: Season, position: Int) {
            val context = binding.root.context

            binding.authorName.text = String.format("%1s %2s",
                context.resources.getString(R.string.show_details_season),
                if(season.number!=null) season.number else NA)
            binding.episodesText.text = String.format("%1s %2s",
                context.resources.getString(R.string.show_details_episodes),
                if(season.episodeOrder!=null) season.episodeOrder else NA)

            Glide.with(context)
                .load(season.image?.medium)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .placeholder(R.drawable.logo_splash)
                .transition(GenericTransitionOptions.with(R.anim.anim_fadein))
                .into(binding.avatar)
            binding.latestSeason = seasonsList.size > 1 && position == seasonsList.size - 1

            binding.parent.setOnClickListener { onSeasonClickSubject.onNext(season)  }
        }
    }

    //observable for adapter clicks
    fun getSeasonClickObservable(): Observable<Season> {
        return onSeasonClickSubject.asObservable()
    }


}