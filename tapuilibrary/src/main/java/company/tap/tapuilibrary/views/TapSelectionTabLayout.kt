package company.tap.tapuilibrary.views

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.google.android.material.tabs.TabLayout
import company.tap.tapcardvalidator_android.CardBrand
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.atoms.TapImageView
import company.tap.tapuilibrary.interfaces.TapSelectionTabLayoutInterface
import company.tap.tapuilibrary.models.SectionTabItem
import company.tap.tapuilibrary.utils.MetricsUtil

/**
 *
 * Created by Mario Gamal on 6/17/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapSelectionTabLayout(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private var indicatorColor = Color.parseColor(INDICATOR_COLOR)
    private var indicatorHeight = MetricsUtil.convertDpToPixel(INDICATOR_HEIGHT, context).toInt()
    private var unselectedAlphaLevel = UNSELECTED_ALPHA


    private var tabLayout: TabLayout
    private val itemsCount = ArrayList<Int>()
    private val tabsView = ArrayList<LinearLayout>()
    private val tabItems = ArrayList<SectionTabItem>()
    private var touchableList = ArrayList<View>()

    private var tabLayoutInterface: TapSelectionTabLayoutInterface? = null

    init {
        inflate(context, R.layout.tap_selection_tablayout, this)
        tabLayout = findViewById(R.id.tab_layout)
        tabLayout.setSelectedTabIndicatorColor(indicatorColor)
        tabLayout.setSelectedTabIndicatorHeight(indicatorHeight)
        setSelectionBehaviour()
    }

    fun setTabLayoutInterface(tabInterface: TapSelectionTabLayoutInterface) {
        this.tabLayoutInterface = tabInterface
    }

    fun setTabIndicatorColor(@ColorInt color: Int) {
        indicatorColor = color
        tabLayout.setSelectedTabIndicatorColor(color)
    }

    fun setTabIndicatorHeight(height: Int) {
        indicatorHeight = height
        tabLayout.setSelectedTabIndicatorHeight(height)
    }

    fun setTabRippleColor(@ColorInt color: Int) {
        tabLayout.tabRippleColor = ColorStateList.valueOf(color)
    }

    fun setTabRippleColorResource(@ColorRes tabRippleColorResourceId: Int) {
        tabLayout.setTabRippleColorResource(tabRippleColorResourceId)
    }

    fun setUnselectedAlphaLevel(level: Float) {
        unselectedAlphaLevel = level
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
            sectionLayout.alpha = unselectedAlphaLevel
        tabsView.add(sectionLayout)
        val sectionTab = tabLayout.newTab().setCustomView(sectionLayout)
        tabLayout.addTab(sectionTab)
    }

    private fun editExistItemsSize() {
        val params = LayoutParams(
            getItemWidth(), 0
        )
        params.setMargins(0, 30, 0, 30)
        params.weight = 1f
        for (item in tabItems) {
            item.imageView?.layoutParams = params
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

    private fun getSectionItem(item: SectionTabItem): LinearLayout {
        val layout = getSectionItemLayout()
        val indicator = getTabSelectionIndicator()
        val params = LayoutParams(
            getItemWidth(), 0
        )
        params.setMargins(0, 30, 0, 30)
        params.weight = 1f
        val image = TapImageView(context, null)
        image.setImageDrawable(item.selectedImage)
        image.layoutParams = params

        item.imageView = image
        item.indicator = indicator
        tabItems.add(item)

        layout.addView(image)
        layout.addView(indicator)
        return layout
    }

    private fun getSectionItemLayout(): LinearLayout {
        val linearLayout = LinearLayout(context)
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        )
        linearLayout.layoutParams = params
        linearLayout.orientation = VERTICAL
        linearLayout.weightSum = 1f
        return linearLayout
    }

    private fun getTabSelectionIndicator(): View {
        val view = View(context)
        val params = LayoutParams(
            LayoutParams.MATCH_PARENT,
            indicatorHeight
        )
        view.setBackgroundColor(indicatorColor)
        view.layoutParams = params
        view.visibility = View.INVISIBLE
        return view
    }

    private fun getItemWidth(): Int {
        var totalItemsCount = 0
        for (items in itemsCount) {
            totalItemsCount += items
        }
        return (Resources.getSystem().displayMetrics.widthPixels - SCREEN_MARGINS) / totalItemsCount
    }

    private fun setSelectionBehaviour() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                fadeOtherTabs(tab?.position)
                tabLayoutInterface?.onSelectedTabChanged(tab?.position)
            }
        })
    }

    private fun fadeOtherTabs(position: Int?) {
        tabsView.forEachIndexed { index, view ->
            if (index == position)
                view.alpha = 1f
            else
                view.alpha = unselectedAlphaLevel
        }
    }

    fun selectTab(type: CardBrand) {
        resetBehaviour()
        changeClickableState(false)
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT)
        tabItems.forEach {
            if (it.type != type) {
                it.imageView?.setImageDrawable(it.unSelectedImage)
                it.indicator?.visibility = View.INVISIBLE
            } else {
                it.imageView?.setImageDrawable(it.selectedImage)
                it.indicator?.visibility = View.VISIBLE
            }
        }
    }

    fun resetBehaviour() {
        changeClickableState(true)
        tabLayout.setSelectedTabIndicatorColor(indicatorColor)
        tabItems.forEach {
            it.imageView?.setImageDrawable(it.selectedImage)
            it.indicator?.visibility = View.INVISIBLE
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

    companion object {
        const val SCREEN_MARGINS = 140
        const val INDICATOR_HEIGHT = 2f
        const val INDICATOR_COLOR = "#2ace00"
        const val UNSELECTED_ALPHA = 0.7f
    }
}