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
import com.kotlinplay.app.util.FALLBACK_IMAGE
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

            binding.authorName.text = String.format("Season %1s",season.number!!.toString())
            binding.episodesText.text = String.format("Episodes %1s",season.episodeOrder!!.toString())

            var url: String? = null
            if(season.image != null){
                url = season.image.medium!!.toString()
            }

            Glide.with(context)
                .load(url)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .placeholder(R.drawable.logo_splash)
                .transition(GenericTransitionOptions.with(R.anim.anim_fadein))
                .into(binding.avatar)
            if (seasonsList.size > 1 && position == seasonsList.size - 1){
                binding.latestSeason = true
            }

            binding.parent.setOnClickListener { view ->  onSeasonClickSubject.onNext(season)  }
        }
    }

    fun getSeasonClickObservable(): Observable<Season> {
        return onSeasonClickSubject.asObservable()
    }


}