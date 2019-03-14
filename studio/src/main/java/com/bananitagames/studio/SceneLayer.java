package com.bananitagames.studio;

import android.opengl.GLES20;
import android.util.Log;
import android.util.SparseArray;

import com.bananitagames.studio.interfaces.LAYER;

import java.util.ArrayList;


final class SceneLayer implements LAYER
{

	// SHADER PROGRAM VARS
	private static final int _DEFAULT_LOCATION 	= -1;
	private static final String _U_MVP 					= "uMVP";
	private static final String _U_SAMPLER_2D 			= "uTexture";
	private static final String _A_VERTEX_POS			= "aVertexPos";
	private static final String _A_POSITION 			= "aPosition";
	private static final String _A_TEXCOORD 			= "aTexCoord";
	private static final String _A_SIZE 				= "aSize";
	private static final String _A_ROTATION 			= "aRotation";
	private static final String _A_COLOR 				= "aColor";

	//INT TO HANDLE CURRENT SCENELAYER
    private static final int _DEFAULT_BLEND_SRC  = GLES20.GL_SRC_ALPHA;
	private static final int _DEFAULT_BLEND_DST  = GLES20.GL_ONE_MINUS_SRC_ALPHA;
	private static final int _DEFAULT_MIN_FILTER = GLES20.GL_LINEAR;
	private static final int _DEFAULT_MAG_FILTER = GLES20.GL_LINEAR;

    // GLES20 VARS
    private int blendFuncSrc;
    private int blendFuncDst;
	private int minFilter;
	private int magFilter;
    private SceneShader shader;

	// SHADER PROGRAM VARS, ACCESSIBLE FROM BUCKETS
	public int loc_uniform_sampler_2d	= _DEFAULT_LOCATION;
	public int loc_uniform_mvp 			= _DEFAULT_LOCATION;
	public int loc_attr_vertex_pos		= _DEFAULT_LOCATION;
	public int loc_attr_pos 			= _DEFAULT_LOCATION;
	public int loc_attr_tex_coor 		= _DEFAULT_LOCATION;
	public int loc_attr_size 			= _DEFAULT_LOCATION;
	public int loc_attr_rotation 		= _DEFAULT_LOCATION;
	public int loc_attr_color 			= _DEFAULT_LOCATION;

	private static int currentGlobalLayerId;
    private final int id;
    private final ArrayList<GameObject> gameObjects;
	private final SparseArray<SceneLayerBucketList> buckets;

