package com.hopovo.mobile.android.prepexam.app.bufferoos.master

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.hopovo.mobile.android.prepexam.app.R
import com.hopovo.mobile.android.prepexam.app.di.applicationModule
import com.hopovo.mobile.android.prepexam.app.di.exerciseListModule
import com.hopovo.mobile.android.prepexam.app.test.util.BufferooFactory
import com.hopovo.mobile.android.prepexam.app.test.util.RecyclerViewMatcher
import com.hopovo.mobile.android.prepexam.domain.bufferoo.repository.BufferooRepository
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.*
import org.junit.runner.*
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock

@RunWith(AndroidJUnit4::class)
class ExerciseListFragmentTest : KoinTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule<NavigationDrawerMainActivity>(NavigationDrawerMainActivity::class.java, false, false)

    @Before
    fun setUp() {
        loadKoinModules(listOf(applicationModule, exerciseListModule))
    }

    @Test
    fun activityLaunches() {
        stubBufferooRepositoryGetBufferoos(Single.just(BufferooFactory.makeBufferooList(2)))
        activity.launchActivity(null)
    }

    @Test
    fun bufferoosDisplay() {
        val bufferoos = BufferooFactory.makeBufferooList(1)
        stubBufferooRepositoryGetBufferoos(Single.just(bufferoos))
        activity.launchActivity(null)

        checkBufferooDetailsDisplay(bufferoos[0], 0)
    }

    @Test
    fun bufferoosAreScrollable() {
        val bufferoos = BufferooFactory.makeBufferooList(20)
        stubBufferooRepositoryGetBufferoos(Single.just(bufferoos))
        activity.launchActivity(null)

        bufferoos.forEachIndexed { index, bufferoo ->
            onView(withId(R.id.rv_bufferoos)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(index)
            )
            checkBufferooDetailsDisplay(bufferoo, index)
        }
    }

    private fun checkBufferooDetailsDisplay(exercise: Exercise, position: Int) {
        onView(RecyclerViewMatcher.withRecyclerView(R.id.rv_bufferoos).atPosition(position))
            .check(matches(hasDescendant(withText(exercise.description))))
        onView(RecyclerViewMatcher.withRecyclerView(R.id.rv_bufferoos).atPosition(position))
            .check(matches(hasDescendant(withText(exercise.word))))
    }

    private fun stubBufferooRepositoryGetBufferoos(single: Single<List<Exercise>>) {
        // Mock is declared with a stubbing function in order to allow that each repository instance created by the
        // Koin factory is properly mocked and getBufferos() returns test data.
        declareMock<BufferooRepository> {
            whenever(this.getBufferoos()).thenReturn(single)
        }
    }
}