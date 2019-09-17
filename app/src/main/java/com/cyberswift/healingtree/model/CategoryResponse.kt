package com.cyberswift.healingtree.model

import com.google.gson.annotations.SerializedName

class CategoryResponse {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("count")
    var count: Int = 0

    @SerializedName("categories")
    var categories: ArrayList<Category>? = null

}