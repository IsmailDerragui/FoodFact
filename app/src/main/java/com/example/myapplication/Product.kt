package com.example.myapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product (val name: String?, val brands: List<String?>?, val codeB: String, val nutriScore: String?, val urlImage: String?, val Quantite: String? = null,
                    val Vendu: List<String? >? = null, val ingredigent: List<String?>? = null, val subsAllergene: List<String?>? = null, val additifs: Map<String, String>? = null, val calories: String?) : Parcelable {
    open fun toHistory() : History {
        return History(
            productName = name,
            codebar = codeB,
            brands = brands,
            nutriScor = nutriScore,
            calories = calories,
            picture = urlImage
        )
    }
}