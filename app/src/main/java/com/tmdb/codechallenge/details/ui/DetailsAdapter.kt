package com.tmdb.codechallenge.details.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tmdb.codechallenge.R
import com.tmdb.codechallenge.api.model.MovieImage
import com.tmdb.codechallenge.util.MovieImageUrlBuilder
import kotlinx.android.synthetic.main.poster_item.view.*

class DetailsAdapter(private val images: List<MovieImage>) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val movieImageUrlBuilder = MovieImageUrlBuilder()

        fun bind(image: MovieImage) {
            Glide.with(itemView)
                    .load(movieImageUrlBuilder.buildDetailsPosterUrl(image.filePath))
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(itemView.posterImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.poster_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        return holder.bind(image)
    }
}