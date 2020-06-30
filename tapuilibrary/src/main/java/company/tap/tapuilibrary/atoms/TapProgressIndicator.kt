package company.tap.tapuilibrary.atoms

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.progressindicator.ProgressIndicator
import company.tap.tapuilibrary.interfaces.TapProgressIndicatorInterface

/**
 *
 * Created by Mario Gamal on 6/30/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapProgressIndicator(context: Context?, attrs: AttributeSet?) :
    ProgressIndicator(context, attrs) {

    var progressIndicatorInterface: TapProgressIndicatorInterface? = null

    override fun setProgress(progress: Int) {
        super.setProgress(progress)
        if (progress == max)
            progressIndicatorInterface?.onProgressEnd()
    }
}