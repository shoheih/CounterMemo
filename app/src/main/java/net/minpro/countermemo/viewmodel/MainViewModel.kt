package net.minpro.countermemo.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.realm.RealmResults
import net.minpro.countermemo.model.RecordModel
import net.minpro.countermemo.model.RecordRepository

class MainViewModel: ViewModel() {

    private val recordRepository = RecordRepository()

    var countNum = MutableLiveData<Int>()
    var displayNum = MutableLiveData<String>()

    fun initParameters() {
        countNum.value = 0
        displayNum.value = countNum.value.toString()
    }

    fun countUp() {
        countNum.value = countNum.value!! + 1
        displayNum.value = countNum.value.toString()
    }

    fun countDown() {
        if (countNum.value!! <= 0) return
        countNum.value = countNum.value!! - 1
        displayNum.value = countNum.value.toString()
    }

    fun recordToRealm() {
        recordRepository.recordToRealm(countNum)
    }

    fun getRecordList(): RealmResults<RecordModel>? {
        return recordRepository.getRecordList()
    }
}