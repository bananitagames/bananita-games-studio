package com.bananitagames.studio.actions;

import java.util.ArrayList;

import com.bananitagames.studio.GameObject;

public class Behavior implements ActionEventsListener
{
	
	int currentAction;
	ArrayList<Action> actions;
	ActionEventsListener listener;
	boolean isLinear; //Opposite is cyclic
	
	public Behavior(Action[] actions)
	{
		this.actions = new ArrayList<Action>();
		for(Action action: actions)this.actions.add(action.setActionEventListener(this));
	}
	
	/**
	 * This method sets the listener wich the behavior will send actions information.
	 * You DON'T have to call this method because it's designed to be called only
	 * in Behavior class extensions designed in the Framework
	 */
	protected Behavior setActionEventsListener(ActionEventsListener listener)
	{
		this.listener = listener;
		return this;
	}
	
	public void update(GameObject gameObject)
	{
		if(isLinear && currentAction < actions.size() || !isLinear)
			actions.get(currentAction).update(gameObject);
	}

	public Behavior setLinear()
	{
		isLinear = true;
		return this;
	}

	public Behavior setCyclic()
	{
		isLinear = false;
		return this;
	}

	public boolean isLinear()
	{
		return isLinear;
	}

	public boolean isCyclic()
	{
		return !isLinear;
	}

	@Override
	public void onActionStarted(Action action) {

	}

	@Override
	public void onActionCompleted(Action action) {
		if(isLinear)
		{
			currentAction++;
		}
		else
		{
			currentAction = (currentAction++)%actions.size();
		}
	}
}
