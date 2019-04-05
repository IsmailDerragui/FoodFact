package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.list.*

class FragmentBar : Fragment(), OnProductClickListener {

    private var listener: ValueEventListener? = null
    private var reference: DatabaseReference? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.layoutManager = LinearLayoutManager(requireContext())
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onResume() {
        super.onResume()

        reference = FirebaseDatabase.getInstance().getReference("user_id/1/history")
        listener = reference!!.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listHistory = mutableListOf<History>()

                for(history in dataSnapshot.children) {
                    val history = history.getValue(History::class.java);
                    if (history != null) {
                        listHistory.add(history)
                    }
                }


                val products = listHistory.map{
                    it.toProduct()
                }


                list.adapter = ListAdapter(products, this@FragmentBar)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    override fun onPause() {
        super.onPause()

        if(listener != null){
            reference?.removeEventListener(listener!!)
        }
    }

    override fun onBookmarkClicked(product: Product) {

    }

    override fun onProductClicked(codeBar: String?) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra("codeBar", codeBar)
        startActivity(intent)

    }
}


class ListAdapter(val products: List<Product>, val listener: OnProductClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductCell).bindProduct(products[position], listener)
    }

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductCell(inflater.inflate(R.layout.item, parent, false))
    }

}

class ProductCell(v: View) : RecyclerView.ViewHolder(v) {
    val productName: TextView = v.product_name
    val productNutriscore: TextView = v.product_nutriscore
    val productImage: ImageView = v.product_item_image
    val productBookmark: ImageView = v.product_bookmark
    val productCard: CardView = v.product_item_card

    fun bindProduct(product: Product, listener: OnProductClickListener) {
        Picasso.get().load(product.urlImage).into(productImage)
        productName.text = product.name
        productNutriscore.text = product.nutriScore
        productBookmark.setOnClickListener {
            listener.onBookmarkClicked(product)
        }
        productCard.setOnClickListener {
            listener.onProductClicked(product.codeB)
        }
    }
}

interface OnProductClickListener{
    fun onBookmarkClicked(product: Product)
    fun onProductClicked(codeBar: String?)
}