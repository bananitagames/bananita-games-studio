package com.bananitagames.studio.ads;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.bananitagames.studio.DATA;

/**
 * Created by luis on 04/08/2017.
 */

public final class ADS
{
	private static AdView mAdView;
	private static InterstitialAd mInterstitialAd;
	private static RewardedVideoAd mRewardedVideoAd;
	private static String testDevice = "";

	private ADS(){}


	////////////////////////////////////////////////////////////////////////////////////////////////
	///	REWARDED VIDEO ADS
	////////////////////////////////////////////////////////////////////////////////////////////////

	public static void createRewardedVideoAd(final String id, final RewardedVideoAdListener listener)
	{
		DATA.runOnUIThread(new Runnable()
		{
			@Override
			public void run()
			{
				ViewGroup view = DATA.getMainView();
				mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(view.getContext());
				mRewardedVideoAd.setRewardedVideoAdListener(listener);
				mRewardedVideoAd.loadAd(id, new AdRequest.Builder().addTestDevice(testDevice).build());
			}
		});
	}

	public static void showRewardedVideoAdIfLoaded()
	{
		if(mRewardedVideoAd != null)
			DATA.runOnUIThread(new Runnable()
			{
				@Override
				public void run()
				{
					if(mRewardedVideoAd.isLoaded())
						mRewardedVideoAd.show();
				}
			});
	}


	////////////////////////////////////////////////////////////////////////////////////////////////
	///	INTERSTITIAL VIDEO ADS
	////////////////////////////////////////////////////////////////////////////////////////////////

	public static void createInterstitialVideoAd(final String id, final AdListener listener)
	{
		DATA.runOnUIThread(new Runnable()
		{
			@Override
			public void run()
			{
				ViewGroup view = DATA.getMainView();
				mInterstitialAd = new InterstitialAd(view.getContext());
				mInterstitialAd.setAdUnitId(id);
				mInterstitialAd.setAdListener(listener);
				mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(testDevice).build());
			}
		});
	}

	public static void showInterstitialVideoAdIfLoaded()
	{
		if(mInterstitialAd != null)
			DATA.runOnUIThread(new Runnable()
			{
				@Override
				public void run()
				{
					if(mInterstitialAd.isLoaded())
						mInterstitialAd.show();
				}
			});
	}



	/**
	 * Starts an interstitial withouth any control of it
	 * @param id
	 */
	public static void startInterstitialOnTheGo(final String id)
	{
		DATA.runOnUIThread(new Runnable() {
			@Override
			public void run() {
				ViewGroup view = DATA.getMainView();
				mInterstitialAd = new InterstitialAd(view.getContext());
				mInterstitialAd.setAdUnitId(id);

				mInterstitialAd.setAdListener(new AdListener() {
					@Override
					public void onAdClosed() {
						super.onAdClosed();
					}

					@Override
					public void onAdFailedToLoad(int i) {
						super.onAdFailedToLoad(i);
					}

					@Override
					public void onAdLeftApplication() {
						super.onAdLeftApplication();
					}

					@Override
					public void onAdOpened() {
						super.onAdOpened();
					}

					@Override
					public void onAdLoaded() {
						super.onAdLoaded();
						mInterstitialAd.show();
					}
				});
				// Create an ad request
				AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
				if(!testDevice.equals(""))
					adRequestBuilder.addTestDevice(testDevice);
				mInterstitialAd.loadAd(adRequestBuilder.build());
			}
		});
	}

	/**
	 * With a given id, it will try to start a banner at the bottom side of the screen
	 * @param id
	 */
	public static void startBanner(final String id)
	{
		DATA.runOnUIThread(new Runnable() {
			@Override
			public void run() {
				// Create banner ad view
				ViewGroup view = DATA.getMainView();
				mAdView = new AdView(view.getContext());
				mAdView.setAdSize(AdSize.SMART_BANNER);
				mAdView.setAdUnitId(id);
				RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);

				lay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				// Create an ad request
				AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
				if(!testDevice.equals(""))
					adRequestBuilder.addTestDevice(testDevice);
				// Add the adView to the view in hierarchy
				view.addView(mAdView, lay);
				// Start loading the ad
				mAdView.loadAd(adRequestBuilder.build());
			}
		});
	}

	/**
	 * Call this method before calling any starting banner or interstitial method
	 * @param testDevice
	 */
	public static void addTestDevice(String testDevice)
	{
		ADS.testDevice = testDevice;
	}

	public static void changeBannerVisibility(final boolean visible)
	{
		if(mAdView!=null)
		{
			if((visible && mAdView.getVisibility() == View.GONE) || (!visible && mAdView.getVisibility() == View.VISIBLE))
				DATA.runOnUIThread(new Runnable() {
					@Override
					public void run() {
						mAdView.setVisibility(visible ? View.VISIBLE : View.GONE);
					}
				});
		}
	}

}
