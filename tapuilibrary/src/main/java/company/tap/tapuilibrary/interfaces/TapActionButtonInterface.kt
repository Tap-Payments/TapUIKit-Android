package company.tap.tapuilibrary.interfaces

import company.tap.tapuilibrary.enums.ActionButtonState

/**
 *
 * Created by Mario Gamal on 6/25/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
interface TapActionButtonInterface {
    fun onStateChanged(state: ActionButtonState)
}