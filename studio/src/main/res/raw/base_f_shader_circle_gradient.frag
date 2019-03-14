precision mediump float;

uniform sampler2D uTexture;
varying vec4 vColor;
varying vec2 vTexCoord;
varying vec2 vVertexCoord;

const vec2 center = vec2(0.0,0.0);

void main()
{
    float alpha =  1.0 - 2.0 * clamp(distance(center,vVertexCoord),0.0, 0.5);
    gl_FragColor = clamp(vec4(vColor.rgb, vColor.a * alpha), 0.0, 1.0);
}