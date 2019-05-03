package net.minpro.countermemo.model

import io.realm.RealmObject

open class RecordModel: RealmObject() {

    //回数
    var count: Int = 0

    //日付(yyyy/MM/dd)
    var date: String = ""

}