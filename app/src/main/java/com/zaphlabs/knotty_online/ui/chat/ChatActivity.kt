package com.zaphlabs.knotty_online.ui.chat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.data.model.Message
import com.zaphlabs.knotty_online.data.remote.CallbackListener
import com.zaphlabs.knotty_online.databinding.ActivityChatBinding
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.ui.chat.adapter.ChatAdapter
import com.zaphlabs.knotty_online.ui.home.adapter.RecyclerClickListener
import com.zaphlabs.knotty_online.utils.ViewType.Companion.MESSAGE_RECEIVE
import com.zaphlabs.knotty_online.utils.ViewType.Companion.MESSAGE_SEND
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.layout_send_message_toolbar.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import com.zaphlabs.knotty_online.utils.DEFAULT_MSG_LENGTH_LIMIT
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import javax.security.auth.callback.Callback


class ChatActivity : BaseActivity(), KodeinAware,RecyclerClickListener,View.OnClickListener,ChatNavigator,CallbackListener{

    override val kodein by kodein()
    private var chatAdapter : ChatAdapter? = null
    private var messageList = ArrayList<Message>()
    private val factory: ChatViewModelFactory by instance()
    private lateinit var viewModel: ChatViewModel
    private var mChildEventListener:ChildEventListener ?= null
    private val RC_PHOTO_PICKER=2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityChatBinding=
        DataBindingUtil.setContentView(this, R.layout.activity_chat)
        viewModel = ViewModelProviders.of(this, factory).get(ChatViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.chatNavigator = this
        viewModel.callbackListener = this
        init()
        messageListener()
    }

    fun init(){
        tvToolbarTitle.visibility= View.VISIBLE
        tvToolbarTitle.text="Group Chat"

        setOnClickListeners(this,tvSendMessage,ivSendImage)

        etMessageField.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tvSendMessage.isEnabled = p0.toString().trim().isNotEmpty()
            }
        })

        etMessageField.filters=
            arrayOf<InputFilter>(InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT))

        setUpAdapter()
    }

    private fun setUpAdapter(){
        var linearLayoutManager=LinearLayoutManager(this)
        //linearLayoutManager.stackFromEnd=true
        rvChat.layoutManager=linearLayoutManager
        chatAdapter = ChatAdapter(
            this@ChatActivity,
            viewModel.getUserId(),
            messageList,
            this
        )
        rvChat.adapter=chatAdapter
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            tvSendMessage.id ->{
                viewModel.sendMessage(Message(viewModel.getUserId(),viewModel.user!!.displayName,etMessageField.text.toString(),null, MESSAGE_SEND))
                //hideKeyboard()
                etMessageField.setText("")
            }
            ivSendImage.id ->{
                var intent= Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/jpeg";
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        }
    }

    private fun messageListener(){
        rvChat.visibility=View.GONE
        pbLoadChats.visibility=View.VISIBLE
        mChildEventListener = object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                showSnackbar("Cancelled",0)
            }
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                showSnackbar("Child Moved",0)

            }
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                showSnackbar("Child Changes",0)

            }
            override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                showSnackbar("onChild Added",0)
                rvChat.visibility=View.VISIBLE
                pbLoadChats.visibility=View.GONE
                messageList.add(messageList.size,snapshot.getValue(Message::class.java) ?: Message())
                chatAdapter!!.notifyItemInserted(messageList.size)
                rvChat.scrollToPosition(chatAdapter!!.itemCount - 1)
            }
            override fun onChildRemoved(p0: DataSnapshot) {
                showSnackbar("child Removed",0)

            }
        }
        viewModel.getDatabaseReference().addChildEventListener(mChildEventListener!!)
    }

    override fun onItemClick(position: Int) {

    }

    override fun onStarClick(position: Int) {
    }

    override fun onStarted() {
       /* pbLoadChats.visibility=View.VISIBLE
        rvChat.visibility=View.GONE
        tvAdditonalMessage.visibility=View.GONE*/
    }

    override fun onSuccess() {
        /*pbLoadChats.visibility=View.GONE
        rvChat.visibility=View.GONE*/
    }

    override fun onComplete() {
        /*if(messageList.size <= 0){
            rvChat.visibility=View.GONE
            tvAdditonalMessage.text="Be the first one to send message!"
            tvAdditonalMessage.visibility=View.VISIBLE
        }else{
            tvAdditonalMessage.visibility=View.GONE
            rvChat.visibility=View.VISIBLE
        }*/
    }

    override fun onFailure(message: String) {
        /*pbLoadChats.visibility=View.GONE
        tvAdditonalMessage.text=message
        tvAdditonalMessage.visibility=View.VISIBLE*/
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_PHOTO_PICKER){
            if(resultCode == Activity.RESULT_OK) {
                var selectedImage = data!!.data

            }
        }
    }

}
