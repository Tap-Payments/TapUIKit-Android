package company.tap.tapuilibrary.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.google.android.material.tabs.TabLayout
import company.tap.tapuilibrary.R

/**
 *
 * Created by Mario Gamal on 6/17/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapSelectionTabLayout (context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var tabLayout: TabLayout
    private var tabCards: TabLayout.Tab
    private var tabMobile: TabLayout.Tab

    init {
        inflate(context, R.layout.tap_selection_tablayout, this)
        tabLayout = findViewById(R.id.tab_layout)
        tabCards = tabLayout.newTab().setCustomView(R.layout.card_section_layout)
        tabMobile = tabLayout.newTab().setCustomView(R.layout.mobile_section_layout)
        addTabs()
        setSelectionBehaviour()
    }

    private fun addTabs() {
        tabMobile.customView?.alpha = 0.7f
        tabLayout.addTab(tabCards)
        tabLayout.addTab(tabMobile)
    }

    private fun setSelectionBehaviour() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.alpha = 0.7f
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.alpha = 1f
            }

        })
    }

}