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

import com.mkavaktech.reciperoamer.data.entities.InfoItem
import com.mkavaktech.reciperoamer.databinding.FragmentInfoBinding
import com.mkavaktech.reciperoamer.presentation.onboarding.OnboardingActivity
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
        val itemList = ArrayList<InfoItem>(InfoItem.itemList)
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
        val subject = "Recipe Roamer Feedback"
        val to = "merthan.kavak@gmail.com"
        val deviceInfo = "Device Model: ${Build.MODEL}\nApp Info: ${getAppInfo()}"

        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, deviceInfo)

        try {
            startActivity(Intent.createChooser(emailIntent, "Send Feedback"))
        } catch (ex: Exception) {
            Log.d("Send Feedback", "sendFeedbackEmail: ${ex.message}")
        }
    }

    private fun openWebLink(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    private fun openGooglePlayApp() {
        val packageName = "com.mkavaktech.dailyhoroscopeapp"
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
                Log.d("reviewError", "Failed to start in-app review")
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
            2 -> openWebLink("https://docs.google.com/document/d/1WhKS0CF5l4xcbcNy49nW-hrrhQ4GdFd3fNYv0keY9nw")
            3 -> showInAppReview()
            5 -> openWebLink("https://bento.me/merthan")
            6 -> sendFeedbackEmail()
            8 -> openGooglePlayApp()
        }
    }
}