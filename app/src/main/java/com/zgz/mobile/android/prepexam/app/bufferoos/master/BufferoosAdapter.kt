package com.zgz.mobile.android.prepexam.app.bufferoos.master

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zgz.mobile.android.prepexam.app.R
import com.zgz.mobile.android.prepexam.app.bufferoos.master.BufferoosAdapter.ViewHolder
import com.zgz.mobile.android.prepexam.app.common.view.OnSingleClickListener
import com.zgz.mobile.android.prepexam.model.bufferoo.Bufferoo
import java.lang.ref.WeakReference

class BufferoosAdapter : RecyclerView.Adapter<ViewHolder>() {


    interface BufferoosAdapterListener {

        fun onItemClicked(position: Int)
    }

    var bufferoos: List<Bufferoo> = arrayListOf()
    private var bufferosAdapterListenerWeakReference: WeakReference<BufferoosAdapterListener>? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bufferoo = bufferoos[position]
        holder.nameText.text = bufferoo.name
        holder.titleText.text = bufferoo.title

        Glide.with(holder.itemView.context)
            .load(bufferoo.avatar)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.avatarImage)

        // On click listener
        holder.itemView.setOnClickListener(
            OnSingleClickListener.wrap(View.OnClickListener {
                this@BufferoosAdapter.bufferosAdapterListenerWeakReference?.get()?.let { bufferoosAdapterListener ->
                    bufferoosAdapterListener.onItemClicked(position)
                }
            })
        )
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

    fun setBufferoosAdapterListener(@NonNull bufferoosAdapterListener: BufferoosAdapterListener) {
        this.bufferosAdapterListenerWeakReference = WeakReference(bufferoosAdapterListener)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatarImage: ImageView
        var nameText: TextView
        var titleText: TextView

        init {
            avatarImage = view.findViewById(R.id.iv_bufferoo_row_avatar_image)
            nameText = view.findViewById(R.id.tv_bufferoo_row_name)
            titleText = view.findViewById(R.id.tv_bufferoo_row_title)
        }
    }
}