package com.isaiahvonrundstedt.bucket.features.core.file

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.isaiahvonrundstedt.bucket.constants.Firestore
import com.isaiahvonrundstedt.bucket.constants.Params
import com.isaiahvonrundstedt.bucket.features.shared.StorageItem

class FileStore (authorParams: String?) {

    private val firestore by lazy { FirebaseFirestore.getInstance() }

    private var initialQueryBatch: Query? = firestore.collection(Firestore.files).limit(15)
        .whereEqualTo(Params.authorID, authorParams)
    private var nextQueryBatch: Query? = null

    fun fetch( onFetch: (List<StorageItem>) -> Unit ){
        val mainQuery = if (nextQueryBatch == null) initialQueryBatch else nextQueryBatch

        mainQuery?.get()
            ?.addOnSuccessListener { snapshots ->
                if (snapshots.size() > 0){
                    val lastVisible = snapshots.documents[snapshots.size() - 1]

                    nextQueryBatch = firestore.collection(Firestore.files)
                        .startAfter(lastVisible)
                        .limit(15)

                    onFetch (snapshots.map { val item: StorageItem = it.toObject(StorageItem::class.java).apply { id = it.id }; item })
                } else
                    onFetch(ArrayList())
            }
    }

    fun refresh(){
        nextQueryBatch = null
    }

}