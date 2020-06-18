package company.tap.tapuilibrary.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.google.android.material.tabs.TabLayout
import company.tap.tapuilibrary.MetricsUtil
import company.tap.tapuilibrary.PaymentSectionItemType
import company.tap.tapuilibrary.PaymentSectionItemType.*
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.atoms.TapImageView

/**
 *
 * Created by Mario Gamal on 6/17/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapSelectionTabLayout(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private var tabLayout: TabLayout
    private var tabCards: TabLayout.Tab
    private var tabMobile: TabLayout.Tab
    private var visaTab: TapImageView
    private var masterTab: TapImageView
    private var amexTab: TapImageView
    private var zainTab: TapImageView
    private var ooredooTab: TapImageView

    init {
        inflate(context, R.layout.tap_selection_tablayout, this)
        tabLayout = findViewById(R.id.tab_layout)
        tabLayout.setSelectedTabIndicatorColor(Color.BLUE)

        tabCards = tabLayout.newTab().setCustomView(R.layout.card_section_layout)
        visaTab = tabCards.customView!!.findViewById(R.id.visa)
        masterTab = tabCards.customView!!.findViewById(R.id.master)
        amexTab = tabCards.customView!!.findViewById(R.id.amex)

        tabMobile = tabLayout.newTab().setCustomView(createDynamicSection())
        zainTab = tabMobile.customView!!.findViewById(R.id.zain)
        ooredooTab = tabMobile.customView!!.findViewById(R.id.ooredoo)

        addTabs()
        setSelectionBehaviour()
    }

    private fun createDynamicSection(): LinearLayout {
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = HORIZONTAL
        val params = LayoutParams(
            MetricsUtil.convertDpToPixel(120f, context).toInt(),
            LayoutParams.MATCH_PARENT
        )
        linearLayout.layoutParams = params
        linearLayout.addView(createImageView(R.id.zain, R.drawable.zain))
        linearLayout.addView(createImageView(R.id.ooredoo, R.drawable.ooredoo))
        return linearLayout
    }

    private fun createImageView(@IdRes id: Int, @DrawableRes image: Int): TapImageView {
        val imageView = TapImageView(context, null)
        imageView.id = id
        imageView.setImageResource(image)
        val itemWidth = Resources.getSystem().displayMetrics.widthPixels /5
        val params = LayoutParams(
            MetricsUtil.convertDpToPixel(60f, context).toInt(),
            MetricsUtil.convertDpToPixel(40f, context).toInt()
        )
        imageView.layoutParams = params
        return imageView
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

    fun selectItem(item: PaymentSectionItemType) {
        changeClickableState(false)
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT)
        changeImagesAlpha(0.7f)
        when (item) {
            VISA -> visaTab.alpha = 1f
            MASTERCARD -> masterTab.alpha = 1f
            AMEX -> amexTab.alpha = 1f
            ZAIN -> zainTab.alpha = 1f
            OOREDOO -> ooredooTab.alpha = 1f
        }
    }

    private fun changeImagesAlpha(alpha: Float) {
        visaTab.alpha = alpha
        masterTab.alpha = alpha
        amexTab.alpha = alpha
        ooredooTab.alpha = alpha
        zainTab.alpha = alpha
    }

    fun resetSelection() {
        changeClickableState(true)
        tabLayout.setSelectedTabIndicatorColor(Color.BLUE)
        changeImagesAlpha(1f)
    }

    private fun changeClickableState(enabled: Boolean) {
        val tabMobileView = tabMobile.customView!!.parent as View
        val tabCardsView = tabCards.customView!!.parent as View
        tabMobileView.isEnabled = enabled
        tabCardsView.isEnabled = enabled
    }

}