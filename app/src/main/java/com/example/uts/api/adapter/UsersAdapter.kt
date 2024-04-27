package com.example.uts.api.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uts.api.model.Users
import com.example.uts.R

class UsersAdapter(private val onClick: (Users) -> Unit) :
    ListAdapter<Users, UsersAdapter.UsersViewHolder>(UsersCallBack){

    class UsersViewHolder(itemView: View, val onClick: (Users) -> Unit) :
        RecyclerView.ViewHolder(itemView){

        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val username: TextView = itemView.findViewById(R.id.username)
        private val firstName: TextView = itemView.findViewById(R.id.firstName)
        private val lastName: TextView = itemView.findViewById(R.id.lastName)

        private var currentUser: Users? = null

        init {
            itemView.setOnClickListener{
                currentUser?.let{
                    onClick(it)
                }
            }
        }

        fun bind(users: Users){
            currentUser = users

            username.text = users.username
            firstName.text = users.firstName
            lastName.text = users.lastName

            Glide.with(itemView).load(users.image).centerCrop().into(imageView)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_users, parent, false)
        return UsersViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)
    }

}

object UsersCallBack : DiffUtil.ItemCallback<Users>(){
    override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
        return oldItem.id == newItem.id
    }

}
