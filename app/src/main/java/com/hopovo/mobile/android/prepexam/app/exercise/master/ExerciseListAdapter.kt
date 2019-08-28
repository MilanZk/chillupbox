package com.hopovo.mobile.android.prepexam.app.exercise.master

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.hopovo.mobile.android.prepexam.app.R
import com.hopovo.mobile.android.prepexam.app.common.view.OnSingleClickListener
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import java.lang.ref.WeakReference

class ExerciseListAdapter : RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>() {


    interface ExerciseListItemListener {

        fun onItemClicked(position: Long)
    }

    var exercises: List<Exercise> = arrayListOf()
    private var exerciseListAdapterListenerWeakReference: WeakReference<ExerciseListItemListener>? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.wordText.text = exercise.word
        holder.descriptionText.text = exercise.description
        holder.translationText.text = exercise.translation

  /*      Glide.with(holder.itemView.context)
                .load(exercise.image)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.avatarImage)*/

        // On click listener
        holder.itemView.setOnClickListener(
                OnSingleClickListener.wrap(View.OnClickListener {
                    this@ExerciseListAdapter.exerciseListAdapterListenerWeakReference?.get()?.onItemClicked(exercises[position].id)
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
        return exercises.size
    }

    fun setExerciseListListener(@NonNull exerciseListItemListener: ExerciseListItemListener) {
        this.exerciseListAdapterListenerWeakReference = WeakReference(exerciseListItemListener)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var wordText: TextView = view.findViewById(R.id.tv_title_exercise_item)
        var descriptionText: TextView = view.findViewById(R.id.tv_exercise_description)
        var translationText: TextView = view.findViewById(R.id.tv_exercise_translation)
    }

}