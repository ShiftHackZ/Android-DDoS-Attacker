package com.shifthackz.android.attacker.presentation.widget

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class DropdownAdapter<T>(
    context: Context,
    layout: Int,
    var values: Array<T>
) : ArrayAdapter<T>(context, layout, values) {

    private val dropdownFilter = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            results.values = values
            results.count = values.size
            return results
        }
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter = dropdownFilter

}