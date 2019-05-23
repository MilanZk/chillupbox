package com.company.mobile.android.appname.app.main.navigationdrawer

import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

class NavigationDrawerMainActivityViewModel : ViewModel() {
    lateinit var currentSectionFragmentTag: String
    var currentMenuItemId: Int by Delegates.notNull()
}
