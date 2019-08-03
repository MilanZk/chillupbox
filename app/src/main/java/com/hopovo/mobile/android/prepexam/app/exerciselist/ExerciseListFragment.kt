package com.hopovo.mobile.android.prepexam.app.exerciselist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
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
import com.hopovo.mobile.android.prepexam.app.common.model.ResourceState.*
import com.hopovo.mobile.android.prepexam.app.common.widget.empty.EmptyListener
import com.hopovo.mobile.android.prepexam.app.common.widget.error.ErrorListener
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import kotlinx.android.synthetic.main.fragment_bufferoos.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class ExerciseListFragment : BaseFragment(), ErrorDialogFragmentListener {

    companion object {

        val TAG = ExerciseListFragment::class.java.canonicalName
        private const val ERROR_DIALOG_REQUEST_CODE = 0

        fun newInstance() = ExerciseListFragment()
    }

    private val exercisesAdapter: ExerciseListAdapter by inject()
    private val exerciseViewModel: ExerciseListViewModel by sharedViewModel()
    private val adapterListener = object : ExerciseListAdapter.ExerciseListItemListener {
        override fun onItemClicked(position: Long) {
            Timber.d("Selected position $position")
            exerciseViewModel.select(position)
        }
    }

    // region Lifecycle methods
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_bufferoos, container, false)
    }

    override fun onResume() {
        super.onResume()

        activity?.setTitle(R.string.bufferoo_master_title)
    }

    override fun earlyInitializeViews() {
        super.earlyInitializeViews()

        setupExercisesRecycler()
        setupViewListeners()
    }

    override fun initializeContents(savedInstanceState: Bundle?) {
        super.initializeContents(savedInstanceState)

        // Link the fragment and the model view with "viewLifecycleOwner", so that observers
        // can be subscribed in onActivityCreated() and can be automatically unsubscribed
        // in onDestroyView().
        // IMPORTANT: Never use "this" as lifecycle owner.
        // See: https://medium.com/@BladeCoder/architecture-components-pitfalls-part-1-9300dd969808
        exerciseViewModel.getExercises().observe(viewLifecycleOwner,
                Observer<ExerciseListState> {
                    if (it != null) this.handleDataState(it)
                }
        )

        // Check if the view model has data
        if (exerciseViewModel.getExercises().value == null) {
            // Fetch data only if the view model doesn't have data
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
    private fun handleDataState(bufferoosState: ExerciseListState) {
        when (bufferoosState) {
            is Loading -> setupScreenForLoadingState()
            is Success -> setupScreenForSuccess(bufferoosState.data)
            is Error -> setupScreenForError(bufferoosState.errorBundle)
        }
    }

    private fun setupScreenForLoadingState() {
        lv_bufferoos_loading_view.visibility = View.VISIBLE
        ev_bufferoos_empty_view.visibility = View.GONE
        ev_bufferoos_error_view.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<Exercise>?) {
        ev_bufferoos_error_view.visibility = View.GONE
        lv_bufferoos_loading_view.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
        } else {
            ev_bufferoos_empty_view.visibility = View.VISIBLE
        }
    }

    private fun updateListView(exercises: List<Exercise>) {
        exercisesAdapter.exercises = exercises
        exercisesAdapter.notifyDataSetChanged()
    }

    private fun setupScreenForError(errorBundle: ErrorBundle) {
        lv_bufferoos_loading_view.visibility = View.GONE
        ev_bufferoos_empty_view.visibility = View.GONE

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
            ev_bufferoos_error_view.visibility = View.VISIBLE
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
                if (previousDialogFragment != null
                        && previousDialogFragment.dialog != null
                        && previousDialogFragment.dialog.isShowing
                        && !previousDialogFragment.isRemoving
                ) {
                    // Error dialog is shown
                    Timber.w("Error dialog is already shown")
                } else {
                    // Error dialog is not shown
                    val errorDialogFragment = ErrorDialogFragment.newInstance(
                            ErrorUtils.buildErrorMessageForDialog(activity, errorBundle),
                            true
                    )
                    if (!fragmentManager.isDestroyed && !fragmentManager.isStateSaved) {
                        // Sets the target fragment for using later when sending results
                        errorDialogFragment.setTargetFragment(this@ExerciseListFragment, ERROR_DIALOG_REQUEST_CODE)
                        // Fragment contains the dialog and dialog should be controlled from fragment interface.
                        // See: https://stackoverflow.com/a/8921129/5189200
                        errorDialogFragment.isCancelable = false
                        errorDialogFragment.show(fragmentManager, tag)
                    }
                }
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
