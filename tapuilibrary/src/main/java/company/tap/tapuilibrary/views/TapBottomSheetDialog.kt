package company.tap.tapuilibrary.views

import android.app.Dialog
import android.content.DialogInterface
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
import company.tap.tapuilibrary.models.DialogConfigurations
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.interfaces.TapBottomDialogInterface

/**
 *
 * Created by Mario Gamal on 6/3/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */


open class TapBottomSheetDialog : BottomSheetDialogFragment() {

    private var topLeftCorner = 16f
    private var topRightCorner = 16f
    private var bottomRightCorner = 0f
    private var bottomLeftCorner = 0f
    private var backgroundColor = Color.WHITE

    private var bottomSheetLayout: FrameLayout? = null
    private lateinit var bottomSheetDialog: BottomSheetDialog
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
            val bottomSheetLayout = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout!!)
            bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    tapBottomDialogInterface?.onSlide(slideOffset)
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    tapBottomDialogInterface?.onStateChanged(newState)
                }
            })
        }
        return bottomSheetDialog
    }

    fun setBottomSheetInterface(tapBottomDialogInterface: TapBottomDialogInterface) {
        this.tapBottomDialogInterface = tapBottomDialogInterface
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tapBottomDialogInterface?.onShow()
        setDialogConfigurations()
        changeBackground()
    }

    private fun changeBackground() {
        bottomSheetDialog.setOnShowListener {
            bottomSheetLayout = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheetLayout?.background = getBackgroundDrawable()
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
        tapBottomDialogInterface?.onDismiss()
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}