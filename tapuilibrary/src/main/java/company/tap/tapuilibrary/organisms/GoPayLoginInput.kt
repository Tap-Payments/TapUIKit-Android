package company.tap.tapuilibrary.organisms

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.datasource.GoPayLoginDataSource
import company.tap.tapuilibrary.utils.FakeThemeManager

/**
 *
 * Created by Mario Gamal on 7/14/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class GoPayLoginInput(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private val loginTabLayout: TabLayout

    init {
        inflate(context, R.layout.gopay_login_input, this)
        loginTabLayout = findViewById(R.id.login_type)
    }

    fun changeDataSource(dataSource: GoPayLoginDataSource) {
        initTabLayout(dataSource.emailTabText, dataSource.phoneTabText)
    }

    private fun initTabLayout(emailTabText: String?, phoneTabText: String?) {
        loginTabLayout.addTab(
            loginTabLayout.newTab().setCustomView(
                getThemedTabText(emailTabText ?: "EMAIL", true)
            )
        )

        loginTabLayout.addTab(
            loginTabLayout.newTab().setCustomView(
                getThemedTabText(phoneTabText ?: "PHONE", false)
            )
        )

        loginTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabText = tab?.customView as TapTextView
                tabText.setTextColor(FakeThemeManager.getGoPayUnSelectedTabColor())
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabText = tab?.customView as TapTextView
                tabText.setTextColor(FakeThemeManager.getGoPaySelectedTabColor())
            }
        })
    }

    private fun getThemedTabText(text: String, isSelected: Boolean): TapTextView {
        val tabText = TapTextView(context, null)
        tabText.setTheme(FakeThemeManager.getGoPayTabLayoutTextTheme(isSelected))
        tabText.text = text
        tabText.typeface = Typeface.create("sans-serif-light", Typeface.NORMAL);
        return tabText
    }

}