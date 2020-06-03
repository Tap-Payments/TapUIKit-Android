package company.tap.tapuilibrary

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 *
 * Created by Mario Gamal on 6/3/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
open class TapBottomSheetDialog: BottomSheetDialogFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}