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
    private var invalidIndicatorColor = Color.parseColor(INVALID_INDICATOR_COLOR)
    private var indicatorHeight = MetricsUtil.convertDpToPixel(INDICATOR_HEIGHT, context).toInt()
    private var unselectedAlphaLevel = UNSELECTED_ALPHA
    private var maxItemWidth = MetricsUtil.convertDpToPixel(MAX_ITEM_WIDTH, context).toInt()
    private var tabLayout: TabLayout
    private val itemsCount = ArrayList<Int>()
    private val tabsView = ArrayList<LinearLayout>()
    private val tabItems = ArrayList<SectionTabItem>()
    private var touchableList = ArrayList<View>()

    private var tabLayoutInterface: TapSelectionTabLayoutInterface? = null

    /**
     * Initiating the tablayout with default theme and behaviour
     */
    init {
        inflate(context, R.layout.tap_selection_tablayout, this)
        tabLayout = findViewById(R.id.tab_layout)
        tabLayout.setSelectedTabIndicatorColor(invalidIndicatorColor)
        tabLayout.setSelectedTabIndicatorHeight(indicatorHeight)
        setSelectionBehaviour()
    }

    /**
     * Setter fot the callback interface
     *
     * @param tabInterface refrence for class implementing the interface
     */
    fun setTabLayoutInterface(tabInterface: TapSelectionTabLayoutInterface) {
        this.tabLayoutInterface = tabInterface
    }

    /**
     * Setter for the indicator color
     *
     * @param color integer color value
     */
    fun setTabIndicatorColor(@ColorInt color: Int) {
        indicatorColor = color
        tabLayout.setSelectedTabIndicatorColor(color)
    }

    /**
     * Setter for the indicator height
     *
     * @param height integer height value
     */
    fun setTabIndicatorHeight(height: Int) {
        indicatorHeight = height
        tabLayout.setSelectedTabIndicatorHeight(height)
    }

    /**
     * setter for the ripple color
     *
     * @param color integer color value
     */
    fun setTabRippleColor(@ColorInt color: Int) {
        tabLayout.tabRippleColor = ColorStateList.valueOf(color)
    }

    /**
     * Setter for the ripple color
     *
     * @param tabRippleColorResourceId integer color resource id
     */
    fun setTabRippleColorResource(@ColorRes tabRippleColorResourceId: Int) {
        tabLayout.setTabRippleColorResource(tabRippleColorResourceId)
    }

    /**
     * Setter for the unselected tab alpha level
     *
     * @param level float level value
     */
    fun setUnselectedAlphaLevel(level: Float) {
        unselectedAlphaLevel = level
    }

    /**
     * Setter for tab item max width
     *
     * @param maxItemWidth integer width value
     */
    fun setMaxItemWidth(maxItemWidth: Int) {
        this.maxItemWidth = maxItemWidth
    }

    /**
     * Adding new section to the tablayout
     *
     * @param items list of the items inside the section
     */
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

    fun selectSection(index: Int) {
        val tab = tabLayout.getTabAt(index)
        tab?.select()
    }

    /**
     * private function to modify the items size based on the screen width after adding new section
     */
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

    /**
     * private function to generate parent layout for the newly added section
     *
     * @return the generated container layout
     */
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

    /**
     * private function for generating the required view for the section item
     *
     * @param item containing the type, image and indicator for that item
     * @return the generated item with passed values
     */
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

    /**
     * private function to generate parent layout for the newly added item
     *
     * @return generated item container layout
     */
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

    /**
     * private function to generate single tab indicator
     *
     * @return view represent the tab indicator
     */
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

    /**
     * private function to calculate the item size based on the screen width and number of items
     *
     * @return integer value represent the item width
     */
    private fun getItemWidth(): Int {
        var totalItemsCount = 0
        for (items in itemsCount) {
            totalItemsCount += items
        }
        val itemSize =
            (Resources.getSystem().displayMetrics.widthPixels - SCREEN_MARGINS) / totalItemsCount
        return if (itemSize > maxItemWidth) maxItemWidth else itemSize
    }

    /**
     * Handle the logic of fading unselected tab and notify the callback with tab selection change listener
     */
    private fun setSelectionBehaviour() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                resetBehaviour()
                fadeOtherTabs(tab?.position)
                tabLayoutInterface?.onTabSelected(tab?.position)
            }
        })
    }

    /**
     * private function to fade the unselected view and display the selected tab with full opacity
     *
     * @param position the selected index
     */
    private fun fadeOtherTabs(position: Int?) {
        tabsView.forEachIndexed { index, view ->
            if (index == position)
                view.alpha = 1f
            else
                view.alpha = unselectedAlphaLevel
        }
    }

    /**
     * public funtion that select the tab based on the required brand
     *
     * @param type the required brand type to be selected
     */
    fun selectTab(type: CardBrand, valid: Boolean) {
        resetBehaviour()
        changeClickableState(!valid)
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT)
        if (valid)
            selectValidType(type)
        else
            selectUnValidType(type)
    }

    private fun selectUnValidType(type: CardBrand) {
        resetTabsAlpha()
        tabsView.forEach { view ->
                if (view.alpha != 1f)
                    view.alpha = 1f
        }
        tabItems.forEach {
            if (it.type != type) {
                it.imageView?.alpha = unselectedAlphaLevel
                it.indicator?.visibility = View.INVISIBLE
            } else {
                it.imageView?.alpha = 1f
                it.indicator?.visibility = View.VISIBLE
                it.indicator?.setBackgroundColor(invalidIndicatorColor)
            }
        }
    }

    private fun resetTabsAlpha() {
        tabItems.forEach {
            it.imageView?.alpha = 1f
        }
    }

    private fun selectValidType(type: CardBrand) {
        tabItems.forEach {
            if (it.type != type) {
                it.imageView?.setImageDrawable(it.unSelectedImage)
                it.indicator?.visibility = View.INVISIBLE
            } else {
                it.imageView?.setImageDrawable(it.selectedImage)
                it.indicator?.visibility = View.VISIBLE
                it.indicator?.setBackgroundColor(indicatorColor)
            }
        }
    }

    /**
     * public function to reset the behaviour of the tabs after selected tab being released
     */
    fun resetBehaviour() {
        resetTabsAlpha()
        changeClickableState(true)
        tabLayout.setSelectedTabIndicatorColor(invalidIndicatorColor)
        tabItems.forEach {
            it.imageView?.setImageDrawable(it.selectedImage)
            it.indicator?.visibility = View.INVISIBLE
        }
    }

    /**
     * private function to change clickable state based on single tab selection
     *
     * @param isClickable the state of click ability
     */
    private fun changeClickableState(isClickable: Boolean) {
        if (isClickable) {
            touchableList.forEach { it.isEnabled = isClickable }
        } else {
            touchableList = tabLayout.touchables
            touchableList.forEach { it.isEnabled = isClickable }
        }
    }

    /**
     * class fot holding the constant values used in the tablayout
     */
    companion object {
        const val SCREEN_MARGINS = 140
        const val INDICATOR_HEIGHT = 2f
        const val INDICATOR_COLOR = "#2ace00"
        const val INVALID_INDICATOR_COLOR = "#a8a8a8"
        const val UNSELECTED_ALPHA = 0.5f
        const val MAX_ITEM_WIDTH = 100f
    }
}