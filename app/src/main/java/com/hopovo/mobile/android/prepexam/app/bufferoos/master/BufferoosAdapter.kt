package com.hopovo.mobile.android.prepexam.app.bufferoos.master

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hopovo.mobile.android.prepexam.app.R
import com.hopovo.mobile.android.prepexam.app.bufferoos.master.BufferoosAdapter.ViewHolder
import com.hopovo.mobile.android.prepexam.app.common.view.OnSingleClickListener
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import java.lang.ref.WeakReference

class BufferoosAdapter : RecyclerView.Adapter<ViewHolder>() {


    interface BufferoosAdapterListener {

        fun onItemClicked(position: Int)
    }

    var mExercises: List<Exercise> = arrayListOf()
    private var bufferosAdapterListenerWeakReference: WeakReference<BufferoosAdapterListener>? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bufferoo = mExercises[position]
        holder.nameText.text = bufferoo.description
        holder.titleText.text = bufferoo.title

        Glide.with(holder.itemView.context)
            .load(bufferoo.image)
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
            .inflate(R.layout.exercise_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mExercises.size
    }

    fun setBufferoosAdapterListener(@NonNull bufferoosAdapterListener: BufferoosAdapterListener) {
        this.bufferosAdapterListenerWeakReference = WeakReference(bufferoosAdapterListener)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatarImage: ImageView
        var nameText: TextView
        var titleText: TextView

        init {
            avatarImage = view.findViewById(R.id.iv_exercise_item)
            nameText = view.findViewById(R.id.tv_title_exercise_item)
            titleText = view.findViewById(R.id.tv_exercise_description)
        }
    }
}