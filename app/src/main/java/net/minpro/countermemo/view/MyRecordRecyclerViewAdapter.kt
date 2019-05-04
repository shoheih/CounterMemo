package net.minpro.countermemo.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.RealmResults
import net.minpro.countermemo.R
import net.minpro.countermemo.model.RecordModel

class MyRecordRecyclerViewAdapter(private val results: RealmResults<RecordModel>)
    : RecyclerView.Adapter<MyRecordRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_record, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = results[position]
        holder.textCountNum.text = item!!.count.toString()
        holder.textDate.text = item.date
    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class ViewHolder(val v: View): RecyclerView.ViewHolder(v) {
        val textCountNum: TextView
        val textDate: TextView

        init {
            textCountNum = v.findViewById(R.id.textCountNum)
            textDate = v.findViewById(R.id.textDate)
        }
    }
}
