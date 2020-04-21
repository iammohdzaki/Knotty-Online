package com.zaphlabs.knotty_online.ui.home.adapter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.data.model.Chat
import kotlinx.android.synthetic.main.item_view_home_layout.view.*

class ChatAdapter(private var mActivity: Activity, private val accountList:ArrayList<Chat>, private val recyclerClickListener: RecyclerClickListener  ) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_home_layout,parent,false)
        return ViewHolder(rootView,recyclerClickListener)
    }

    override fun getItemCount(): Int {
        return accountList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userAccount=accountList[position]

        holder.AccountImage.setColorFilter(getRandomMaterialColor("400"))

    }

    class ViewHolder(itemView: View, private var recyclerClickListener: RecyclerClickListener) : RecyclerView.ViewHolder(itemView) ,View.OnClickListener{


        val AccountImage= itemView.ivAccountImage!!
        val AccountIconText=itemView.tvImageText!!
        val AccountUserName= itemView.tvAccountUserName!!
        val AccountTitle= itemView.tvAccountTitle!!
        val AccountTimeStamp= itemView.tvAccountTimeStamp!!
        val AccountStarred= itemView.ivStar!!
        val AccountEmail= itemView.tvAccountEmailOrNote!!
        val AccountNote= itemView.tvAccountEmailOrNote!!
        val pbDoing=itemView.pbDoing!!

        init {
            itemView.setOnClickListener (this)
            AccountStarred.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when(v?.id){
                R.id.ivStar -> {
                    recyclerClickListener.onStarClick(adapterPosition)
                    pbDoing.visibility=View.VISIBLE
                    AccountStarred.visibility=View.GONE
                }
                else -> recyclerClickListener.onItemClick(adapterPosition)
            }
        }
    }

    /**
     * chooses a random color from array.xml
     */
    private fun getRandomMaterialColor(typeColor: String): Int {
        var returnColor = Color.GRAY
        val arrayId = mActivity.resources.getIdentifier("mdcolor_$typeColor", "array", mActivity.packageName)

        if (arrayId != 0) {
            val colors = mActivity.resources.obtainTypedArray(arrayId)
            val index = (Math.random() * colors.length()).toInt()
            returnColor = colors.getColor(index, Color.GRAY)
            colors.recycle()
        }
        return returnColor
    }
}