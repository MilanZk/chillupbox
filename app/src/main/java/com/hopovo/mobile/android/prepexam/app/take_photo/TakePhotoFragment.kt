package com.hopovo.mobile.android.prepexam.app.take_photo

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.hopovo.mobile.android.prepexam.app.R
import com.hopovo.mobile.android.prepexam.app.common.BaseFragment
import com.hopovo.mobile.android.prepexam.app.common.util.PhotoUtils
import kotlinx.android.synthetic.main.fragment_take_photo.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class TakePhotoFragment : BaseFragment() {

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        private const val PERMISSION_CODE = 1
        private const val REQUEST_IMAGE_CAPTURE = 2
    }

    private val takePhotoViewModel: TakePhotoViewModel by sharedViewModel()
    lateinit var currentPhotoPath: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_take_photo, container, false)
    }

    override fun earlyInitializeViews() {
        super.earlyInitializeViews()

        bt_take_photo.setOnClickListener {
            requestCameraPermissions()
        }

        bt_save_photo.setOnClickListener {
            currentPhotoPath?.let {
                takePhotoViewModel.savePhoto(it)
            }
        }
    }

    override fun initializeContents(savedInstanceState: Bundle?) {
        super.initializeContents(savedInstanceState)
    }

    private fun hasAllPermission() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(context!!, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermissions() {
        if (hasAllPermission()) {
            dispatchTakePictureIntent()
            /*     photo_texture_view.post {
                  bindCamera()
                 updateTextureView()
            }*/
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, PERMISSION_CODE)
        }
    }

/*    @SuppressLint("RestrictedApi")
    private fun bindCamera() {
        CameraX.initialize(context!!, Camera2Config.defaultConfig())
        CameraX.unbindAll()
        val previewUseCase = createPreviewUseCase()
        CameraX.bindToLifecycle(viewLifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, previewUseCase)
    }*/

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_CODE) {
            if (hasAllPermission()) {
                //   photo_texture_view.post { bindCamera() }

            } else {
                // TODO Ask for permission again
                Toast.makeText(context, "Camera permission not granted!", Toast.LENGTH_LONG).show()
                activity?.onBackPressed()
            }
        }
    }

/*
    private fun updateTextureView() {
        val centerY = photo_texture_view.height / 2f
        val centerX = photo_texture_view.width / 2f
        val matrix = Matrix()

        val rotationDegrees = when (photo_texture_view.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }

        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)
        photo_texture_view.setTransform(matrix)
    }

    private fun createPreviewUseCase(): Preview {
        return Preview.Builder().apply {
            setTargetResolution(Size(photo_texture_view.width, photo_texture_view.height))
        }.build()
    }
*/

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(activity?.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Timber.e(ex)
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                  val photoURI = FileProvider.getUriForFile(
                            context!!,
                            "com.hopovo.mobile.android.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val resizedBitmap = PhotoUtils.getResizedPhoto(currentPhotoPath, PhotoUtils.IMAGE_TARGET_SIZE)
            photo_texture_view.setImageBitmap(resizedBitmap)
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            context?.sendBroadcast(mediaScanIntent)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


}
