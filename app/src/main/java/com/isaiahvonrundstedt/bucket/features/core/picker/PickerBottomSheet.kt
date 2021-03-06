package com.isaiahvonrundstedt.bucket.features.core.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.isaiahvonrundstedt.bucket.R
import com.isaiahvonrundstedt.bucket.features.shared.abstracts.BaseBottomSheet
import kotlinx.android.synthetic.main.layout_sheet_picker.*

class PickerBottomSheet(private var items: List<Picker>,
                        private var itemListener: PickerListener?): BaseBottomSheet(),
    PickerListener {

    private var adapter = PickerAdapter(items, this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_sheet_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = GridLayoutManager(context, items.size)
        recyclerView.adapter = adapter
    }

    override fun onItemSelected(index: Int) {
        itemListener?.onItemSelected(index)
        this.dismiss()
    }

    object Builder {
        private var itemListener: PickerListener? = null
        private var items: List<Picker> = ArrayList()

        fun setItems(items: List<Picker>): Builder {
            Builder.items = items
            return this
        }
        fun setListener(itemListener: PickerListener): Builder {
            Builder.itemListener = itemListener
            return this
        }
        fun build(): PickerBottomSheet {
            return PickerBottomSheet(items, itemListener)
        }

    }


    private class PickerAdapter(private var items: List<Picker>,
                                listener: PickerListener
    ): RecyclerView.Adapter<PickerAdapter.ViewHolder>() {

        private var bottomSheet: PickerListener = listener

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.iconView.setImageResource(items[position].drawableID)
            viewHolder.titleView.text = viewHolder.itemView.context.getString(items[position].stringID)
            viewHolder.rowView.setOnClickListener { bottomSheet.onItemSelected(position) }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val rowView: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_picker, parent, false)
            return ViewHolder(rowView)
        }

        override fun getItemCount(): Int = items.size

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val rowView: View = itemView.findViewById(R.id.rootView)
            val iconView: AppCompatImageView = itemView.findViewById(R.id.iconView)
            val titleView: AppCompatTextView = itemView.findViewById(R.id.titleView)
        }
    }
}