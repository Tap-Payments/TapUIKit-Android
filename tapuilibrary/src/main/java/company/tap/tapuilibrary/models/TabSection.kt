package company.tap.tapuilibrary.models

import company.tap.tapuilibrary.enums.TabSectionType

/**
 *
 * Created by Mario Gamal on 7/14/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
data class TabSection(
    val type: TabSectionType,
    val items: ArrayList<SectionTabItem>
)