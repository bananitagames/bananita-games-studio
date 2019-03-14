package com.bananitagames.studio.interfaces;

import com.bananitagames.studio.SceneShader;

/**
 * Created by luis on 10/11/2017.
 */

public interface LAYER
{
	public LAYER setBlendFunc(int src, int dst);
	public LAYER setBlendFuncMultiply();
	public LAYER setBlendFuncAdditive();
	public LAYER setBlendFuncNormal();
	public LAYER setFilters(int minFilter, int magFilter);
	public LAYER setShader(SceneShader shader);
}
