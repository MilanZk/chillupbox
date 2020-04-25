package com.hopovo.mobile.android.prepexam.app.exercise

sealed class ExerciseCommand {

    object ExerciseAddingSuccess : ExerciseCommand()
    object ExerciseAddingError : ExerciseCommand()
}