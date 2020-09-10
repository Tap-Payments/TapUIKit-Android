package company.tap.tapuilibrary.uikit.atoms

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.ChipTheme
import company.tap.tapuilibrary.themekit.theme.TextViewTheme
import kotlinx.android.synthetic.main.item_knet.view.*
import kotlinx.android.synthetic.main.tap_chip_group.view.*


/**
 * A ChipGroup is used to hold multiple Chips. By default, the chips are reflowed across
 * multiple lines. Set the attribute to constrain the chips to a single horizontal line.
 *  @param context The Context the view is running in, through which it can
 *  access the current theme, resources, etc.
 *  @param attrs The attributes of the XML Button tag being used to inflate the view.
 **/
open class TapChipGroup(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    val groupName by lazy { findViewById<TapTextView>(R.id.group_name) }
    val groupAction by lazy { findViewById<TapTextView>(R.id.group_name) }
    val chipsRecycler by lazy { findViewById<RecyclerView>(R.id.chip_recycler) }


    //Initialize views
    init {
        inflate(context, R.layout.tap_chip_group, this)
        setTheme(groupName,groupAction,chipsRecycler)
    }

    fun setTheme(groupName:TapTextView?,groupAction :TapTextView?, chipsRecycler:RecyclerView? ) {
        val groupNameTextViewTheme = TextViewTheme()
        groupNameTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("horizontalList.headers.gatewayHeader.leftButton.labelTextColor"))
        groupNameTextViewTheme.font =
            ThemeManager.getFontName("horizontalList.headers.gatewayHeader.leftButton.labelTextFont")
        groupName?.setTheme(groupNameTextViewTheme)

        val groupActionTextViewTheme = TextViewTheme()
        groupActionTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("horizontalList.headers.gatewayHeader.leftButton.labelTextColor"))
        groupActionTextViewTheme.font =
            ThemeManager.getFontName("horizontalList.headers.gatewayHeader.leftButton.labelTextFont")
        groupAction?.setTheme(groupActionTextViewTheme)

        linearMainView.setBackgroundColor(Color.parseColor(ThemeManager.getValue("horizontalList.backgroundColor")))
        chipsRecycler?.setBackgroundColor(Color.parseColor(ThemeManager.getValue("horizontalList.backgroundColor")))

    }
}