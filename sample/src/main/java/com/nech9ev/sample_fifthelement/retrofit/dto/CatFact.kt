package com.nech9ev.sample_fifthelement.retrofit.dto

import com.google.gson.annotations.SerializedName

class CatFact {

    @SerializedName("fact")
    var fact: String = ""

    @SerializedName("length")
    var length: Int = 0
}