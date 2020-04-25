package com.hopovo.mobile.android.prepexam.app.take_photo

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hopovo.mobile.android.prepexam.app.R
import com.hopovo.mobile.android.prepexam.app.common.util.PhotoUtils
import com.hopovo.mobile.android.prepexam.model.take_photo.GolingPhoto

class PhotoListAdapter : RecyclerView.Adapter<PhotoListAdapter.ViewHolder>() {

    var photos: List<GolingPhoto> = arrayListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val photo = photos[position]
        val resizedBitmap = PhotoUtils.getResizedPhoto(photo.photoUri, PhotoUtils.IMAGE_TARGET_SIZE)
        holder.photoView.setImageBitmap(resizedBitmap)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.row_photo, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var photoView: ImageView = view.findViewById(R.id.row_photo)
    }

}