package com.bananitagames.studio;

/**
 * This class is used to have access to the main activity game class object, from everywhere
 */
final class ActivityGameManager
{
	//
	// SINGLETON accessing
	//
	private static ActivityGameManager instance;

	private ActivityGameManager(){}

	public static ActivityGameManager getInstance()
	{
		if(instance == null)
			instance = new ActivityGameManager();
		return instance;
	}
	//
	// VARS
	//
	private ActivityGame activity;

	//
	// METHODS
	//
	public void init(ActivityGame activity)
	{
		if(activity != null)
			this.activity = activity;
	}

	public ActivityGame getActivity()
	{
		if(activity == null)
		{
			throw new RuntimeException("Your project is not using an ActivityGame Extension");
		}
		return activity;
	}
}
