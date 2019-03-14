package com.bananitagames.studio;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class DATA
{

	private DATA(){}

	///////////////////////////////////////////////////////
	///	SAVE & LOAD INTS
	///////////////////////////////////////////////////////

	public static void saveInteger(String key, int value)
	{
		if(CONSTANTS._DEBUG_DATA) Log.d(CONSTANTS._TAG, "save integer key:["+key+"] value:["+value+"]");
		ActivityGame a = ActivityGameManager.getInstance().getActivity();
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(a).edit();
		editor.putInt(key, value);
		editor.apply();
	}

	public static int loadInteger(String key, int defValue)
	{
		ActivityGame a = ActivityGameManager.getInstance().getActivity();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(a);
		int result = prefs.getInt(key, defValue);
		if(CONSTANTS._DEBUG_DATA) Log.d(CONSTANTS._TAG, "load integer key:["+key+"] default:["+defValue+"] result:["+result+"]");
		return result;

	}

	///////////////////////////////////////////////////////
	///	SAVE & LOAD BOOLEANS
	///////////////////////////////////////////////////////

	public static void saveBoolean(String key, boolean value)
	{
		if(CONSTANTS._DEBUG_DATA) Log.d(CONSTANTS._TAG, "save boolean key:["+key+"] value:["+value+"]");
		ActivityGame a = ActivityGameManager.getInstance().getActivity();
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(a).edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	public static boolean loadBoolean(String key, boolean defValue)
	{
		ActivityGame a = ActivityGameManager.getInstance().getActivity();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(a);
		boolean result =  prefs.getBoolean(key, defValue);
		if(CONSTANTS._DEBUG_DATA) Log.d(CONSTANTS._TAG, "load boolean key:["+key+"] default:["+defValue+"] result:["+result+"]");
		return result;
	}

	///////////////////////////////////////////////////////
	///	SAVE & LOAD STRINGS
	///////////////////////////////////////////////////////

	public static String getString(int id)
	{
		ActivityGame a = ActivityGameManager.getInstance().getActivity();
		return a.getString(id);
	}

	public static String[] getStringArray(int id)
	{
		return ActivityGameManager.getInstance().getActivity().getResources().getStringArray(id);
	}

	public static String readTextFileFromResource(int resourceId)
	{
		Context context = ActivityGameManager.getInstance().getActivity();
		StringBuilder body = new StringBuilder();
		try
		{
			InputStream inputStream = context.getResources().openRawResource(resourceId);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String nextLine;
			while((nextLine = bufferedReader.readLine()) != null)
			{
				body.append(nextLine);
				body.append("\n");
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException("Couldn't open resource: " + resourceId, e);
		}
		catch (Resources.NotFoundException nfe)
		{
			throw  new RuntimeException("Resource not found: " + resourceId, nfe);
		}
		return body.toString();
	}

	///////////////////////////////////////////////////////
	///	SAVE & LOAD STRINGS
	///////////////////////////////////////////////////////

	public static ViewGroup getMainView()
	{
		ActivityGame a = ActivityGameManager.getInstance().getActivity();
		return a.getViewGroup();
	}

	public static void runOnUIThread(Runnable runnable)
	{
		ActivityGameManager.getInstance().getActivity().runOnUiThread(runnable);
	}

	public static String getApplicationName() {
		Activity a = ActivityGameManager.getInstance().getActivity();
		ApplicationInfo applicationInfo = a.getApplicationInfo();
		int stringId = applicationInfo.labelRes;
		return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : a.getString(stringId);
	}
}
