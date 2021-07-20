package com.project.fishbud.ui.main_ui.profile.fisherman.new_order

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.project.fishbud.ui.main_ui.marketplace.checkout.PaymentEntity
import com.project.fishbud.ui.main_ui.profile.OrderFishermanEntity
import com.project.fishbud.utils.DataFirebase

class NewOrderViewModel : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    lateinit var mContext: Context
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    fun getDataPayment(): LiveData<MutableList<PaymentEntity>> {
        val mutableData = MutableLiveData<MutableList<PaymentEntity>>()
        DataFirebase.getDataPayment().observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun getIdOrderedFromFisherman(): LiveData<MutableList<String>> {
        val mutableData = MutableLiveData<MutableList<String>>()
        DataFirebase.getIdOrderedFromFisherman().observeForever {
            mutableData.value = it
        }
        return mutableData
    }

//    fun getIdOrderedFromFisherman(idOrdered: List<String>): LiveData<MutableList<String>> {
//        val mutableData = MutableLiveData<MutableList<String>>()
//        DataFirebase.getIdOrderedFromFisherman(idOrdered).observeForever {
//            mutableData.value = it
//        }
//        return mutableData
//    }

    fun getItemOrderedFromFisherman(idOrdered : List<String>): LiveData<MutableList<OrderFishermanEntity>> {
        val mutableData = MutableLiveData<MutableList<OrderFishermanEntity>>()
        DataFirebase.getItemOrderedFromFisherman(idOrdered).observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun storeToDatabase(
        nelayanId: String,
        idProduk: String,
        namaIkan: String,
        harga: Long,
        linkImage: String,
        tokoIkan: String,
        idPembayaran: String
    ) {

        val reference2 =
            FirebaseDatabase.getInstance().reference.child("Users").child(nelayanId)
                .child("shipping")
                .child(idPembayaran)
                .child(idProduk)

        val value = OrderFishermanEntity(
            nelayanId,
            idProduk,
            namaIkan,
            harga,
            linkImage,
            tokoIkan,
            idPembayaran
        )

        reference2.setValue(value).addOnCompleteListener {
            if (it.isSuccessful) {

                //hapus dari itemOrdered
                val reference =
                    FirebaseDatabase.getInstance().reference.child("Users").child(nelayanId)
                        .child("itemOrdered")
                        .child(idPembayaran)
                        .child(idProduk)
                reference.removeValue().addOnCompleteListener { er ->
                    if (er.isSuccessful) {
                        Toast.makeText(mContext, "Pesanan diantar!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(mContext, "Error from database", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
//                    Toast.makeText(mContext, "Error from database", Toast.LENGTH_SHORT).show()
            }
        }


    }
}