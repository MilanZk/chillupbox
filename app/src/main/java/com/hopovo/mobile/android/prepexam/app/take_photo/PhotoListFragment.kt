package com.hopovo.mobile.android.prepexam.app.take_photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.hopovo.mobile.android.prepexam.app.R
import com.hopovo.mobile.android.prepexam.app.common.BaseFragment
import com.hopovo.mobile.android.prepexam.model.take_photo.GolingPhoto
import kotlinx.android.synthetic.main.fragment_photo_list.*
import org.koin.android.ext.android.inject

import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class PhotoListFragment : BaseFragment() {

    private val takePhotoViewModel: TakePhotoViewModel by sharedViewModel()
    private val photoListAdapter: PhotoListAdapter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_photo_list, container, false)
    }

    override fun earlyInitializeViews() {
        super.earlyInitializeViews()

        val gridLayoutManager = GridLayoutManager(activity, 5, GridLayoutManager.HORIZONTAL, false)
        rv_photos.layoutManager = gridLayoutManager
        rv_photos.adapter = photoListAdapter

    }

    override fun initializeContents(savedInstanceState: Bundle?) {
        super.initializeContents(savedInstanceState)

        takePhotoViewModel.getPhotos().observe(viewLifecycleOwner, Observer {
            setPhotos(it)
        })

        if(takePhotoViewModel.getPhotos().value == null){
            takePhotoViewModel.fetchPhotos()
        }
    }

    private fun setPhotos(photos: List<GolingPhoto>) {
        photoListAdapter.photos = photos
        photoListAdapter.notifyDataSetChanged()
    }

}
