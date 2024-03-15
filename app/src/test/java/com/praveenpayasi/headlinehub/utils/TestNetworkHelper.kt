package com.praveenpayasi.headlinehub.utils

import com.praveenpayasi.headlinehub.ui.utils.NetworkHelper

class TestNetworkHelper : NetworkHelper {
    override fun isNetworkConnected(): Boolean {
        return true
    }
}