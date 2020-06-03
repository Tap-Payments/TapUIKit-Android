package company.tap.tapuilibrary

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 *
 * Created by Mario Gamal on 6/3/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
open class TapBottomSheetDialog : BottomSheetDialogFragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tapBottomDialogInterface.didShow()

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        tapBottomDialogInterface.didDismiss()
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

}