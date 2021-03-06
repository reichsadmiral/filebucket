package com.isaiahvonrundstedt.bucket.features.auth

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.isaiahvonrundstedt.bucket.R
import com.isaiahvonrundstedt.bucket.features.auth.login.LoginActivity
import com.isaiahvonrundstedt.bucket.features.auth.register.RegisterActivity
import com.isaiahvonrundstedt.bucket.features.shared.abstracts.BaseActivity
import com.isaiahvonrundstedt.bucket.constants.Params
import com.isaiahvonrundstedt.bucket.features.shared.ui.WebViewFragment
import com.isaiahvonrundstedt.bucket.features.shared.receivers.NetworkReceiver
import kotlinx.android.synthetic.main.activity_firstrun.*
import kotlinx.android.synthetic.main.layout_banner_network.*

class FirstRunActivity: BaseActivity(), NetworkReceiver.ConnectivityListener {

    private var networkReceiver: NetworkReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstrun)

        networkReceiver = NetworkReceiver()
    }

    override fun onStart() {
        super.onStart()

        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onStop(){
        super.onStop()
        unregisterReceiver(networkReceiver)
    }

    override fun onResume() {
        super.onResume()
        NetworkReceiver.connectivityListener = this

        statusRootView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                startActivity(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
            else startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

        val webViewFragment = WebViewFragment()
        val args = Bundle()

        val spannableString = SpannableString(getString(R.string.first_run_terms))
        spannableString.setSpan(object: ClickableSpan() {
            override fun onClick(widget: View) {
                args.putInt(Params.viewType, WebViewFragment.viewTypePrivacy)
                webViewFragment.arguments = args
                webViewFragment.invoke(supportFragmentManager)
            }
        }, 33, 47, 0)
        spannableString.setSpan(object: ClickableSpan(){
            override fun onClick(widget: View) {
                args.putInt(Params.viewType, WebViewFragment.viewTypeTerms)
                webViewFragment.arguments = args
                webViewFragment.invoke(supportFragmentManager)
            }
        }, 52, 68, 0)
        hyperlinkView.movementMethod = LinkMovementMethod.getInstance()
        hyperlinkView.setText(spannableString, TextView.BufferType.SPANNABLE)
    }

    override fun onNetworkChanged(status: Int) {
        statusRootView.isVisible = status == NetworkReceiver.typeNotConnected
    }

}