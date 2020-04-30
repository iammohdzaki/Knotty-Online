package com.zaphlabs.knotty_online.ui.home.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.data.model.Event
import com.zaphlabs.knotty_online.data.model.User
import com.zaphlabs.knotty_online.data.remote.ApiClient
import kotlinx.android.synthetic.main.fragment_events.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsFragment : Fragment() {

    private var eventList=ArrayList<Event>()
    private var eventAdapter:EventAdapter ?= null
    private var rootView:View ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_events, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getEvents()
    }


    private fun getEvents(){
        ApiClient.getClient().getEvents().enqueue(object : Callback<List<Event>>{
            override fun onFailure(call: Call<List<Event>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if(response.isSuccessful){
                    eventList = response.body() as ArrayList<Event>
                    setRecycler()
                }
            }

        })
    }

    private fun setRecycler(){
        eventAdapter= EventAdapter(this,eventList,object : OnEventSelectListener{
            override fun onEventSelected(position: Int, event: Event) {

            }
            override fun onJoin(position: Int, event: Event) {

            }
        })

        rvManageAccount.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventAdapter
        }
    }

}
