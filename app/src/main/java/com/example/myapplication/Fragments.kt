package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_view.*


open class BaseFragment : Fragment(){
    lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // on vérifie que le produit nous soit bien envoyé
        if (arguments == null || arguments?.containsKey("product") == false) {
            throw Exception("The product is missing")
        }

        product = arguments!!.getParcelable<Product>("product")!!
    }
}



class FragmentFiche : BaseFragment() {

    companion object {
        fun newInstance(product: Product): FragmentFiche {
            val fragment = FragmentFiche()
            val args = Bundle()
            args.putParcelable("product", product)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get().load(product.urlImage).into(img)
        product_title.text = product.name
        marque.text = product.brands.toString()
        codeBar.text = "${codeBar.text} ${product.codeB}"
        quantite.text = product.Quantite
        lieu.text = product.Vendu.toString()
        ingredients.text = product.ingredigent.toString()
        image_nutriScore.setImageResource(
            resources.getIdentifier("nutri_score_${product.nutriScore}", "drawable", requireActivity().packageName)
        )
    }
}
class FragmentNutrition : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.nutrition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(product: Product): FragmentNutrition {
            val fragment = FragmentNutrition()
            val args = Bundle()
            args.putParcelable("product", product)
            fragment.arguments = args
            return fragment
        }
    }
}
class FragmentInfoNutri : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.info_nutri, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(product: Product): FragmentInfoNutri {
            val fragment = FragmentInfoNutri()
            val args = Bundle()
            args.putParcelable("product", product)
            fragment.arguments = args
            return fragment
        }
    }
}
