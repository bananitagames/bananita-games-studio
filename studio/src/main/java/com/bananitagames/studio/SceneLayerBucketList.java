package com.bananitagames.studio;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by luis on 10/11/2017.
 */

public final class SceneLayerBucketList
{

	private final SceneLayer sceneLayer;
	private final int textureIdentifier;
	private ArrayList<SceneLayerBucket> buckets;
	private int currentIndex;

	public SceneLayerBucketList(SceneLayer sceneLayer, int textureIdentifier)
	{
		this.sceneLayer = sceneLayer;
		this.textureIdentifier = textureIdentifier;
		this.buckets = new ArrayList<>();
		this.currentIndex = 0;
	}

	public void draw()
	{
		for(SceneLayerBucket bucket : buckets)
		{
			bucket.draw();
		}
		currentIndex = 0;

	}

	public void drawTextureRegion(float u1, float u2, float v1, float v2, float x, float y, float width, float height, float angle, int colorTopLeft, int colorTopRight, int colorBottonLeft, int colorBottomRight)
	{
		int listIndex = currentIndex >> SceneLayerBucket._SHIFT_BITS;
		SceneLayerBucket bucket;
		if(listIndex >= buckets.size())
		{
			if(CONSTANTS._DEBUG) Log.d(CONSTANTS._TAG, "SceneLayerBucket_Created["+ SceneLayerBucket._SPRITES_MAX +"/"+(buckets.size()+1)*SceneLayerBucket._SPRITES_MAX+"sprites]_LAYER["+sceneLayer.getId()+"]");
			bucket = new SceneLayerBucket(sceneLayer, textureIdentifier);
			buckets.add(bucket);
		}
		else
			bucket = buckets.get(listIndex);
		bucket.drawTextureRegion(u1, u2, v1, v2, x, y, width, height, angle, colorTopLeft, colorTopRight, colorBottonLeft, colorBottomRight);
		currentIndex++;
	}

}
