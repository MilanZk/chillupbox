package com.hopovo.mobile.android.prepexam.app.exercise.master

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hopovo.mobile.android.prepexam.app.bufferoos.master.BufferoosNavigationCommand
import com.hopovo.mobile.android.prepexam.app.bufferoos.master.BufferoosNavigationCommand.GoToDetailsView
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.AppAction.GET_BUFFEROOS
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorBundleBuilder
import com.hopovo.mobile.android.prepexam.app.common.model.ResourceState
import com.hopovo.mobile.android.prepexam.app.common.model.ResourceState.*
import com.hopovo.mobile.android.prepexam.app.common.viewmodel.CommonEventsViewModel
import com.hopovo.mobile.android.prepexam.app.common.viewmodel.SingleLiveEvent
import com.hopovo.mobile.android.prepexam.domain.bufferoo.interactor.GetExercises
import com.hopovo.mobile.android.prepexam.domain.exercise.interactor.SaveExercise
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import io.reactivex.disposables.Disposable

typealias ExerciseListState = ResourceState<List<Exercise>>

class ExerciseViewModel(private val getExercisesUseCase: GetExercises,
                        private val errorBundleBuilder: ErrorBundleBuilder,
                        private val saveExercise: SaveExercise) : CommonEventsViewModel() {

    private val exerciseListLiveData: MutableLiveData<ExerciseListState> = MutableLiveData()
    private val selectedExerciseLiveData: MutableLiveData<Exercise> = MutableLiveData()
    private var exercises: List<Exercise> = emptyList()
    private var disposable: Disposable? = null
    val exerciseListNavigationLiveEvent = SingleLiveEvent<BufferoosNavigationCommand>()

    override fun onCleared() {
        disposable?.dispose()

        super.onCleared()
    }

    fun getExercises(): LiveData<ExerciseListState> {
        return exerciseListLiveData
    }

    fun getSelectedExercise(): LiveData<Exercise> {
        return selectedExerciseLiveData
    }

    fun select(id: Long) {
        selectedExerciseLiveData.value = exercises[id.toInt()]
        exerciseListNavigationLiveEvent.value = GoToDetailsView
    }

    fun openAddExercise(){
        exerciseListNavigationLiveEvent.value = GoToDetailsView
    }

    fun fetchExercises() {
        exerciseListLiveData.value = Loading()
        disposable = getExercisesUseCase.execute()
                .subscribeWith(
                        object : SingleRemoteInterceptor<List<Exercise>>(commonLiveEvent) {
                            override fun onSuccess(t: List<Exercise>) {
                                this@ExerciseViewModel.exercises = t
                                exerciseListLiveData.value = Success(this@ExerciseViewModel.exercises)
                            }

                            override fun onRegularError(e: Throwable) {
                                exerciseListLiveData.value = Error(errorBundleBuilder.build(e, GET_BUFFEROOS))
                            }
                        }
                )
    }

    fun saveExercise(exercise: Exercise) {
        disposable = saveExercise.execute(params = exercise).subscribeWith(
                object : CompletableRemoteInterceptor(commonLiveEvent) {
                    override fun onComplete() {
                        exerciseListLiveData.value = Success(this@ExerciseViewModel.exercises)
                    }

                    override fun onRegularError(e: Throwable) {
                        exerciseListLiveData.value = Error(errorBundleBuilder.build(e, GET_BUFFEROOS))
                    }
                }
        )

    }
}