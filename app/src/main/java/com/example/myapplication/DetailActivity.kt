package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.detailpager.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!intent.hasExtra("codeBar")) {
            throw Exception("The product is missing")
        }

        setContentView(R.layout.activity_details)

        val barcode = intent.getStringExtra("codeBar")

        GlobalScope.launch {
//            viewPager.visibility = View.GONE
//            progressbar.visibility = View.VISIBLE
            val product = NetworkManager.getProduct(barcode)
            withContext(Dispatchers.Main) {
                if (product != null) {
                    addToHistory(product)
                    pager.adapter = ProductDetailAdapter(supportFragmentManager, product)
                    tabs.setupWithViewPager(pager)
                } else {
                    // throw Exception("Erro")
                }
            }
//            progressbar.visibility = View.GONE
//            viewPager.visibility = View.VISIBLE
        }

    }

    private fun addToHistory(product: Product){
        val history : History = product.toHistory()
        FirebaseDatabase.getInstance()
            .getReference("user_id/1/history/${System.currentTimeMillis()}")
            .setValue(history)
    }
}

class ProductDetailAdapter(fm: FragmentManager, val product: Product) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FragmentFiche.newInstance(product)
            1 -> FragmentNutrition.newInstance(product)
            2 -> FragmentInfoNutri.newInstance(product)
            else -> throw Exception("Error")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Fiche"
            1 -> "Nutrition"
            2 -> "Infos nutritionnelles"
            else -> throw Exception("Error")
        }
    }
}