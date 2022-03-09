package com.a7medkenawy.foody.util

import androidx.recyclerview.widget.DiffUtil
import com.a7medkenawy.foody.models.Result

class RecipesDiffUtil(var oldList: List<Result>, var newList: List<Result>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] === newList[newItemPosition]
}