	public SceneLayer(int id)
	{
		this.id = id;
		this.gameObjects = new ArrayList<>();
		this.buckets = new SparseArray<>();
		setBlendFunc(_DEFAULT_BLEND_SRC, _DEFAULT_BLEND_DST);
		setFilters(_DEFAULT_MIN_FILTER, _DEFAULT_MAG_FILTER);
		setShader(SHADERS.defaultShader());
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// INTERFACE
	//
	////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public LAYER setBlendFunc(int src, int dst)
	{
		blendFuncSrc = src;
		blendFuncDst = dst;
		return this;
	}

	@Override
	public LAYER setBlendFuncMultiply()
	{
		blendFuncSrc = GLES20.GL_DST_COLOR;
		blendFuncDst = GLES20.GL_ZERO;
		return this;
	}

	@Override
	public LAYER setBlendFuncAdditive()
	{
		blendFuncSrc = GLES20.GL_SRC_ALPHA;
		blendFuncDst = GLES20.GL_ONE;
		return this;
	}

	@Override
	public LAYER setBlendFuncNormal()
	{
		blendFuncSrc = GLES20.GL_SRC_ALPHA;
		blendFuncDst = GLES20.GL_ONE_MINUS_SRC_ALPHA;
		return this;
	}

	@Override
	public LAYER setFilters(int minFilter, int magFilter)
	{
		this.minFilter = minFilter;
		this.magFilter = magFilter;
		return this;
	}

	@Override
	public LAYER setShader(SceneShader shader)
	{
		this.shader = shader;
		setShaderProgramLocationVars();
		return this;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// PUBLIC
	//
	////////////////////////////////////////////////////////////////////////////////////////////////

    public void update()
    {
        for(int i = gameObjects.size()-1; i >= 0; i--)
        {
			GameObject go = gameObjects.get(i);
			if(!go.destroyed)
				go.update();
			// During object update, it's very trendy to be destroyed
			if(go.destroyed)//If game object is marked as destroyed, remove from this list
				gameObjects.remove(i);

        }

    }

    public void present()
    {
		currentGlobalLayerId = id;
		for(GameObject go : gameObjects)
			go.present();
    }

    public void draw()
	{
		if(buckets == null)
			return;
		//Use program
		GLES20.glUseProgram(shader.getProgram());
		//Blending mode
		GLES20.glBlendFunc(blendFuncSrc, blendFuncDst);
		//UNIFORM MVP
		GLES20.glUniformMatrix4fv(loc_uniform_mvp, 1, false, Camera2D._MVP_MATRIX, 0);
		for(int i = 0, len = buckets.size(); i < len; i++)
		{
			buckets.valueAt(i).draw();
		}
	}

    public void addGameObject(GameObject gameObject)
    {
        gameObjects.add(gameObject);
    }

    public int getGameObjectsAmount()
    {
        return gameObjects.size();
    }

    public ArrayList<GameObject> getGameObjects()
	{
		return gameObjects;
	}

    public void destroyAllObjects()
    {
		for(GameObject go : gameObjects)
			go.destroyed = true;
    }

	public void drawTextureRegion(int textureId, float u1, float u2, float v1, float v2, float x, float y, float width, float height, float angle, int colorTopLeft, int colorTopRight, int colorBottonLeft, int colorBottomRight)
	{
		SceneLayerBucketList bucketList = buckets.get(textureId);
		if(bucketList == null)
		{
			if(CONSTANTS._DEBUG || CONSTANTS._DEBUG_SHADER) Log.d(CONSTANTS._TAG, "Layer[id:"+id+"]_BucketList_Added[tex:"+textureId+"]");
			bucketList = new SceneLayerBucketList(this, textureId);
			buckets.append(textureId, bucketList);
		}
		bucketList.drawTextureRegion(u1, u2, v1, v2, x, y, width, height, angle, colorTopLeft, colorTopRight, colorBottonLeft, colorBottomRight);
	}

	public int getMinFilter()
	{
		return minFilter;
	}

	public int getMagFilter()
	{
		return magFilter;
	}

	public int getId()
	{
		return id;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// PRIVATE
	//
	////////////////////////////////////////////////////////////////////////////////////////////////

	private void setShaderProgramLocationVars()
	{
		int program = shader.getProgram();
		loc_uniform_mvp			= GLES20.glGetUniformLocation(program, _U_MVP);
		loc_uniform_sampler_2d	= GLES20.glGetUniformLocation(program, _U_SAMPLER_2D);
		loc_attr_vertex_pos 	= GLES20.glGetAttribLocation (program, _A_VERTEX_POS);
		loc_attr_pos 			= GLES20.glGetAttribLocation (program, _A_POSITION);
		loc_attr_tex_coor 		= GLES20.glGetAttribLocation (program, _A_TEXCOORD);
		loc_attr_size 			= GLES20.glGetAttribLocation (program, _A_SIZE);
		loc_attr_rotation 		= GLES20.glGetAttribLocation (program, _A_ROTATION);
		loc_attr_color 			= GLES20.glGetAttribLocation (program, _A_COLOR);
		if(CONSTANTS._DEBUG_SHADER)
			Log.d(CONSTANTS._TAG,
					"loc_uniform_sampler_2d=" + loc_uniform_sampler_2d + " " +
					"loc_uniform_mvp=" + loc_uniform_mvp + " " +
					"loc_attr_vertex_pos=" + loc_attr_vertex_pos + " " +
					"loc_attr_pos=" + loc_attr_pos + " " +
					"loc_attr_tex_coor=" + loc_attr_tex_coor + " " +
					"loc_attr_size=" + loc_attr_size + " " +
					"loc_attr_rotation=" + loc_attr_rotation + " " +
					"loc_attr_color=" + loc_attr_color);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// STATIC
	//
	////////////////////////////////////////////////////////////////////////////////////////////////

	public static int getCurrentGlobalLayerId()
	{
		return currentGlobalLayerId;
	}
}
