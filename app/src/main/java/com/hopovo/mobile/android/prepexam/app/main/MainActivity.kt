package com.hopovo.mobile.android.prepexam.app.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import com.hopovo.mobile.android.prepexam.app.BuildConfig
import com.hopovo.mobile.android.prepexam.app.R
import com.hopovo.mobile.android.prepexam.app.bufferoos.master.BufferoosNavigationCommand.GoToDetailsView
import com.hopovo.mobile.android.prepexam.app.common.BaseActivity
import com.hopovo.mobile.android.prepexam.app.common.viewmodel.CommonEvent
import com.hopovo.mobile.android.prepexam.app.common.viewmodel.SingleLiveEvent
import com.hopovo.mobile.android.prepexam.app.exerciselist.ExerciseListFragment
import com.hopovo.mobile.android.prepexam.app.exerciselist.ExerciseListViewModel
import kotlinx.android.synthetic.main.navigation_drawer_main_app_bar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    companion object {
        private const val VERSION_NAME = BuildConfig.VERSION_NAME

        fun getCallingIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    private val exerciseListViewModel: ExerciseListViewModel by viewModel()
    private val commonLiveEvent = SingleLiveEvent<CommonEvent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer_main)

        initializeViews(savedInstanceState)
        initializeState(savedInstanceState)
        initializeContents(savedInstanceState)
    }

    private fun initializeContents(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            // First time initialization
        }

        exerciseListViewModel.exerciseListNavigationLiveEvent.observe(this, Observer { command ->
            when (command) {
                GoToDetailsView -> {
                }
            }
        })

        // Set commonLiveEvent channel in every view model used by this activity that inherits from CommonEventsViewModel
        exerciseListViewModel.commonLiveEvent = commonLiveEvent
    }

    private fun initializeState(@Suppress("UNUSED_PARAMETER") savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_main_container, ExerciseListFragment.newInstance())
                    .commitNow()
        }
    }

    private fun initializeViews(@Suppress("UNUSED_PARAMETER") savedInstanceState: Bundle?) {
        // Initialize toolbar
        setSupportActionBar(tb_navigation_drawer_main_toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }
}