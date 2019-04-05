package com.example.myapplication

data class History(val codebar : String, val productName : String?, val brands : List<String?>?, val nutriScor: String?, val calories: String?, val picture: String?) {
    constructor() : this("", "", listOf(), "", "", "")

    fun toProduct() : Product {

        return Product(
            name = productName,
            codeB = codebar,
            brands = brands,
            nutriScore = nutriScor,
            calories = calories,
            urlImage = picture

        )
    }
}