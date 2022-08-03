package com.a7medkenawy.foody.util

import androidx.recyclerview.widget.DiffUtil
import com.a7medkenawy.foody.models.Result

class RecipesDiffUtil<T>(var oldList: List<T>, var newList: List<T>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] === newList[newItemPosition]
}