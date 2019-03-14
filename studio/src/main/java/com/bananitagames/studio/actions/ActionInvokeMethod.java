package com.bananitagames.studio.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.bananitagames.studio.GameObject;

public class ActionInvokeMethod extends Action 
{

	private final String methodName;
	private Method method;
	
	public ActionInvokeMethod(String methodName)
	{
		this.methodName = methodName;
	}
	
	@Override
	public void update(GameObject gameObject)
	{
		super.update(gameObject);
		// Try to get the method
		try 
		{
			method = gameObject.getClass().getMethod(methodName);
		}
		catch (SecurityException e) 
		{
			endAction();
		}
		catch (NoSuchMethodException e) 
		{
			endAction();}
		
		try 
		{
			method.invoke(gameObject);
		} 
		catch (IllegalArgumentException e) {} 
		catch (IllegalAccessException e) {} 
		catch (InvocationTargetException e) {}
		endAction();
	}
}
