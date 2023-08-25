package com.mkavaktech.reciperoamer.presentation.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mkavaktech.reciperoamer.R
import com.mkavaktech.reciperoamer.data.entities.OnboardingItem
import com.mkavaktech.reciperoamer.databinding.ActivityOnboardingBinding
import com.mkavaktech.reciperoamer.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onboardingItemsListAdapter: OnboardingListAdapter
    private lateinit var indicatorLayout: LinearLayout

    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val PREF_KEY_ONBOARDING = "onboarding"
        const val PREF_KEY_ONBOARDING_SHOWN = "onboarding_shown"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(
            PREF_KEY_ONBOARDING, Context.MODE_PRIVATE
        )

        setupRecyclerView()
        setupViewPager()
        setupIndicators()

        onSkipButtonClick()
        onNextImageClick()
    }


    private fun setupRecyclerView() {
        onboardingItemsListAdapter = OnboardingListAdapter()
        binding.onBoardingViewPager.apply {
            adapter = onboardingItemsListAdapter
        }

        val itemList = ArrayList<OnboardingItem>(OnboardingItem.itemList)
        onboardingItemsListAdapter.setItems(itemList)
    }

    private fun setupViewPager() {
        binding.onBoardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (binding.onBoardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
    }

    private fun navigateHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun saveOnboardingStatus(status: Boolean) {
        sharedPreferences.edit {
            putBoolean(PREF_KEY_ONBOARDING_SHOWN, status)
        }
    }

    private fun onSkipButtonClick() {
        binding.skipTextView.setOnClickListener {
            saveOnboardingStatus(true)
            navigateHome()
        }
    }

    private fun onNextImageClick() {
        binding.nextImage.setOnClickListener {
            val nextItem = binding.onBoardingViewPager.currentItem + 1
            if (nextItem < onboardingItemsListAdapter.itemCount) {
                binding.onBoardingViewPager.currentItem = nextItem
            } else {
                saveOnboardingStatus(true)
                navigateHome()
            }
        }
    }

    private fun createIndicatorImageView(): ImageView {
        val imageView = ImageView(applicationContext)
        imageView.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext, R.drawable.indicator_inactive_bg
            )
        )
        imageView.layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            setMargins(8, 0, 0, 0)
        }
        return imageView
    }

    private fun setupIndicators() {
        indicatorLayout = binding.indicatorLayout
        val indicators = arrayOfNulls<ImageView>(onboardingItemsListAdapter.itemCount)

        for (i in indicators.indices) {
            indicators[i] = createIndicatorImageView()
            indicatorLayout.addView(indicators[i])
        }
        setCurrentIndicator(0)
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorLayout.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorLayout.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_active_bg
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_inactive_bg
                    )
                )
            }
        }
    }


}