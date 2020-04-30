package com.zaphlabs.knotty_online.ui.home.events

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.data.model.Event
import kotlinx.android.synthetic.main.item_view_home_layout.view.*

/**
 * Developer : Mohammad Zaki
 * Created On : 30-04-2020
 */

class EventAdapter(
    private var mActivity: Fragment,
    private val eventList:ArrayList<Event>,
    private val onEventSelectListener: OnEventSelectListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_home_layout,parent,false)
        return EventViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EventViewHolder).bind(position)
    }

    inner class EventViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        fun bind(position: Int){
            itemView.tvAccountTitle.text=eventList[position].hostName
        }
    }

}
interface OnEventSelectListener{
    fun onEventSelected(position:Int,event:Event)
    fun onJoin(position: Int,event: Event)
}