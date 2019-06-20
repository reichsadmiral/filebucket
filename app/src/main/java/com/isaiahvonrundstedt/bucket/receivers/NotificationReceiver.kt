package com.isaiahvonrundstedt.bucket.receivers

import android.app.Application
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.isaiahvonrundstedt.bucket.R
import com.isaiahvonrundstedt.bucket.architecture.store.CollectionRepository
import com.isaiahvonrundstedt.bucket.objects.File
import com.isaiahvonrundstedt.bucket.service.BaseService
import com.isaiahvonrundstedt.bucket.utils.Preferences

class NotificationReceiver: BroadcastReceiver() {

    private var downloadID: Long? = 0L

    private lateinit var request: DownloadManager.Request
    private lateinit var downloadManager: DownloadManager

    override fun onReceive(context: Context?, intent: Intent?) {
        downloadManager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val file: File? = intent?.getParcelableExtra(BaseService.BUNDLE_ARGS)
        val externalDir: String? = Preferences(context).downloadDirectory
        val bufferedFile = java.io.File(externalDir, file?.name)

        when (intent?.action){
            BaseService.ACTION_DOWNLOAD -> {
                request = DownloadManager.Request(Uri.parse(file?.downloadURL))
                    .setTitle(context.getString(R.string.notification_downloading_file))
                    .setDestinationUri(bufferedFile.toUri())

                downloadID = downloadManager.enqueue(request)
                Log.i("RECEIVED", "Data Received")
            }
            BaseService.ACTION_SAVE -> {
                CollectionRepository(context?.applicationContext as Application).insert(file!!)
            }
        }
    }

}