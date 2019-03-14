package com.bananitagames.studio.actions;


import com.bananitagames.studio.GameObject;

/**
 * When you create an action, you have to call the
 * method endAction at least once to let
 * the action end at some moment
 * @author Luis
 *
 */
public abstract class Action 
{
	private ActionEventsListener listener;
	private boolean started;
	
	protected Action setActionEventListener(ActionEventsListener listener)
	{
		this.listener = listener;
		return this;
	}
	
	public void update(GameObject gameObject)
	{
		if(!started)
		{
			listener.onActionStarted(this);
			started = true;
		}
		
	}
	
	public void endAction()
	{
		listener.onActionCompleted(this);
	}
}
