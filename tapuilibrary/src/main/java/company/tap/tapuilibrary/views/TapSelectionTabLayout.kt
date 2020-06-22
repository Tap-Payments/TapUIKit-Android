package company.tap.tapuilibrary.views

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.doOnLayout
import com.google.android.material.tabs.TabLayout
import company.tap.tapcardvalidator_android.CardBrand
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.SectionTabItem
import company.tap.tapuilibrary.atoms.TapImageView

/**
 *
 * Created by Mario Gamal on 6/17/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapSelectionTabLayout(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private var indicatorColor = Color.parseColor("#2ace00")
    private var tabLayout: TabLayout
    private val itemsCount = ArrayList<Int>()
    private val tabsView = ArrayList<LinearLayout>()
    private val tabItems = ArrayList<SectionTabItem>()
    private var touchableList = ArrayList<View>()

    init {
        inflate(context, R.layout.tap_selection_tablayout, this)
        tabLayout = findViewById(R.id.tab_layout)
        tabLayout.setSelectedTabIndicatorColor(indicatorColor)
        setSelectionBehaviour()
    }

    fun addSection(items: ArrayList<SectionTabItem>) {
        itemsCount.add(items.size)
        if (itemsCount.size > 1)
            editExistItemsSize()
        val sectionLayout = getSectionLayout()
        for (item in items) {
            sectionLayout.addView(getSectionItem(item))
        }
        if (tabsView.size != 0)
            sectionLayout.alpha = 0.7f
        tabsView.add(sectionLayout)
        val sectionTab = tabLayout.newTab().setCustomView(sectionLayout)
        tabLayout.addTab(sectionTab)
    }

    private fun editExistItemsSize() {
        val params = LayoutParams(
            getItemWidth(),
            LayoutParams.MATCH_PARENT
        )
        params.setMargins(0,30,0,30)
        for (item in tabItems) {
            item.view?.layoutParams = params
        }
    }

    private fun getSectionLayout(): LinearLayout {
        val linearLayout = LinearLayout(context)
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        )
        linearLayout.layoutParams = params
        linearLayout.orientation = HORIZONTAL
        return linearLayout
    }

    private fun getSectionItem(item: SectionTabItem): ImageView {
        val params = LayoutParams(
            getItemWidth(),
            LayoutParams.MATCH_PARENT
        )
        params.setMargins(0,30,0,30)
        val image = TapImageView(context, null)
        image.setImageDrawable(item.selectedImage)
        image.layoutParams = params
        item.view = image
        tabItems.add(item)
        return image
    }

    private fun getItemWidth(): Int {
        var count = 0
        for (items in itemsCount) {
            count += items
        }
        return (Resources.getSystem().displayMetrics.widthPixels - 140) / count
    }

    private fun setSelectionBehaviour() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                fadeOtherTabs(tab?.position)
            }
        })
    }

    private fun fadeOtherTabs(position: Int?) {
        tabsView.forEachIndexed { index, view ->
            if (index == position)
                view.alpha = 1f
            else
                view.alpha = 0.7f
        }
    }

    fun selectTab(type: CardBrand) {
        changeClickableState(false)
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT)
        tabItems.forEach {
            if (it.type != type)
                it.view?.setImageDrawable(it.unSelectedImage)
        }
    }

    fun resetBehaviour() {
        changeClickableState(true)
        tabLayout.setSelectedTabIndicatorColor(indicatorColor)
        tabItems.forEach {
            it.view?.setImageDrawable(it.selectedImage)
        }
    }

    private fun changeClickableState(isClickable: Boolean) {
        if (isClickable) {
            touchableList.forEach { it.isEnabled = isClickable }
        } else {
            touchableList = tabLayout.touchables
            touchableList.forEach { it.isEnabled = isClickable }
        }
    }


//    private fun createDynamicSection(): LinearLayout {
//        linearLayout.orientation = HORIZONTAL
//        val params = LayoutParams(
//            MetricsUtil.convertDpToPixel(120f, context).toInt(),
//            LayoutParams.MATCH_PARENT
//        )
//        linearLayout.layoutParams = params
//        linearLayout.addView(createImageView(R.id.zain, R.drawable.zain))
//        linearLayout.addView(createImageView(R.id.ooredoo, R.drawable.ooredoo))
//        return linearLayout
//    }
//
//
//
//    private fun createImageView(@IdRes id: Int, @DrawableRes image: Int): TapImageView {
//        val imageView = TapImageView(context, null)
//        imageView.id = id
//        imageView.setImageResource(image)
//        val itemWidth =
//        val params = LayoutParams(
//            MetricsUtil.convertDpToPixel(60f, context).toInt(),
//            MetricsUtil.convertDpToPixel(40f, context).toInt()
//        )
//        imageView.layoutParams = params
//        return imageView
//    }
//
//    private fun addTabs() {
//        tabMobile.customView?.alpha = 0.7f
//        tabLayout.addTab(tabCards)
//        tabLayout.addTab(tabMobile)
//    }
//
//
//    fun selectItem(item: PaymentSectionItemType) {
//        changeClickableState(false)
//        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT)
//        changeImagesAlpha(0.7f)
//        when (item) {
//            VISA -> visaTab.alpha = 1f
//            MASTERCARD -> masterTab.alpha = 1f
//            AMEX -> amexTab.alpha = 1f
//            ZAIN -> zainTab.alpha = 1f
//            OOREDOO -> ooredooTab.alpha = 1f
//        }
//    }
//
//
//    fun resetSelection() {
//        changeClickableState(true)
//        tabLayout.setSelectedTabIndicatorColor(Color.BLUE)
//        changeImagesAlpha(1f)
//    }
//
//    private fun changeClickableState(enabled: Boolean) {
//
//
//    val tabMobileView = tabMobile.customView!!.parent as View
//        val tabCardsView = tabCards.customView!!.parent as View
//        tabMobileView.isEnabled = enabled
//        tabCardsView.isEnabled = enabled
//    }
}