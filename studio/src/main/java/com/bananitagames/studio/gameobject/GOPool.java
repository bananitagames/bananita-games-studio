package com.bananitagames.studio.gameobject;

import android.util.Log;

import com.bananitagames.studio.CONSTANTS;
import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.memoryutils.Pool;
import com.bananitagames.studio.memoryutils.Pool.PoolObjectFactory;

public class GOPool 
{

	private static final int POOL_OBJECTS = 1024;
	private PoolObjectFactory<GameObject> factory;
	public Pool<GameObject> pool;
	
	public static final GOPool instance = new GOPool();
	private GOPool()
	{
		factory = new PoolObjectFactory<GameObject>() {

			@Override
			public GameObject createObject() {
				return new GameObject();
			}
		};
		if(CONSTANTS._DEBUG) Log.d(CONSTANTS._TAG, "creating pool objects...");
		pool = new Pool<GameObject>(factory, POOL_OBJECTS);
		for(int i = 0; i < POOL_OBJECTS; i++)
		{
			pool.free(new GameObject());
		}
		if(CONSTANTS._DEBUG) Log.d(CONSTANTS._TAG, "Pool objects created");
	}
}
