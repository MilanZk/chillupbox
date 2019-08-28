package com.hopovo.mobile.android.prepexam.app.exercise.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hopovo.mobile.android.prepexam.app.R
import com.hopovo.mobile.android.prepexam.app.common.BaseFragment
import com.hopovo.mobile.android.prepexam.app.exercise.master.ExerciseViewModel
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import kotlinx.android.synthetic.main.fragment_execrcise_detail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ExerciseDetailFragment : BaseFragment() {

    companion object {

        val TAG = ExerciseDetailFragment::class.java.canonicalName

        fun newInstance() = ExerciseDetailFragment()
    }

    private val exerciseViewModel: ExerciseViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_execrcise_detail, container, false)
    }

    override fun onResume() {
        super.onResume()

        activity?.setTitle(R.string.bufferoo_master_title)
    }

    override fun earlyInitializeViews() {
        super.earlyInitializeViews()
        setupSaveButton()
    }

    override fun initializeContents(savedInstanceState: Bundle?) {
        super.initializeContents(savedInstanceState)
    }

    private fun setupSaveButton() {
        bt_save.setOnClickListener {
            exerciseViewModel.saveExercise(Exercise(et_word.text.toString(),
                    et_description.text.toString(), "", et_translation.text.toString()))
        }
    }
}
