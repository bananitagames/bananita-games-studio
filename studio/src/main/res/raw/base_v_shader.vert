uniform mat4 uMVP;
attribute vec2 aVertexPos;
attribute vec2 aPosition;
attribute vec2 aTexCoord;
attribute vec2 aSize;
attribute float aRotation;
attribute vec4 aColor;
varying vec4 vColor;
varying vec2 vTexCoord;
varying vec2 vVertexCoord;
void main()
{
    vec2 sVP = aVertexPos * aSize;
    float sVPcos = cos(radians(aRotation));
    float sVPsin = sin(radians(aRotation));
    vec2 rsVP = vec2(sVP.x * sVPcos - sVP.y * sVPsin, sVP.x * sVPsin + sVP.y * sVPcos);
    vTexCoord = aTexCoord;
    vColor = clamp(aColor / 255.0 ,0.0,1.0);
    vVertexCoord = aVertexPos;
    gl_Position = uMVP * vec4((rsVP + aPosition),0.0,1.0);
}