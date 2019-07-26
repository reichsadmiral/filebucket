package com.isaiahvonrundstedt.bucket.architecture.viewmodel.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isaiahvonrundstedt.bucket.architecture.store.network.FileStore
import com.isaiahvonrundstedt.bucket.objects.core.StorageItem

class FileViewModel(authorParams: String?): ViewModel() {

    private val repository = FileStore(authorParams)
    private var initialList = mutableListOf<StorageItem>()
    private var _items: MutableLiveData<List<StorageItem>> = MutableLiveData()
    internal var itemList: LiveData<List<StorageItem>> = _items

    init {
        fetch()
    }

    fun fetch(){
        repository.fetch { fileList ->
            initialList.addAll(fileList)
            initialList.distinctBy { it.id }.toMutableList()
            _items.postValue(initialList)
        }
    }

    val size: Int
        get() = initialList.size

    fun refresh(){
        repository.refresh()
        initialList.clear()
        fetch()
    }
}