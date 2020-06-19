package company.tap.tapuilibrary.views

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.doOnLayout
import com.google.android.material.tabs.TabLayout
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

    private var tabLayout: TabLayout
    private val itemsCount = ArrayList<Int>()
    private val tabItems = ArrayList<ImageView>()

    init {
        inflate(context, R.layout.tap_selection_tablayout, this)
        tabLayout = findViewById(R.id.tab_layout)
        tabLayout.setSelectedTabIndicatorColor(Color.BLUE)
    }

    fun addSection(items: ArrayList<SectionTabItem>) {
        itemsCount.add(items.size)
        if (itemsCount.size > 1)
            editExistItemsSize()
        val sectionLayout = getSectionLayout()
        for (item in items) {
            sectionLayout.addView(getSectionItem(item))
        }
        val sectionTab = tabLayout.newTab().setCustomView(sectionLayout)
        tabLayout.addTab(sectionTab)
    }

    private fun editExistItemsSize() {
        val params = LayoutParams(
            getItemWidth(),
            LayoutParams.MATCH_PARENT
        )
        params.setMargins(0,20,0,20)
        for (item in tabItems) {
            item.layoutParams = params
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
        params.setMargins(0,20,0,20)
        val image = TapImageView(context, null)
        image.setImageDrawable(item.image)
        image.layoutParams = params
        tabItems.add(image)
        return image
    }

    private fun getItemWidth(): Int {
        var count = 0
        for (items in itemsCount) {
            count += items
        }
        return (Resources.getSystem().displayMetrics.widthPixels - 130) / count
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
//    private fun setSelectionBehaviour() {
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabReselected(tab: TabLayout.Tab?) {}
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                tab?.customView?.alpha = 0.7f
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab?.customView?.alpha = 1f
//            }
//        })
//    }
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
//    private fun changeImagesAlpha(alpha: Float) {
//        visaTab.alpha = alpha
//        masterTab.alpha = alpha
//        amexTab.alpha = alpha
//        ooredooTab.alpha = alpha
//        zainTab.alpha = alpha
//    }
//
//    fun resetSelection() {
//        changeClickableState(true)
//        tabLayout.setSelectedTabIndicatorColor(Color.BLUE)
//        changeImagesAlpha(1f)
//    }
//
//    private fun changeClickableState(enabled: Boolean) {
//        val tabMobileView = tabMobile.customView!!.parent as View
//        val tabCardsView = tabCards.customView!!.parent as View
//        tabMobileView.isEnabled = enabled
//        tabCardsView.isEnabled = enabled
//    }

}