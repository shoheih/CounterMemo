package net.minpro.countermemo.model

import android.arch.lifecycle.MutableLiveData
import io.realm.Realm
import java.text.SimpleDateFormat
import java.util.*

class RecordRepository {

    fun recordToRealm(countNum: MutableLiveData<Int>) {

        val today = SimpleDateFormat("yyyy/MM/dd").format(Date())

        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val newRecord = realm.createObject(RecordModel::class.java)
        newRecord.apply {
            count = countNum.value!!
            date = today
        }
        realm.commitTransaction()

        realm.close()
    }


}