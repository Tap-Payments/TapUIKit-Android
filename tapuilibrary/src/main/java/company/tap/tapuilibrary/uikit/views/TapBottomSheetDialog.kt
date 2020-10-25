package company.tap.tapuilibrary.uikit.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.SeparatorViewTheme
import company.tap.tapuilibrary.uikit.interfaces.TapBottomDialogInterface
import company.tap.tapuilibrary.uikit.models.DialogConfigurations
import kotlinx.android.synthetic.main.modal_bottom_sheet.*


/**
 *
 * Created on 6/3/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
open class TapBottomSheetDialog : BottomSheetDialogFragment() {

    private var topLeftCorner = 16f
    private var topRightCorner = 16f
    private var bottomRightCorner = 0f
    private var bottomLeftCorner = 0f
     var backgroundColor = Color.TRANSPARENT

    var bottomSheetLayout: FrameLayout? = null
    lateinit var bottomSheetDialog: BottomSheetDialog
    private var tapBottomDialogInterface: TapBottomDialogInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.modal_bottom_sheet, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheetLayout =
                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout!!)
            bottomSheetBehavior.isDraggable
            bottomSheetDialog.behavior.isFitToContents
            backgroundColor =
                Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor"))

            view?.alpha = 0.0f;
            view?.animate()
                ?.alpha(1.0f)
                ?.setListener(null);
            bottomSheetBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    tapBottomDialogInterface?.onSlide(slideOffset)
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    tapBottomDialogInterface?.onStateChanged(newState)
                }
            })
            setSeparatorTheme()
        }
        return bottomSheetDialog
    }

    fun setBottomSheetInterface(tapBottomDialogInterface: TapBottomDialogInterface) {
        this.tapBottomDialogInterface = tapBottomDialogInterface
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialogConfigurations()
        changeBackground()
    }

    private fun changeBackground() {
        bottomSheetDialog.setOnShowListener {
            bottomSheetLayout =
                bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheetLayout?.background = getBackgroundDrawable()
            tapBottomDialogInterface?.onShow()
        }
    }

    private fun setDialogConfigurations() {
        arguments?.let {
            dialog?.setCanceledOnTouchOutside(it.getBoolean(DialogConfigurations.Cancelable, true))
            dialog?.window?.setDimAmount(it.getFloat(DialogConfigurations.Dim, 0.5f))
            backgroundColor = it.getInt(DialogConfigurations.Color, Color.WHITE)
            val corners = it.getFloatArray(DialogConfigurations.Corners)
            corners?.let { array ->
                topLeftCorner = array[0]
                topRightCorner = array[1]
                bottomRightCorner = array[2]
                bottomLeftCorner = array[3]
            }
        }
    }

    private fun getBackgroundDrawable(): Drawable {
        val shape = ShapeDrawable(
            RoundRectShape(
                floatArrayOf(
                    topLeftCorner, topLeftCorner,
                    topRightCorner, topRightCorner,
                    bottomRightCorner, bottomRightCorner,
                    bottomLeftCorner, bottomLeftCorner
                ),
                null, null
            )
        )
        shape.paint.color = backgroundColor
        return shape
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        view?.animate()
            ?.translationY(0F)
            ?.alpha(0.0f)
            ?.setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    view!!.visibility = View.GONE
                }
            })
        tapBottomDialogInterface?.onDismiss()
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    open fun onNewIntent(intent: Intent?) {}


    fun setTheme() {

    }


    fun setSeparatorTheme() {
        topLinear.setBackgroundColor(Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor")))
        val separatorViewTheme = SeparatorViewTheme()
        separatorViewTheme.strokeColor =
            Color.parseColor(ThemeManager.getValue("tapSeparationLine.backgroundColor"))
        separatorViewTheme.strokeHeight = ThemeManager.getValue("tapSeparationLine.height")
        indicatorSeparator.setTheme(separatorViewTheme)
    }
}