package com.dankanq.aston_intensiv_6.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dankanq.aston_intensiv_6.databinding.ItemContactBinding
import com.dankanq.aston_intensiv_6.model.Contact

class ContactAdapter(
    private val context: Context,
) : ListAdapter<Contact, ViewHolder>(ContactDiffCallback) {
    var onContactClick: ((Contact) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = getItem(position)
        with((holder as ContactViewHolder).binding) {
            with(contact) {
                Glide.with(context)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)
                tvId.text = id.toString()
                tvFirstName.text = firstName
                tvLastName.text = lastName
                tvPhoneNumber.text = phoneNumber
                root.setOnClickListener { onContactClick?.invoke(this) }
            }
        }
    }
}