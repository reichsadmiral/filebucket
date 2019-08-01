package com.isaiahvonrundstedt.bucket.architecture.store.room

import android.app.Application
import com.isaiahvonrundstedt.bucket.architecture.database.AppDatabase
import com.isaiahvonrundstedt.bucket.objects.core.StorageItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SavedStore (application: Application){

    private var database = AppDatabase.getDatabase(application)
    private var savedDAO = database?.saved()

    fun fetch(onFetch:(List<StorageItem>) -> Unit) = GlobalScope.launch {
        onFetch(savedDAO?.storageItems() ?: ArrayList())
    }

    fun insert(item: StorageItem) = GlobalScope.launch {
        savedDAO?.insert(item)
    }

    fun update(item: StorageItem) = GlobalScope.launch {
        savedDAO?.update(item)
    }

    fun remove(item: StorageItem) = GlobalScope.launch {
        savedDAO?.remove(item)
    }

}