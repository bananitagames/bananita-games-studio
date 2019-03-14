package com.bananitagames.studio.actions;

import com.bananitagames.studio.TIME;
import com.bananitagames.studio.GameObject;

public class ActionWait extends Action {

	private final float time;
	private float storedTime;
	
	public ActionWait(float seconds)
	{
		this.time = seconds;
	}
	
	@Override
	public void update(GameObject gameObject)
	{
		super.update(gameObject);
		storedTime += TIME.deltaTime;
		if(storedTime >= time) 
		{
			storedTime = 0f;
			endAction();
		}
	}

}
