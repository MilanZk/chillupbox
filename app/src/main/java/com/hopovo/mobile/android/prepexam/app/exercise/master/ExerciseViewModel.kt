package com.hopovo.mobile.android.prepexam.app.exercise.master

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.AppAction.GET_BUFFEROOS
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorBundleBuilder
import com.hopovo.mobile.android.prepexam.app.common.model.ResourceState
import com.hopovo.mobile.android.prepexam.app.common.model.ResourceState.*
import com.hopovo.mobile.android.prepexam.app.common.viewmodel.SingleLiveEvent
import com.hopovo.mobile.android.prepexam.app.exercise.ExerciseCommand
import com.hopovo.mobile.android.prepexam.domain.exercise.interactor.GetExerciseById
import com.hopovo.mobile.android.prepexam.domain.exercise.interactor.GetExercises
import com.hopovo.mobile.android.prepexam.domain.exercise.interactor.SaveExercise
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import io.reactivex.disposables.Disposable

typealias ExerciseListState = ResourceState<List<Exercise>>
typealias ExerciseState = ResourceState<Exercise>

class ExerciseViewModel(private val getExercises: GetExercises,
                        private val errorBundleBuilder: ErrorBundleBuilder,
                        private val saveExercise: SaveExercise,
                        private val getExerciseById: GetExerciseById) : ViewModel() {

    private val exerciseListLiveData: MutableLiveData<ExerciseListState> = MutableLiveData()
    private val selectedExerciseLiveData: MutableLiveData<ExerciseState> = MutableLiveData()
    private val exerciseSingleLiveEvent: SingleLiveEvent<ExerciseCommand> = SingleLiveEvent()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()

        super.onCleared()
    }

    fun getExercises(): LiveData<ExerciseListState> {
        return exerciseListLiveData
    }

    fun getExerciseSingleLiveEvent(): LiveData<ExerciseCommand> {
        return exerciseSingleLiveEvent
    }

    fun getSelectedExercise(): LiveData<ExerciseState> {
        return selectedExerciseLiveData
    }

    fun select(id: Int) {
        disposable = getExerciseById.execute(id).subscribe({
            selectedExerciseLiveData.value = Success(it)
        }, {
            selectedExerciseLiveData.value = Error(errorBundleBuilder.build(it, GET_BUFFEROOS))
        })
    }

    fun fetchExercises() {
        exerciseListLiveData.value = Loading()
        disposable = getExercises.execute().subscribe({
            exerciseListLiveData.value = Success(it)
        }, {
            exerciseListLiveData.value = Error(errorBundleBuilder.build(it, GET_BUFFEROOS))

        })
    }

    fun saveExercise(exercise: Exercise) {
        disposable = saveExercise.execute(exercise)
                .subscribe({
                    exerciseSingleLiveEvent.value = ExerciseCommand.ExerciseAddingSuccess
                }, {
                    exerciseSingleLiveEvent.value = ExerciseCommand.ExerciseAddingError
                })

    }

    fun savePhoto(stringUri: String) {

    }
}