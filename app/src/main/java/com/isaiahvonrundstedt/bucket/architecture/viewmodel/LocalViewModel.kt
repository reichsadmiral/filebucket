package com.isaiahvonrundstedt.bucket.architecture.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.isaiahvonrundstedt.bucket.architecture.store.LocalStore
import com.isaiahvonrundstedt.bucket.objects.core.StorageItem

class LocalViewModel(app: Application): AndroidViewModel(app){

    private val localStore: LocalStore = LocalStore(app)

    private val initialList: ArrayList<StorageItem> = ArrayList()
    private val _itemList: MutableLiveData<List<StorageItem>> = MutableLiveData()
    internal var itemList: LiveData<List<StorageItem>> = _itemList

    private val _itemSize: MutableLiveData<Int> = MutableLiveData()
    internal var itemSize: LiveData<Int> = _itemSize

    init {
        fetch()
    }

    private fun fetch() {
        localStore.fetch { items ->
            initialList.addAll(items)
            initialList.distinctBy { it.id }.toMutableList()
            _itemList.postValue(initialList)
            _itemSize.postValue(initialList.size)
        }
    }

}