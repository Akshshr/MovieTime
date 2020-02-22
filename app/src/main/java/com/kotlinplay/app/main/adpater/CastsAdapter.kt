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
import com.kotlinplay.api.model.response.Cast
import com.kotlinplay.api.model.response.Show
import com.kotlinplay.databinding.RowCastBinding
import rx.Observable
import rx.subjects.PublishSubject

class CastsAdapter(val castList: ArrayList<Cast>) : RecyclerView.Adapter<CastsAdapter.ViewHolder>() {

    private val onCastSubject = PublishSubject.create<Cast>()

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val v = DataBindingUtil.inflate<RowCastBinding>(layoutInflater, R.layout.row_cast, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(castList.get(position))
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return castList.size
    }

    inner class ViewHolder(private val binding: RowCastBinding) : RecyclerView.ViewHolder(binding.getRoot()) {

        fun bind(cast: Cast) {
            val context = binding.getRoot().getContext()

            binding.authorName.text = cast.person!!.name

            var url: String? = null
            if(cast.person.image != null){
                url = cast.person.image.medium!!.toString()
            }
            Glide.with(context)
                .load(url)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL).circleCrop())
                .placeholder(R.drawable.logo_splash_mini)
                .transition(GenericTransitionOptions.with(R.anim.anim_fadein))
                .into(binding.avatar)

            binding.parent.setOnClickListener { view ->  onCastSubject.onNext(cast) }
        }
    }

    fun getCastObservable(): Observable<Cast> {
        return onCastSubject.asObservable()
    }


}