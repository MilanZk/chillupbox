package com.hopovo.mobile.android.prepexam.app.exerciselist

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
        holder.nameText.text = exercise.description
        holder.titleText.text = exercise.title

        Glide.with(holder.itemView.context)
                .load(exercise.image)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.avatarImage)

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