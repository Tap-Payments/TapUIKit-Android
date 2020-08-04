package company.tap.tapuilibrary.atoms

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuilibrary.R
import kotlinx.android.synthetic.main.tap_chip_group.view.*

/**
 *
 * Created by Mario Gamal on 5/8/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */

/**
 * A ChipGroup is used to hold multiple Chips. By default, the chips are reflowed across
 * multiple lines. Set the attribute to constrain the chips to a single horizontal line.
 *  @param context The Context the view is running in, through which it can
 *  access the current theme, resources, etc.
 *  @param attrs The attributes of the XML Button tag being used to inflate the view.
 **/
open class TapChipGroup(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    var groupName: TapTextView
    var groupAction: TapTextView
    var chipsRecycler: RecyclerView

    //Initialize views
    init {
        inflate(context, R.layout.tap_chip_group, this)
        groupName = findViewById(R.id.group_name)
        groupAction = findViewById(R.id.group_action)
        chipsRecycler = findViewById(R.id.chip_recycler)
    }
}