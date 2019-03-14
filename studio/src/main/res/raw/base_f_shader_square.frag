precision mediump float;

uniform sampler2D uTexture;
varying vec4 vColor;
varying vec2 vTexCoord;
varying vec2 vVertexCoord;

const vec2 center = vec2(0.5,0.5);

void main()
{
    gl_FragColor = clamp(vColor, 0.0, 1.0);
}