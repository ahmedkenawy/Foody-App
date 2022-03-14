package com.a7medkenawy.foody.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.a7medkenawy.foody.data.database.RecipesEntity
import com.a7medkenawy.foody.models.FoodRecipe
import com.a7medkenawy.foody.util.NetWorkResult

class RecipesBinding {

    companion object {

        @BindingAdapter("readFromApi", "readFromDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: NetWorkResult<FoodRecipe>,
            database: List<RecipesEntity>
        ) {
            if (apiResponse is NetWorkResult.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (apiResponse is NetWorkResult.Loading) {
                imageView.visibility = View.INVISIBLE
            } else {
                imageView.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("readFromApi2", "readFromDatabase2", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: NetWorkResult<FoodRecipe>,
            database: List<RecipesEntity>
        ) {
            if (apiResponse is NetWorkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
            } else if (apiResponse is NetWorkResult.Loading) {
                textView.visibility = View.INVISIBLE
            } else {
                textView.visibility = View.INVISIBLE
            }
        }
    }
}
