package com.cyberswift.healingtree.model

import com.google.gson.annotations.SerializedName

class Category {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("parent")
    var parent: Int = 0

    @SerializedName("post_count")
    var post_count: Int = 0
}