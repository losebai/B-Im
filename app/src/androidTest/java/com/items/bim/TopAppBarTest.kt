package com.items.bim

import org.junit.Rule
import org.junit.Test


class TopAppBarTest {

    // Don't copy this over

    @get:Rule
    val composeTestRule = createAndroidComposeRule(RallyActivity::class.java)

    @Test
    fun rallyTopAppBarTest() {
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = ,
                onTabSelected = { /*TODO*/ },
                currentScreen =
            )
        }
    }
}