package com.zaphlabs.knotty_online.ui.chat

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.data.model.Message
import com.zaphlabs.knotty_online.ui.home.adapter.RecyclerClickListener
import com.zaphlabs.knotty_online.utils.ViewType.Companion.MESSAGE_RECEIVE
import com.zaphlabs.knotty_online.utils.ViewType.Companion.MESSAGE_SEND
import kotlinx.android.synthetic.main.item_view_message_receive.view.*
import kotlinx.android.synthetic.main.item_view_message_send.view.*
import kotlinx.android.synthetic.main.item_view_message_send.view.tvMessage

/**
 * Developer : Mohammad Zaki
 * Created On : 22-02-2020
 */

class ChatAdapter(private var mActivity: Activity, private val messageList:ArrayList<Message>, private val recyclerClickListener: RecyclerClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view:View
        when(viewType){
            MESSAGE_SEND -> {
                view= LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_view_message_send, parent, false)
                return SendViewHolder(view,recyclerClickListener)
            }
            MESSAGE_RECEIVE -> {
                view =  LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_view_message_receive, parent, false)
                return ReceiveViewHolder(view,recyclerClickListener)
            }
            else ->{
                view= LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_view_message_send, parent, false)
                return SendViewHolder(view,recyclerClickListener)
            }
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(messageList[position].messageType){
            MESSAGE_SEND ->{
                if(messageList[position].photoUrl != null){
                    (holder as SendViewHolder).tvMessage.visibility=View.GONE
                    (holder as SendViewHolder).ivMessage.visibility=View.VISIBLE

                    Glide.with(mActivity)
                        .load(messageList[position].photoUrl)
                        .into((holder as SendViewHolder).ivMessage)
                }else{
                    (holder as SendViewHolder).tvMessage.visibility=View.VISIBLE
                    (holder as SendViewHolder).ivMessage.visibility=View.GONE
                    (holder as SendViewHolder).tvMessage.text=messageList[position].message
                }
            }
            MESSAGE_RECEIVE ->{
                if(messageList[position].photoUrl != null){
                    (holder as ReceiveViewHolder).tvMessage.visibility=View.GONE
                    (holder as ReceiveViewHolder).ivMessage.visibility=View.VISIBLE

                    Glide.with(mActivity)
                        .load(messageList[position].photoUrl)
                        .into((holder as ReceiveViewHolder).ivMessage)
                }else{
                    (holder as ReceiveViewHolder).tvMessage.visibility=View.VISIBLE
                    (holder as ReceiveViewHolder).ivMessage.visibility=View.GONE
                    (holder as ReceiveViewHolder).tvMessage.text=messageList[position].message
                }
            }
        }


    }

    class SendViewHolder(itemView: View, private var recyclerClickListener: RecyclerClickListener) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val ivMessage=itemView.ivImage!!
        val tvMessage=itemView.tvMessage!!

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.ivStar -> {
                }
                else -> recyclerClickListener.onItemClick(adapterPosition)
            }
        }
    }

    class ReceiveViewHolder(itemView: View, private var recyclerClickListener: RecyclerClickListener) :
    RecyclerView.ViewHolder(itemView),
    View.OnClickListener {

        val ivMessage = itemView.ivMessage!!
        val tvMessage = itemView.tvMessage!!

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.ivStar -> {
                }
                else -> recyclerClickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when(messageList[position].messageType){
        MESSAGE_SEND ->{
            MESSAGE_SEND
        }
        MESSAGE_RECEIVE -> {
            MESSAGE_RECEIVE
        }
        else -> -1
    }

    /**
     * chooses a random color from array.xml
     */
    private fun getRandomMaterialColor(typeColor: String): Int {
        var returnColor = Color.GRAY
        val arrayId =
            mActivity.resources.getIdentifier("mdcolor_$typeColor", "array", mActivity.packageName)

        if (arrayId != 0) {
            val colors = mActivity.resources.obtainTypedArray(arrayId)
            val index = (Math.random() * colors.length()).toInt()
            returnColor = colors.getColor(index, Color.GRAY)
            colors.recycle()
        }
        return returnColor
    }
}
