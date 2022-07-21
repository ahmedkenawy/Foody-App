package com.a7medkenawy.foody.ui.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.a7medkenawy.foody.R
import com.a7medkenawy.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.a7medkenawy.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.a7medkenawy.foody.viewmodels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.recipes_bottom_sheet.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.log


class RecipesBottomSheet : BottomSheetDialogFragment() {
    private lateinit var recipesViewModel: RecipesViewModel

    private var mealType = DEFAULT_MEAL_TYPE
    private var mealTypeId = 0
    private var dietType = DEFAULT_DIET_TYPE
    private var dietTypeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val mView = inflater.inflate(R.layout.recipes_bottom_sheet, container, false)

        recipesViewModel.readMealTypeAndDiet.asLiveData().observe(viewLifecycleOwner) { value ->
            mealType = value.selectedMealType
            mealTypeId = value.selectedMealTypeId
            dietType = value.selectedDietType
            dietTypeId = value.selectedDietTypeId

            updateChipGroup(mealTypeId, mView.mealChipGroup)
            updateChipGroup(dietTypeId, mView.dietChipGroup)
        }


        mView.mealChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            mealType = selectedMealType
            mealTypeId = checkedId
        }


        mView.dietChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
            dietType = selectedDietType
            dietTypeId = checkedId
        }

        mView.apply_btn.setOnClickListener {
            recipesViewModel.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
            val action =
                RecipesBottomSheetDirections.actionRecipesBottomSheetToFoodRecipesFragment(true)
            findNavController().navigate(action)
        }



        return mView
    }

    private fun updateChipGroup(id: Int, chipGroup: ChipGroup?) {
        if (id != 0) {
            try {
                chipGroup!!.findViewById<Chip>(id).isChecked = true
            } catch (exception: Exception) {
                Log.d("ChipGroupError", "عندك ايرور يا محترم")
            }
        }
    }

}