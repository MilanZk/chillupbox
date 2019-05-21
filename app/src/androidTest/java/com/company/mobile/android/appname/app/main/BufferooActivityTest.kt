package com.company.mobile.android.appname.app.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.di.applicationModule
import com.company.mobile.android.appname.app.di.bufferoosModule
import com.company.mobile.android.appname.app.test.util.BufferooFactory
import com.company.mobile.android.appname.app.test.util.RecyclerViewMatcher
import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.domain.bufferoo.repository.BufferooRepository
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.*
import org.junit.runner.*
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock

@RunWith(AndroidJUnit4::class)
class BufferooActivityTest : KoinTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @Before
    fun setUp() {
        loadKoinModules(applicationModule, bufferoosModule)
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

    private fun checkBufferooDetailsDisplay(bufferoo: Bufferoo, position: Int) {
        onView(RecyclerViewMatcher.withRecyclerView(R.id.rv_bufferoos).atPosition(position))
            .check(matches(hasDescendant(withText(bufferoo.name))))
        onView(RecyclerViewMatcher.withRecyclerView(R.id.rv_bufferoos).atPosition(position))
            .check(matches(hasDescendant(withText(bufferoo.title))))
    }

    private fun stubBufferooRepositoryGetBufferoos(single: Single<List<Bufferoo>>) {
        // Mock is declared with a stubbing function in order to allow that each repository instance created by the
        // Koin factory is properly mocked and getBufferos() returns test data.
        declareMock<BufferooRepository> {
            whenever(this.getBufferoos()).thenReturn(single)
        }
    }
}