package company.tap.tapuilibrary

import android.content.Context
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * Created by Mario Gamal on 5/8/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
open class TapChipGroup(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    var groupName: TapTextView
    var groupAction: TapTextView
    var chipsRecycler: RecyclerView
  //  var chipsRecycler: HorizontalScrollView

    init {
        inflate(context, R.layout.tap_chip_group, this)
        groupName = findViewById(R.id.group_name)
        groupAction = findViewById(R.id.group_action)
        chipsRecycler = findViewById(R.id.chip_recycler)
    }
}