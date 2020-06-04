package company.tap.tapuilibrary

import android.app.Dialog
import android.content.Context
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var tapBottomDialogInterface: TapBottomDialogInterface

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.modal_bottom_sheet, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            tapBottomDialogInterface = context as TapBottomDialogInterface
        } catch (ex: ClassCastException) {
            try {
                tapBottomDialogInterface = parentFragment as TapBottomDialogInterface
            } catch (ignore: Exception) {
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return bottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tapBottomDialogInterface.didShow()
        setDialogConfigurations()
        changeBackground()
    }

    private fun changeBackground() {
        bottomSheetDialog.setOnShowListener {
            val bottomSheet =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.background = getBackgroundDrawable()
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
        tapBottomDialogInterface.didDismiss()
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

}