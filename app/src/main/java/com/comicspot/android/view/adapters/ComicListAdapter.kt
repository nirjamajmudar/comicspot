package com.comicspot.android.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.comicspot.android.R
import com.comicspot.android.network.model.ComicResults
import com.bumptech.glide.Glide.with
import com.comicspot.android.Constants
import com.comicspot.android.utils.ComicUtils

/**
 * A RecyclerView.Adapter to populate the screen with the list of comics.
 */

class ComicListAdapter(
    var comicList: MutableList<ComicResults>?,
    clickListener: ComicView.OnComicItemClickListener?
) : RecyclerView.Adapter<ComicItemViewHolder>() {

    private val listener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicItemViewHolder {
        return ComicItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_comic, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ComicItemViewHolder, position: Int) {
        with(holder.itemView) {
            findViewById<TextView>(R.id.comic_title_text).text = comicList?.get(position)?.title
            with(context).load(
                ComicUtils.generateImagePath(
                    comicList?.get(position)?.thumbnail?.path,
                    comicList?.get(position)?.thumbnail?.extension, Constants.PORTRAIT_FANTASTIC
                )
            )
                .into(findViewById<AppCompatImageView>(R.id.comic_thumbnail))
            setOnClickListener { view ->
                comicList?.get(position)?.id?.let {
                    listener?.itemClicked(
                        it
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return comicList?.size ?: run { 0 }
    }

    fun updateData(list: MutableList<ComicResults>) {
        comicList = list
        notifyDataSetChanged()
    }
}

object ComicView {
    interface OnComicItemClickListener {
        fun itemClicked(comicId: Int)
    }
}

/**
 * Holds the view for the Comic item.
 */
class ComicItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
