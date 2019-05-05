package net.minpro.countermemo.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.davemorrissey.labs.subscaleview.ImageSource
import kotlinx.android.synthetic.main.activity_photo.*
import kotlinx.android.synthetic.main.content_photo.*
import net.minpro.countermemo.R
import net.minpro.countermemo.util.RQ_CODE_GALLERY
import net.minpro.countermemo.util.RQ_CODE_PERMISSION

class PhotoActivity : AppCompatActivity() {

    val PERMISSION = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    var isWriteStorageEnabled = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        setSupportActionBar(toolbar)

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
            setNavigationOnClickListener {
                finish()
            }
        }

        if (Build.VERSION.SDK_INT >= 23) permissionCheck() else accessGallery()
    }

    private fun permissionCheck() {
        val permissionCheckWriteStorage: Int = ContextCompat.checkSelfPermission(this@PhotoActivity,PERMISSION[0])

        isWriteStorageEnabled = (permissionCheckWriteStorage == PackageManager.PERMISSION_GRANTED)

        if (isWriteStorageEnabled) accessGallery() else permissionRequest()
    }

    private fun permissionRequest() {
        val isNeedExplainForWriteStoragePermission
            = ActivityCompat.shouldShowRequestPermissionRationale(this@PhotoActivity, PERMISSION[0])

        //許可をリクエストするパーミッションを入れるリストの設定
        val requestPermissionList = ArrayList<String>()

        //許可されていないパーミッションをリクエストのリストに入れる
        if (!isWriteStorageEnabled) requestPermissionList.add(PERMISSION[0])

        //説明が不要な場合
        if (!isNeedExplainForWriteStoragePermission) {
            ActivityCompat.requestPermissions(
                this@PhotoActivity,
                requestPermissionList.toArray(arrayOfNulls(requestPermissionList.size)),
                RQ_CODE_PERMISSION
            )
            return
        }

        //説明が必要な場合
        val dialog = AlertDialog.Builder(this@PhotoActivity).apply {
            setTitle(getString(R.string.permission_request_title))
            setMessage(getString(R.string.permission_request_message))
            setPositiveButton(getString(R.string.admit)) { dialogInterface, i ->
                ActivityCompat.requestPermissions(
                    this@PhotoActivity,
                    requestPermissionList.toArray(arrayOfNulls(requestPermissionList.size)),
                    RQ_CODE_PERMISSION
                )
            }
            setNegativeButton(getString(R.string.reject)) { dialogInterFace, i ->
                finish()
            }
            show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != RQ_CODE_PERMISSION) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        if (grantResults.isEmpty()) return

        for (i in 0 until permissions.size) {
            when (permissions[i]) {
                PERMISSION[0] -> {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        finish()
                        return
                    }
                    isWriteStorageEnabled = true
                }
            }
        }

        if (isWriteStorageEnabled) accessGallery() else finish()
    }


    private fun accessGallery() {

        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/jpeg"
        }

        startActivityForResult(intent, RQ_CODE_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != RQ_CODE_GALLERY) {
            return
        }

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        val resultUri = data?.data ?: return

        selectedPhoto.setImage(ImageSource.uri(resultUri))
    }
}
