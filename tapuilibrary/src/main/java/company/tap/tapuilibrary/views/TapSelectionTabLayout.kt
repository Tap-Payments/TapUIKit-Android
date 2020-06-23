package company.tap.tapuilibrary.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import company.tap.tapcardvalidator_android.CardBrand
import company.tap.tapuilibrary.utils.MetricsUtil
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.models.SectionTabItem
import company.tap.tapuilibrary.atoms.TapImageView
import company.tap.tapuilibrary.interfaces.TapSelectionTabLayoutInterface

/**
 *
 * Created by Mario Gamal on 6/17/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapSelectionTabLayout(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private var indicatorColor = Color.parseColor("#2ace00")
    private val indicatorHeight = MetricsUtil.convertDpToPixel(2f, context).toInt()
    private var tabLayout: TabLayout
    private val itemsCount = ArrayList<Int>()
    private val tabsView = ArrayList<LinearLayout>()
    private val tabItems = ArrayList<SectionTabItem>()
    private var touchableList = ArrayList<View>()

    private var tabLayoutInterface : TapSelectionTabLayoutInterface? = null

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
            getItemWidth(), 0
        )
        params.setMargins(0,30,0,30)
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
        params.setMargins(0,30,0,30)
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
                tabLayoutInterface?.onSelectedTabChanged(tab?.position)
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

}