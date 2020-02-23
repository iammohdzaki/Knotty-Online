package com.zaphlabs.knotty_online.ui.chat

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.data.model.Message
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.ui.home.adapter.RecyclerClickListener
import com.zaphlabs.knotty_online.utils.ViewType.Companion.MESSAGE_RECEIVE
import com.zaphlabs.knotty_online.utils.ViewType.Companion.MESSAGE_SEND
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ChatActivity : BaseActivity() ,RecyclerClickListener{
    override fun onItemClick(position: Int) {

    }

    override fun onStarClick(position: Int) {
    }

    private var chatAdapter : ChatAdapter? = null
    private var messageList = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        init()
    }

    fun init(){
        tvToolbarTitle.visibility= View.VISIBLE
        tvToolbarTitle.text="Group Chat"
        messageList.add(Message("Zaki","Hello !",null,MESSAGE_SEND))
        messageList.add(Message("Razi","Hi !",null, MESSAGE_RECEIVE))
        messageList.add(Message("Zaki","How's You !",null,MESSAGE_SEND))
        messageList.add(Message("Zaki","Fine and U ?","https://images.pexels.com/photos/1236701/pexels-photo-1236701.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",MESSAGE_RECEIVE))
        messageList.add(Message("Zaki","Also doing good!",null,MESSAGE_SEND))
        messageList.add(Message("Zaki","How's You !",null,MESSAGE_RECEIVE))
        messageList.add(Message("Zaki","How's You !",null,MESSAGE_SEND))
        messageList.add(Message("Zaki","How's You !",null,MESSAGE_RECEIVE))
        messageList.add(Message("Zaki","How's You !",null,MESSAGE_SEND))
        messageList.add(Message("Zaki","How's You !",null,MESSAGE_RECEIVE))
        messageList.add(Message("Zaki","How's You !",null,MESSAGE_SEND))
        messageList.add(Message("Zaki","How's You !",null,MESSAGE_RECEIVE))
        rvChat.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,true)
        chatAdapter = ChatAdapter(this@ChatActivity,messageList,this)
        rvChat.adapter=chatAdapter
    }


}
