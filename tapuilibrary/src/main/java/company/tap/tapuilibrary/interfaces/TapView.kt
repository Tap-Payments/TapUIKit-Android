package company.tap.tapuilibrary.interfaces

import company.tap.thememanager.theme.BaseTextTheme


/**
 *
 * Created by Mario Gamal on 4/20/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
interface TapView<T: BaseTextTheme> {
    fun setTheme(theme: T)
}