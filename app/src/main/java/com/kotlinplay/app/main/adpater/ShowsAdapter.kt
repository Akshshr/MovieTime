package com.kotlinplay.app.main.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kotlinplay.R
import com.kotlinplay.api.model.response.Show
import com.kotlinplay.app.util.NA
import com.kotlinplay.databinding.RowSeriesBinding
import rx.Observable
import rx.subjects.PublishSubject

class ShowsAdapter(val seriesList: ArrayList<Show>) : RecyclerView.Adapter<ShowsAdapter.ShowViewHolder>() {

    private val onShowSubject = PublishSubject.create<Show>()

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<RowSeriesBinding>(layoutInflater, R.layout.row_series, parent, false)
        return ShowViewHolder(binding)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(seriesList.get(position))
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return seriesList.size
    }

    inner class ShowViewHolder(private val binding: RowSeriesBinding) : RecyclerView.ViewHolder(binding.getRoot()) {

        fun bind(show: Show) {
            val context = binding.root.context

            binding.title.text = show.name
            binding.authorName.text = show.network?.name ?: NA
            binding.languageTag.text = show.language ?: NA

            Glide.with(context)
                .load(show.image?.medium)
                .transform(RoundedCorners(12))
                .transition(GenericTransitionOptions.with(R.anim.anim_fadein))
                .apply(RequestOptions.centerCropTransform())
                .fallback(R.drawable.logo_splash_mini)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.avatar)

            binding.ratingValue.text = if(show.rating?.average!=null) String.format("%1s / 10", show.rating.average.toString()) else NA

            binding.parent.setOnClickListener { onShowSubject.onNext(show)  }
        }
    }

    //observable for adapter clicks
    fun getShowObservable(): Observable<Show> {
        return onShowSubject.asObservable()
    }


}