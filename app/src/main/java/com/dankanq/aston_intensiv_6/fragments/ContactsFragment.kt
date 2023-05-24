package com.dankanq.aston_intensiv_6.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import com.dankanq.aston_intensiv_6.R
import com.dankanq.aston_intensiv_6.adapter.ContactAdapter
import com.dankanq.aston_intensiv_6.fragments.DetailFragment.Companion.BUNDLE_FIRST_NAME_KEY
import com.dankanq.aston_intensiv_6.fragments.DetailFragment.Companion.BUNDLE_LAST_NAME_KEY
import com.dankanq.aston_intensiv_6.fragments.DetailFragment.Companion.BUNDLE_PHONE_NUMBER_KEY
import com.dankanq.aston_intensiv_6.fragments.DetailFragment.Companion.RESULT_KEY
import com.dankanq.aston_intensiv_6.model.Contact

class ContactsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContactAdapter(requireContext())
        adapter.onContactClick = { contact ->
            launchDetailFragment(contact.id, contact)
        }
        adapter.submitList(contacts)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter

        setFragmentResultListener()
    }

    private fun setFragmentResultListener() {
        setFragmentResultListener(RESULT_KEY) { _, bundle ->
            val firstName = bundle.getString(BUNDLE_FIRST_NAME_KEY).orEmpty()
            val lastName = bundle.getString(BUNDLE_LAST_NAME_KEY).orEmpty()
            val phoneNumber = bundle.getString(BUNDLE_PHONE_NUMBER_KEY).orEmpty()

            contacts[launchedItemPosition].apply {
                this.firstName = firstName
                this.lastName = lastName
                this.phoneNumber = phoneNumber
            }

            val newList = adapter.currentList.toMutableList()
            newList[launchedItemPosition] = contacts[launchedItemPosition]
            adapter.submitList(newList)
        }
    }

    private fun launchDetailFragment(position: Int, contact: Contact) {
        launchedItemPosition = position

        val detailFragment = DetailFragment.newInstance(
            contact.imageUrl,
            contact.firstName,
            contact.lastName,
            contact.phoneNumber
        )
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val BASE_IMAGE_URL = "https://picsum.photos/id/"
        private const val IMAGE_URL_SIZE_ENDPOINTS = "/200/200"

        private var launchedItemPosition = -1
        private val contacts = createContacts()
        private fun createContacts(): List<Contact> {
            val contacts = mutableListOf<Contact>()
            repeat(101) { index ->
                contacts.add(
                    Contact(
                        id = index,
                        firstName = "first_name_$index",
                        lastName = "last_name_$index",
                        phoneNumber = "7900$index",
                        imageUrl = BASE_IMAGE_URL + index + IMAGE_URL_SIZE_ENDPOINTS
                    )
                )
            }
            return contacts
        }

        fun newInstance() = ContactsFragment()
    }
}