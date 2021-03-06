package com.karyaprestasi.fishpal.ui.main_ui.marketplace

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IkanEntity(
    var idProduk : String = "",
    var namaIkan : String = "",
    var harga : Long = 0,
    var tokoIkan : String = "",
    var linkImage : String = "",
    var userId : String = ""
) : Parcelable