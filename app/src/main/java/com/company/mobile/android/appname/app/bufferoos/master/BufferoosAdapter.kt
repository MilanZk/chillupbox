package com.company.mobile.android.appname.app.bufferoos.master

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosAdapter.ViewHolder
import com.company.mobile.android.appname.model.bufferoo.Bufferoo

class BufferoosAdapter : RecyclerView.Adapter<ViewHolder>() {

    var bufferoos: List<Bufferoo> = arrayListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bufferoo = bufferoos[position]
        holder.nameText.text = bufferoo.name
        holder.titleText.text = bufferoo.title

        Glide.with(holder.itemView.context)
            .load(bufferoo.avatar)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.avatarImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_bufferoo, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return bufferoos.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatarImage: ImageView
        var nameText: TextView
        var titleText: TextView

        init {
            avatarImage = view.findViewById(R.id.iv_bufferoo_row_avatar_image)
            nameText = view.findViewById(R.id.tv_bufferoo_visit_row_name)
            titleText = view.findViewById(R.id.tv_bufferoo_row_title)
        }
    }
}