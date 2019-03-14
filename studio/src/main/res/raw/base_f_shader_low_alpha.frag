precision mediump float;

uniform sampler2D uTexture;
varying vec4 vColor;
varying vec2 vTexCoord;
varying vec2 vVertexCoord;

void main()
{
    vec4 finalColor = clamp(vColor * texture2D(uTexture, vTexCoord), 0.0, 1.0);
    if(finalColor.a < 0.5)
        discard;
    gl_FragColor = finalColor;
}