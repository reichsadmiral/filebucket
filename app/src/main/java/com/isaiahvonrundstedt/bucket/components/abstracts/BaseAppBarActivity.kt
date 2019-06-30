package com.isaiahvonrundstedt.bucket.components.abstracts

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.isaiahvonrundstedt.bucket.R
import com.isaiahvonrundstedt.bucket.activities.support.SupportActivity

abstract class BaseAppBarActivity: BaseActivity() {

    internal var toolbarTitleView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPersistentActionBar()
    }

    private fun setPersistentActionBar() {
        val rootView: ViewGroup? = findViewById(R.id.action_bar_root)
        if (rootView != null){
            val view: View = LayoutInflater.from(this).inflate(R.layout.layout_appbar_main, rootView, false)
            rootView.addView(view, 0)

            val toolbar: Toolbar = rootView.findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)

            supportActionBar?.title = null
            toolbarTitleView = findViewById(R.id.toolbarTitle)
        }
    }

    internal fun setToolbarTitle(title: String?){
        toolbarTitleView?.text = title
    }

    internal fun setToolbarTitle(titleResID: Int){
        toolbarTitleView?.text = getString(titleResID)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        tintActionBarItem(menu, R.id.action_support, R.color.colorDefault)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.action_support -> startActivity(Intent(this, SupportActivity::class.java))
        }
        return true
    }

    internal fun tintActionBarItem(menu: Menu?, id: Int, colorID: Int){
        val drawable = menu?.findItem(id)?.icon
        drawable?.setColorFilter(ContextCompat.getColor(this, colorID), PorterDuff.Mode.SRC_ATOP)
        menu?.findItem(id)?.icon = drawable
    }

}