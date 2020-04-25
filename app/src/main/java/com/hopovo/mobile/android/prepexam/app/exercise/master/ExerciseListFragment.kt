package com.hopovo.mobile.android.prepexam.app.exercise.master


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hopovo.mobile.android.prepexam.app.R
import com.hopovo.mobile.android.prepexam.app.common.BaseFragment
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.AppAction
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.AppAction.GET_BUFFEROOS
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.AppError.NO_INTERNET
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.AppError.TIMEOUT
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorBundle
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorDialogFragment
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorDialogFragment.ErrorDialogFragmentListener
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorUtils
import com.hopovo.mobile.android.prepexam.app.common.gone
import com.hopovo.mobile.android.prepexam.app.common.model.ResourceState.*
import com.hopovo.mobile.android.prepexam.app.common.visible
import com.hopovo.mobile.android.prepexam.app.common.widget.empty.EmptyListener
import com.hopovo.mobile.android.prepexam.app.common.widget.error.ErrorListener
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import kotlinx.android.synthetic.main.fragment_exercise_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class ExerciseListFragment : BaseFragment(), ErrorDialogFragmentListener {

    private val exercisesAdapter: ExerciseListAdapter by inject()
    private val exerciseViewModel: ExerciseViewModel by sharedViewModel()
    private val adapterListener = object : ExerciseListAdapter.ExerciseListItemListener {
        override fun onItemClicked(position: Int) {
            Timber.d("Selected position $position")
            exerciseViewModel.select(position)
        }
    }

    // region Lifecycle methods
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_exercise_list, container, false)
    }

    override fun onResume() {
        super.onResume()

        activity?.setTitle(R.string.bufferoo_master_title)
    }

    override fun earlyInitializeViews() {
        super.earlyInitializeViews()

        setupExercisesRecycler()
        setupViewListeners()
        setupAddExerciseButton()
    }

    override fun initializeContents(savedInstanceState: Bundle?) {
        super.initializeContents(savedInstanceState)

        exerciseViewModel.getExercises().observe(viewLifecycleOwner,
                Observer {
                    handleDataState(it)
                }
        )

        if (exerciseViewModel.getExercises().value == null) {
            exerciseViewModel.fetchExercises()
        }
    }

    private fun setupExercisesRecycler() {
        rv_bufferoos.layoutManager = LinearLayoutManager(this.context)

        exercisesAdapter.setExerciseListListener(this.adapterListener)
        rv_bufferoos.adapter = exercisesAdapter
    }
    //endregion

    //region Handling state
    private fun handleDataState(exerciseList: ExerciseListState) {
        when (exerciseList) {
            is Loading -> setupScreenForLoadingState()
            is Success -> setupScreenForSuccess(exerciseList.data)
            is Error -> setupScreenForError(exerciseList.errorBundle)
        }
    }

    private fun setupScreenForLoadingState() {
        lv_bufferoos_loading_view.visible()
        ev_bufferoos_empty_view.gone()
        ev_bufferoos_error_view.gone()
    }

    private fun setupScreenForSuccess(data: List<Exercise>?) {
        ev_bufferoos_error_view.gone()
        lv_bufferoos_loading_view.gone()
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
        } else {
            ev_bufferoos_empty_view.visible()
            fab_add_exercise.show()
        }
    }

    private fun updateListView(exercises: List<Exercise>) {
        exercisesAdapter.exercises = exercises
        exercisesAdapter.notifyDataSetChanged()
    }

    private fun setupScreenForError(errorBundle: ErrorBundle) {
        lv_bufferoos_loading_view.gone()
        ev_bufferoos_empty_view.gone()

        if (errorBundle.appError == NO_INTERNET || errorBundle.appError == TIMEOUT) {
            // Example of using a custom error view as part of the fragment view
            showErrorView(errorBundle)
        } else {
            // Example of using an error fragment dialog
            showErrorDialog(errorBundle)
        }
    }
    //endregion

    //region Error and empty views
    private fun setupViewListeners() {
        ev_bufferoos_empty_view.emptyListener = emptyListener
        ev_bufferoos_error_view.errorListener = errorListener
    }

    private fun setupAddExerciseButton(){
        fab_add_exercise.setOnClickListener {
            findNavController().navigate(R.id.photoListFragment)
        }

        fab_add.setOnClickListener{
            findNavController().navigate(R.id.takePhotoFragment)
        }
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            exerciseViewModel.fetchExercises()
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onRetry(errorBundle: ErrorBundle) {
            retry(errorBundle.appAction)
        }
    }

    private fun retry(appAction: AppAction) {
        when (appAction) {
            GET_BUFFEROOS -> exerciseViewModel.fetchExercises()
            else -> Timber.e("Unknown action code")
        }
    }

    private fun showErrorView(errorBundle: ErrorBundle) {
        activity?.let { activity ->
            ev_bufferoos_error_view.visible()
            ev_bufferoos_error_view.errorBundle = errorBundle
            ev_bufferoos_error_view.setErrorMessage(ErrorUtils.buildErrorMessageForDialog(activity, errorBundle).message)
        } ?: Timber.e("Activity is null")
    }

    private fun showErrorDialog(errorBundle: ErrorBundle) {
        val tag = ErrorDialogFragment.TAG
        activity?.let { activity ->
            activity.supportFragmentManager?.let { fragmentManager ->
                val previousDialogFragment = fragmentManager.findFragmentByTag(tag) as? DialogFragment

                // Check that error dialog is not already shown after a screen rotation

//                if (previousDialogFragment != null
//                        && previousDialogFragment.dialog != null
//                        && previousDialogFragment.dialog?.isShowing
//                        && !previousDialogFragment.isRemoving
//                ) {
//                    // Error dialog is shown
//                    Timber.w("Error dialog is already shown")
//                } else {
//                    // Error dialog is not shown
//                    val errorDialogFragment = ErrorDialogFragment.newInstance(
//                            ErrorUtils.buildErrorMessageForDialog(activity, errorBundle),
//                            true
//                    )
//                    if (!fragmentManager.isDestroyed && !fragmentManager.isStateSaved) {
//                        // Sets the target fragment for using later when sending results
//                        errorDialogFragment.setTargetFragment(this@ExerciseListFragment, ERROR_DIALOG_REQUEST_CODE)
//                        // Fragment contains the dialog and dialog should be controlled from fragment interface.
//                        // See: https://stackoverflow.com/a/8921129/5189200
//                        errorDialogFragment.isCancelable = false
//                        errorDialogFragment.show(fragmentManager, tag)
//                    }
//                }
            } ?: Timber.e("Support fragment manager is null")
        } ?: Timber.e("Activity is null")
    }

    override fun onErrorDialogAccepted(action: Long, retry: Boolean) {
        if (retry) {
            retry(AppAction.fromCode(action))
        } else {
            Timber.d("There was no retry option for action \'$action\' given to the user.")
        }
    }

    override fun onErrorDialogCancelled(action: Long) {
        Timber.d("User cancelled error dialog")
    }
    //endregion
}
