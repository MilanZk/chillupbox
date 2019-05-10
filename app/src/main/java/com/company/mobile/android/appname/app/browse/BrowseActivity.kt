package com.company.mobile.android.appname.app.browse

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.browse.BrowseState.Error
import com.company.mobile.android.appname.app.browse.BrowseState.Loading
import com.company.mobile.android.appname.app.browse.BrowseState.Success
import com.company.mobile.android.appname.app.widget.empty.EmptyListener
import com.company.mobile.android.appname.app.widget.error.ErrorListener
import com.company.mobile.android.appname.data.browse.Bufferoo
import kotlinx.android.synthetic.main.activity_browse.progress
import kotlinx.android.synthetic.main.activity_browse.recycler_browse
import kotlinx.android.synthetic.main.activity_browse.view_empty
import kotlinx.android.synthetic.main.activity_browse.view_error
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ext.android.bindScope
import org.koin.androidx.scope.ext.android.getOrCreateScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class BrowseActivity : AppCompatActivity() {

    private val SCOPE_NAME = (this::class.java.canonicalName ?: "BrowseActivity") + hashCode()

    private val browseAdapter: BrowseAdapter by inject()
    val browseBufferoosViewModel: BrowseBufferoosViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        bindScope(getOrCreateScope(SCOPE_NAME))

        setupBrowseRecycler()
        setupViewListeners()

        browseBufferoosViewModel.getBufferoos().observe(this,
            Observer<BrowseState> {
                if (it != null) this.handleDataState(it)
            })
        browseBufferoosViewModel.fetchBufferoos()
    }

    private fun setupBrowseRecycler() {
        recycler_browse.layoutManager = LinearLayoutManager(this)
        recycler_browse.adapter = browseAdapter
    }

    private fun handleDataState(browseState: BrowseState) {
        when (browseState) {
            is Loading -> setupScreenForLoadingState()
            is Success -> setupScreenForSuccess(browseState.data)
            is Error -> setupScreenForError(browseState.errorMessage)
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<Bufferoo>?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            recycler_browse.visibility = View.VISIBLE
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(bufferoos: List<Bufferoo>) {
        browseAdapter.bufferoos = bufferoos
        browseAdapter.notifyDataSetChanged()
    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.VISIBLE
        Timber.e(message)
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            browseBufferoosViewModel.fetchBufferoos()
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            browseBufferoosViewModel.fetchBufferoos()
        }
    }
}