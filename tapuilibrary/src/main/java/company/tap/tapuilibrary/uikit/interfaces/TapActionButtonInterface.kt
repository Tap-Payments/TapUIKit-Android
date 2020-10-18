package company.tap.tapuilibrary.uikit.interfaces

import company.tap.tapuilibrary.uikit.enums.ActionButtonState

/**
 *
 * Created by Mario Gamal on 6/25/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
interface TapActionButtonInterface {
    fun onStateChanged(state: ActionButtonState)
    fun onClickActionButton()
}