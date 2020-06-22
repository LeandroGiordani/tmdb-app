package com.tmdb.codechallenge.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tmdb.codechallenge.R
import com.tmdb.codechallenge.api.model.Movie
import com.tmdb.codechallenge.util.MovieImageUrlBuilder
import kotlinx.android.synthetic.main.movie_item.view.*

class HomeAdapter(
        private val moviesList: MutableList<Movie>,
        private val listener: OnItemClickListener) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val movieImageUrlBuilder = MovieImageUrlBuilder()

        fun bind(movie: Movie) {

            Glide.with(itemView)
                .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.moviePosterImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moviesList[position])
        holder.itemView.setOnClickListener {
            listener.onItemClick(moviesList[position])
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Movie)
    }
}
