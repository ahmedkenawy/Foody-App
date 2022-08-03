package com.a7medkenawy.foody.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.a7medkenawy.foody.R
import com.a7medkenawy.foody.adapter.pageradapter.PagerAdapter
import com.a7medkenawy.foody.ui.fragments.detailsfragments.ingredients.IngredientsFragment
import com.a7medkenawy.foody.ui.fragments.detailsfragments.instructions.InstructionsFragment
import com.a7medkenawy.foody.ui.fragments.detailsfragments.overview.OverViewFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val fragments = ArrayList<Fragment>()
        fragments.add(OverViewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("OVERVIEW")
        titles.add("INGREDIENTS")
        titles.add("INSTRUCTIONS")


        val resultBundle = Bundle()
        resultBundle.putParcelable("recipesBundle", args.result)

        val pagerAdapter = PagerAdapter(resultBundle, fragments, titles, this)

        viewPager.adapter = pagerAdapter
        viewPager.currentItem = 0
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = pagerAdapter.getTabTitle(position)
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}