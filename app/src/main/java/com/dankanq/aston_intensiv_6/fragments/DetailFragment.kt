package com.dankanq.aston_intensiv_6.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dankanq.aston_intensiv_6.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding ?: throw RuntimeException("DetailFragment is null")

    private lateinit var imageUrl: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            Glide.with(requireContext())
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
            etFirstName.setText(firstName)
            etLastName.setText(lastName)
            etPhoneNumber.setText(phoneNumber)
            bSave.setOnClickListener {
                saveContact()
            }
        }

        setContactData()
    }

    private fun setContactData() {

    }

    private fun saveContact() {
        parentFragmentManager.popBackStack()
        val bundle = Bundle().apply {
            with(binding) {
                putString(BUNDLE_FIRST_NAME_KEY, etFirstName.text.toString())
                putString(BUNDLE_LAST_NAME_KEY, etLastName.text.toString())
                putString(BUNDLE_PHONE_NUMBER_KEY, etPhoneNumber.text.toString())
            }
        }
        setFragmentResult(RESULT_KEY, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun parseArgs() {
        val args = requireArguments()
        if (args.containsKey(FIRST_NAME_KEY) && args.containsKey(LAST_NAME_KEY) &&
            args.containsKey(PHONE_NUMBER_KEY)
        ) {
            imageUrl = args.getString(IMAGE_URL_KEY) ?: UNDEFINED_STRING_VALUE
            firstName = args.getString(FIRST_NAME_KEY) ?: UNDEFINED_STRING_VALUE
            lastName = args.getString(LAST_NAME_KEY) ?: UNDEFINED_STRING_VALUE
            phoneNumber = args.getString(PHONE_NUMBER_KEY) ?: UNDEFINED_STRING_VALUE
        }
    }

    companion object {
        private const val IMAGE_URL_KEY = "image_url"
        private const val FIRST_NAME_KEY = "first_name"
        private const val LAST_NAME_KEY = "last_name"
        private const val PHONE_NUMBER_KEY = "phone_number"

        private const val UNDEFINED_STRING_VALUE = ""

        const val RESULT_KEY = "result"
        const val BUNDLE_FIRST_NAME_KEY = "bundle_first_name"
        const val BUNDLE_LAST_NAME_KEY = "bundle_last_name"
        const val BUNDLE_PHONE_NUMBER_KEY = "bundle_phone_number"

        fun newInstance(
            imageUrl: String,
            firstName: String,
            lastName: String,
            phoneNumber: String
        ): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE_URL_KEY, imageUrl)
                    putString(FIRST_NAME_KEY, firstName)
                    putString(LAST_NAME_KEY, lastName)
                    putString(PHONE_NUMBER_KEY, phoneNumber)
                }
            }
        }
    }
}