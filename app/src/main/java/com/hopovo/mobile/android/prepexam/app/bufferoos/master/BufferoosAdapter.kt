package com.hopovo.mobile.android.prepexam.app.bufferoos.master

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
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
        holder.wordText.text = bufferoo.word
        holder.descriptionText.text = bufferoo.description
        holder.translationText.text = bufferoo.translation

/*        Glide.with(holder.itemView.context)
            .load(bufferoo.image)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.avatarImage)*/

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
        var wordText: TextView = view.findViewById(R.id.tv_title_exercise_item)
        var descriptionText: TextView = view.findViewById(R.id.tv_exercise_description)
        var translationText: TextView = view.findViewById(R.id.et_translation)
    }
}