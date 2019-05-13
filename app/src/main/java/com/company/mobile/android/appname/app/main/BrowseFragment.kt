package com.company.mobile.android.appname.app.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.widget.empty.EmptyListener
import com.company.mobile.android.appname.app.widget.error.ErrorListener
import kotlinx.android.synthetic.main.fragment_bufferoos.progress
import kotlinx.android.synthetic.main.fragment_bufferoos.recycler_browse
import kotlinx.android.synthetic.main.fragment_bufferoos.view_empty
import kotlinx.android.synthetic.main.fragment_bufferoos.view_error
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class BrowseFragment : Fragment() {

    private val browseAdapter: BrowseAdapter by inject()
    val browseBufferoosViewModel: BrowseBufferoosViewModel by viewModel()

    companion object {
        fun newInstance() = BrowseFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_bufferoos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // At this point, Kotlin extensions are available
        earlyInitializeViews()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeState(savedInstanceState)
        initializeViews()
        initializeContents()
    }

    /**
     * View initialization that does not depend on view models.
     */
    private fun earlyInitializeViews() {
        setupBrowseRecycler()
        setupViewListeners()
    }

    /**
     * Initializes fragment state with [androidx.lifecycle.ViewModel]s and parameters passed through [Bundle].
     */
    private fun initializeState(savedInstanceState: Bundle?) {
    }

    /**
     * View initialization that depends on view models.
     */
    private fun initializeViews() {
        browseBufferoosViewModel.getBufferoos().observe(this,
            Observer<BrowseState> {
                if (it != null) this.handleDataState(it)
            }
        )
    }

    /**
     * Initializes view contents.
     */
    private fun initializeContents() {
        browseBufferoosViewModel.fetchBufferoos()
    }

    private fun setupBrowseRecycler() {
        recycler_browse.layoutManager = LinearLayoutManager(this.context)
        recycler_browse.adapter = browseAdapter
    }

    private fun handleDataState(browseState: BrowseState) {
        when (browseState) {
            is BrowseState.Loading -> setupScreenForLoadingState()
            is BrowseState.Success -> setupScreenForSuccess(browseState.data)
            is BrowseState.Error -> setupScreenForError(browseState.errorMessage)
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
