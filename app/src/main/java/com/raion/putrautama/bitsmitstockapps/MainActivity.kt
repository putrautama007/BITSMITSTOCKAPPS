package com.raion.putrautama.bitsmitstockapps

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.raion.putrautama.bitsmitstockapps.laporan.LaporanHarianFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.beranda)

        updateFragment(MainFragment())

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_beranda -> {
                supportActionBar?.title = getString(R.string.beranda)
                updateFragment(MainFragment())
            }
            R.id.nav_lap_perhari ->{
                supportActionBar?.title = getString(R.string.laporan_harian)
                updateFragment(LaporanHarianFragment())
            }
            R.id.nav_tentang ->{
                supportActionBar?.title = getString(R.string.tentang)
                updateFragment(AboutAppsFragment())
            }
            R.id.nav_keluar ->{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun updateFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frame_content, fragment)
                .commit()
    }

}
