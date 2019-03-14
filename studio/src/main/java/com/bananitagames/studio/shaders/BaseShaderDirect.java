package com.bananitagames.studio.shaders;

/**
 * The default shader object which grants access to vertex shader and fragment shader code
 */
public final class BaseShaderDirect extends BaseShader
{
	@Override
	public String getVertexShader()
	{

		return  "uniform mat4 uMVP;" +
				"attribute vec2 aVertexPos;" +
				"attribute vec2 aPosition;" +
				"attribute vec2 aTexCoord;" +
				"attribute vec2 aSize;" +
				"attribute float aRotation;" +
				"attribute vec4 aColor;" +
				"varying vec4 vColor;" +
				"varying vec2 vTexCoord;" +
				"void main() {" +
				"  vec2 sVP = aVertexPos * aSize;" +
				"  float sVPcos = cos(radians(aRotation));" +
				"  float sVPsin = sin(radians(aRotation));" +
				"  vec2 rsVP = vec2(sVP.x * sVPcos - sVP.y * sVPsin, sVP.x * sVPsin + sVP.y * sVPcos);" +
				"  vTexCoord = aTexCoord;" +
				"  vColor = clamp(aColor,0.0,1.0);" +
				"  gl_Position = uMVP * vec4((rsVP + aPosition),0.0,1.0);" +
				"}";
	}

	@Override
	public String getFragmentShader()
	{

		return "precision mediump float;" +
				"uniform sampler2D uTexture;" +
				"varying vec4 vColor;" +
				"varying vec2 vTexCoord;" +
				"void main() {" +
				"  vec4 finalColor = clamp(vColor * texture2D(uTexture, vTexCoord), 0.0, 1.0);" +
				"  if(finalColor.a <= 0.0) discard;" +
				"  gl_FragColor = finalColor;" +
				"}";
	}
}
