package company.tap.tapuilibrary.atoms

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.google.android.material.card.MaterialCardView
import company.tap.tapuilibrary.interfaces.TapView
import company.tap.thememanager.theme.ChipTheme

/**
 * Created by AhlaamK on 4/28/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
/** TapChip which uses default properties of MaterialCardView.
 *
 * */
open class TapChip (context: Context, attributeSet: AttributeSet?) : MaterialCardView(context, attributeSet),
    TapView<ChipTheme> {
    lateinit var view:View
    lateinit var viewsList:List<View>

     constructor(context: Context, attributeSet: AttributeSet?, view : View) : this(context, attributeSet){
      this.view = view
    }
    constructor(context: Context, viewList : List<View>):this(context,null){
        this.viewsList = viewList
    }
    override fun setTheme(theme: ChipTheme) {
        theme.cardCornerRadius?.let{ radius = it.toFloat() }
        theme.cardElevation?.let { cardElevation= it.toFloat() }
        theme.outlineSpotShadowColor?.let { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            outlineSpotShadowColor=it
        }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
       // addView(view)
    }
}