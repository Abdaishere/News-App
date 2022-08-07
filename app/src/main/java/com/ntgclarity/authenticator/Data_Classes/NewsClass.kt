package com.example.example

import com.google.gson.annotations.SerializedName


data class NewsClass (

  @SerializedName("status"       ) var status       : String?             = null,
  @SerializedName("totalResults" ) var totalResults : Int?                = null,
  @SerializedName("articles"     ) var articles     : Array<Articles>   =  arrayOf()

)