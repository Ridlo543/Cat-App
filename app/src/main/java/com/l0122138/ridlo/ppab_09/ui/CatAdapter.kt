package com.l0122138.ridlo.ppab_09.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.l0122138.ridlo.ppab_09.R
import com.l0122138.ridlo.ppab_09.model.Cat

class CatAdapter(private val cats: List<Cat>) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_item, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = cats[position]
        Glide.with(holder.itemView.context)
            .load(cat.url)
            .into(holder.imageView)
    }

    override fun getItemCount() = cats.size
}
