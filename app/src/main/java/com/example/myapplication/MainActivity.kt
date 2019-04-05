package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle(R.string.produits)
        supportActionBar?.setIcon(R.drawable.ic_barcode)

        main_navigation.setOnNavigationItemSelectedListener {

            val fragment = when(it.itemId) {
                R.id.main_navbar_history -> FragmentBar()
                /*R.id.main_navbar_favs -> println("Favoris")
                R.id.main_navbar_stats -> println("Statistiques")
                R.id.main_navbar_profile -> println("Profil")*/
                else -> throw Exception()
            }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .commitAllowingStateLoss()

            true
        }

        main_navigation.selectedItemId = R.id.main_navbar_history


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.barcode->startActivityForResult(Intent("com.google.zxing.client.android.SCAN").apply {
                putExtra("SCAN_FORMATS", "EAN_13")
            }, 100)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val barcode = data?.getStringExtra("SCAN_RESULT")

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("codeBar", barcode)
        startActivity(intent)
    }
}