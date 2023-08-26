package com.mkavaktech.reciperoamer.presentation.info

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.mkavaktech.reciperoamer.R

import com.mkavaktech.reciperoamer.data.entities.InfoItem
import com.mkavaktech.reciperoamer.databinding.FragmentInfoBinding
import com.mkavaktech.reciperoamer.presentation.onboarding.OnboardingActivity
import com.mkavaktech.reciperoamer.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : Fragment(), InfoListAdapter.InfoListListener {
    private lateinit var binding: FragmentInfoBinding
    private lateinit var infoListAdapter: InfoListAdapter
    private lateinit var reviewManager: ReviewManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        reviewManager = ReviewManagerFactory.create(requireContext())
        binding.appInfoTextView.text = getAppInfo()
    }

    private fun setupRecyclerView() {
        infoListAdapter = InfoListAdapter(this)
        binding.infoRecView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = infoListAdapter
        }

        val itemList = arrayListOf(
            InfoItem(title = getString(R.string.app), isHeader = true),
            InfoItem(R.drawable.ic_tour, getString(R.string.application_tour), R.color.cerise),
            InfoItem(R.drawable.ic_lock, getString(R.string.privacy_policy), R.color.byzantium),
            InfoItem(R.drawable.ic_rate, getString(R.string.rate_the_app), R.color.gold),
            InfoItem(title = getString(R.string.general), isHeader = true),
            InfoItem(
                R.drawable.ic_connect, getString(R.string.connect_with_me), R.color.brightBlue
            ),
            InfoItem(R.drawable.ic_feedback, getString(R.string.send_feedback), R.color.black),
            InfoItem(title = getString(R.string.my_other_app), isHeader = true),
            InfoItem(R.drawable.ic_app, getString(R.string.daily_horoscopes), R.color.red),
        )
        infoListAdapter.setInfoItem(itemList)
    }

    private fun getAppInfo(): String {
        return try {
            val packageInfo =
                requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            val appName =
                packageInfo.applicationInfo.loadLabel(requireContext().packageManager).toString()
            val versionName = packageInfo.versionName
            "$appName $versionName"
        } catch (e: PackageManager.NameNotFoundException) {
            "N/A"
        }
    }

    private fun sendFeedbackEmail() {
        val subject = Constants.InfoItems.EMAIL_SUBJECT
        val to = Constants.InfoItems.EMAIL_ADDRESS
        val deviceInfo = getString(R.string.device_model_app_info, Build.MODEL, getAppInfo())

        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, deviceInfo)

        try {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.send_feedback)))
        } catch (ex: Exception) {
            Log.d("Send Feedback", "sendFeedbackEmail: ${ex.message}")
        }
    }

    private fun openWebLink(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    private fun openGooglePlayApp() {
        val packageName = Constants.InfoItems.DAILY_HOROSCOPES_PACKAGE_NAME
        val playStoreIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        try {
            startActivity(playStoreIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun showInAppReview() {
        val reviewFlow = reviewManager.requestReviewFlow()
        reviewFlow.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                reviewManager.launchReviewFlow(requireActivity(), reviewInfo)
            } else {
                Log.d("Review Error", "Failed to start in-app review")
            }
        }
    }

    private fun navigateOnboarding() {
        val intent = Intent(requireContext(), OnboardingActivity::class.java)
        startActivity(intent)
    }

    override fun onInfoItemClick(position: Int) {
        when (position) {
            1 -> navigateOnboarding()
            2 -> openWebLink(Constants.InfoItems.PRIVACY_POLICY)
            3 -> showInAppReview()
            5 -> openWebLink(Constants.InfoItems.CONNECT_WITH_ME)
            6 -> sendFeedbackEmail()
            8 -> openGooglePlayApp()
        }
    }
}