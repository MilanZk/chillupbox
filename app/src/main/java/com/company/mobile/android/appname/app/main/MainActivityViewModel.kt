package com.company.mobile.android.appname.app.main

import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

class MainActivityViewModel : ViewModel() {
    lateinit var currentSectionFragmentTag: String
    var currentMenuItemId: Int by Delegates.notNull()
}
