package com.bananitagames.studio.memoryutils;

import java.util.ArrayList;
import java.util.List;

import com.bananitagames.studio.CONSTANTS;

import android.util.Log;

public class Pool<T> 
{
    public interface PoolObjectFactory<T> 
    {
        T createObject();
    }

    private final List<T> freeObjects;
    private final PoolObjectFactory<T> factory;
    private final int maxSize;
    private int size;

    public Pool(PoolObjectFactory<T> factory, int maxSize) 
    {
        this.factory = factory;
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList<>(maxSize);
        this.size = 0;
    }

    public synchronized T newObject()
    {
        T object;
        if (size <= 0) object = factory.createObject();
        else {
            size--;
            object = freeObjects.remove(size);
        }
        return object;
    }

    public synchronized void free(T object)
    {
        if (size < maxSize)
		{
			size++;
            freeObjects.add(object);
        }
        else Log.d(CONSTANTS._TAG, "trying to free an object of class=" + object.getClass().getCanonicalName());
    }
}
