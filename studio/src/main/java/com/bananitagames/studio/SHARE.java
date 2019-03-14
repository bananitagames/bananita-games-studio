package com.bananitagames.studio;

import android.content.Intent;
import android.net.Uri;

/**
 * This class contains methods that can be used to share the game through different social media
 */
public final class SHARE
{

	private SHARE(){}

	public static void shareGameDefault()
	{
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Must have!");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getShareDefaultBody());
		ActivityGameManager.getInstance().getActivity().startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}

	public static void shareGameGoogle()
	{
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND, Uri.parse("https://plus.google.com/[Google+ID]/"));
		sharingIntent.setPackage("com.google.android.apps.plus"); // don't open the browser, make sure it opens in Google+ app
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Must have!");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getShareDefaultBody());
		ActivityGameManager.getInstance().getActivity().startActivity(sharingIntent );
	}

	public static void shareGameFacebook()
	{
		String urlToShare = getShareDefaultBody();
		Intent intent;

		try {
			intent = new Intent();
			intent.setClassName("com.facebook.katana", "com.facebook.katana.activity.composer.ImplicitShareIntentHandler");
			intent.setAction("android.intent.action.SEND");
			intent.setType("text/plain");
			intent.putExtra("android.intent.extra.TEXT", urlToShare);
		} catch (Exception e) {
			// If we failed (not native FB app installed), try share through SEND
			String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
		}
		ActivityGameManager.getInstance().getActivity().startActivity(intent);
	}

	public static void shareGameTwitter()
	{
		final String appPackageName = ActivityGameManager.getInstance().getActivity().getPackageName();
		final String myUrl = "https://play.google.com/store/apps/details?id=" + appPackageName;
		final String text = getShareDefaultBodyWithoutLink();
		final String url = "http://www.twitter.com/intent/tweet?url="+myUrl+"&text="+text;
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		ActivityGameManager.getInstance().getActivity().startActivity(i);
	}

	public static void shareGameLike()
	{
		final String appPackageName = ActivityGameManager.getInstance().getActivity().getPackageName(); // getPackageName() from Context or Activity object
		try {
			ActivityGameManager.getInstance().getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
		} catch (android.content.ActivityNotFoundException anfe) {
			ActivityGameManager.getInstance().getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
		}
	}

	public static void openGooglePlayDeveloperSearch(String developerName)
	{
		try
		{
			ActivityGameManager.getInstance().getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id="+developerName)));
		}
		catch (android.content.ActivityNotFoundException anfe)
		{
		}
	}

	private static String getShareDefaultBody()
	{

		return "You have to try this game, " + DATA.getApplicationName() + ": " + "https://play.google.com/store/apps/details?id=" + ActivityGameManager.getInstance().getActivity().getPackageName();
	}

	private static String getShareDefaultBodyWithoutLink()
	{
		return "You have to try this game, " + DATA.getApplicationName();
	}

}
