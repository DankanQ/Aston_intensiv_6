package com.dankanq.aston_intensiv_6.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dankanq.aston_intensiv_6.model.Contact

object ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}