package com.hopovo.mobile.android.prepexam.app.exercise.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.hopovo.mobile.android.prepexam.app.R
import com.hopovo.mobile.android.prepexam.app.common.BaseFragment
import com.hopovo.mobile.android.prepexam.app.exercise.ExerciseCommand
import com.hopovo.mobile.android.prepexam.app.exercise.master.ExerciseViewModel
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import kotlinx.android.synthetic.main.fragment_execrcise_detail.*
import kotlinx.android.synthetic.main.word_edit_text.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ExerciseDetailFragment : BaseFragment() {

    private val exerciseViewModel: ExerciseViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_execrcise_detail, container, false)
    }

    override fun earlyInitializeViews() {
        super.earlyInitializeViews()
        setupSaveButton()
    }

    override fun initializeContents(savedInstanceState: Bundle?) {
        super.initializeContents(savedInstanceState)

        exerciseViewModel.getExerciseSingleLiveEvent().observe(viewLifecycleOwner, Observer {
            when (it) {

                ExerciseCommand.ExerciseAddingSuccess -> {
                    activity?.onBackPressed()
                }

                ExerciseCommand.ExerciseAddingError -> {
                }
            }
        })
    }

    private fun setupSaveButton() {
        bt_save.setOnClickListener {
            exerciseViewModel.saveExercise(Exercise(et_word.edit_text.text.toString(),
                    et_phrase_example.edit_text.text.toString(), "", et_translation.edit_text.text.toString()))
        }
    }
}
