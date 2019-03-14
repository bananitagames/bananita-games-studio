package com.bananitagames.studio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.bananitagames.studio.interfaces.Texture;

import static android.content.ContentValues.TAG;

final class AndroidTexture implements Texture
{

    //Accessed from outer objects
    private int textureId;
    private int width;
    private int height;
	private String fileName = "Unspecified";
	private int resourceId;
	private boolean isLoadedFromResourceId;

    
    public AndroidTexture(String fileName)
	{
		this.fileName = fileName;
		this.isLoadedFromResourceId = false;
		init();
	}

	public AndroidTexture(int resourceId)
	{
		this.resourceId = resourceId;
		this.isLoadedFromResourceId = true;
		init();
	}

	public void init()
	{
		load();
	}
    
    private void load() {
        int[] textureIds = new int[1];
		GLES20.glGenTextures(1, textureIds, 0);
        textureId = textureIds[0];
        
        InputStream in=null;
        try {
        	if(CONSTANTS._DEBUG_RESOURCES) Log.d(CONSTANTS._TAG, "Start loading texture [" + getTextureName() + "]");
            if(isLoadedFromResourceId)
				in = ActivityGameManager.getInstance().getActivity().getResources().openRawResource(resourceId);
			else
				in = ActivityGameManager.getInstance().getActivity().getFileIO().readAsset(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
			width = bitmap.getWidth();
			height = bitmap.getHeight();
			loadBitmapToOpenGL(bitmap);
         	bitmap.recycle();

        } catch(IOException e) {
			throw new RuntimeException("Couldn't load texture '" + getTextureName() +"'", e);
        } catch (NotFoundException e){ //Only occurs to loading resource Id
			throw new RuntimeException("Couldn't find texture with Id '" + resourceId +"'", e);
		} finally {
            if(in != null)
                try { in.close(); } catch (IOException e) { }
        }
    }

    private void loadBitmapToOpenGL(Bitmap bitmap)
    {
        if(CONSTANTS._DEBUG_RESOURCES) Log.d(CONSTANTS._TAG, "End loading texture [" + getTextureName() + "]");
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }
    
    public void dispose() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        int[] textureIds = { textureId };
		GLES20.glDeleteTextures(1, textureIds, 0);
    }

	public String getTextureName()
	{
		ActivityGame activity = ActivityGameManager.getInstance().getActivity();
		return isLoadedFromResourceId ? activity.getResources().getResourceName(resourceId) : fileName;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//	GETTERS
	////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int getTextureId()
	{
		return textureId;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	public String getFileName()
	{
		return fileName;
	}

	public int getResourceId()
	{
		return resourceId;
	}

	public boolean isLoadedFromResourceId()
	{
		return isLoadedFromResourceId;
	}

	private boolean isUnused;

	public void setUnused(boolean isUnused)
	{
		this.isUnused = isUnused;
	}

	public boolean isUnused()
	{
		return isUnused;
	}

	private boolean isDeprecated = false;

	public void setDeprecated(boolean isDeprecated)
	{
		this.isDeprecated = isDeprecated;
	}

	public boolean isDeprecated()
	{
		return this.isDeprecated;
	}

}