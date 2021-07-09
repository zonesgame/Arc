package arc.graphics.g2d;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.gl.Shader;
import arc.math.Affine2;
import arc.math.Mat;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.Rect;
import arc.util.Tmp;

import static arc.Core.camera;

public class Draw{
    private static Color[] carr = new Color[3];
    public static float scl = 1f;

    public static void batch(SpriteBatch nextBatch, Runnable run){
        SpriteBatch prev = Core.batch;
        prev.flush();

        Core.batch = nextBatch;

        run.run();

        nextBatch.flush();
        Core.batch = prev;
    }

    public static Shader getShader(){
        return Core.batch.getShader();
    }

    public static void shader(Shader shader){
        shader(shader, true);
    }

    public static void shader(Shader shader, boolean apply){
        Core.batch.setShader(shader, apply);
    }

    public static void shader(){
        Core.batch.setShader(null);
    }

    public static Color getColor(){
        return Core.batch.getColor();
    }

    public static Color getMixColor(){
        return Core.batch.getMixColor();
    }

    public static void mixcol(Color color, float a){
        Core.batch.setMixColor(color.r, color.g, color.b, Mathf.clamp(a));
    }

    public static void mixcol(){
        Core.batch.setPackedMixColor(Color.clearFloatBits);
    }

    public static void tint(Color a, Color b, float s){
        Tmp.c1.set(a).lerp(b, s);
        Core.batch.setColor(Tmp.c1.r, Tmp.c1.g, Tmp.c1.b, Core.batch.getColor().a);
    }

    public static void tint(Color color){
        Core.batch.setColor(color.r, color.g, color.b, Core.batch.getColor().a);
    }

    public static void colorMul(Color color, float mul){
        color(color.r * mul, color.g * mul, color.b * mul, 1f);
    }

    public static void color(Color color){
        Core.batch.setColor(color);
    }

    public static void color(Color color, float alpha){
        Core.batch.setColor(color.r, color.g, color.b, alpha);
    }

    public static void color(float color){
        Core.batch.setPackedColor(color);
    }

    public static void color(Color a, Color b, Color c, float progress){
        carr[0] = a;
        carr[1] = b;
        carr[2] = c;
        color(Tmp.c1.lerp(carr, progress));
    }

    /** Automatically mixes colors. */
    public static void color(Color a, Color b, float s){
        Core.batch.setColor(Tmp.c1.set(a).lerp(b, s));
    }

    public static void color(){
        Core.batch.setPackedColor(Color.whiteFloatBits);
    }

    public static void color(float r, float g, float b){
        Core.batch.setColor(r, g, b, 1f);
    }

    public static void color(float r, float g, float b, float a){
        Core.batch.setColor(r, g, b, a);
    }

    /** Lightness color. */
    public static void colorl(float l){
        color(l, l, l);
    }

    /** Lightness color, alpha. */
    public static void colorl(float l, float a){
        color(l, l, l, a);
    }

    public static void blend(Blending blending){
        Core.batch.setBlending(blending);
    }

    public static void blend(){
        blend(Blending.normal);
    }

    public static void reset(){
        color();
        mixcol();
        Lines.stroke(1f);
    }

    public static void alpha(float alpha){
        Core.batch.setColor(Core.batch.getColor().r, Core.batch.getColor().g, Core.batch.getColor().b, alpha);
    }

    public static void fbo(Texture texture, int worldWidth, int worldHeight, int tilesize){
        float ww = worldWidth * tilesize, wh = worldHeight * tilesize;
        float x = camera.position.x + tilesize / 2f, y = camera.position.y + tilesize / 2f;
        float u = (x - camera.width / 2f) / ww,
        v = (y - camera.height / 2f) / wh,
        u2 = (x + camera.width / 2f) / ww,
        v2 = (y + camera.height / 2f) / wh;

        Tmp.tr1.set(texture);
        Tmp.tr1.set(u, v2, u2, v);

        Draw.rect(Tmp.tr1, camera.position.x, camera.position.y, camera.width, camera.height);
    }

    public static void rect(String region, float x, float y, float w, float h){
        rect(Core.atlas.find(region), x, y, w, h);
    }

    public static void rect(TextureRegion region, float x, float y, float w, float h){
        Core.batch.draw(region, x - w /2f, y - h /2f, w, h);
    }

    public static void rect(TextureRegion region, float x, float y){
        rect(region, x, y, region.getWidth() * scl, region.getHeight() * scl);
    }

    public static void rect(String region, float x, float y){
        rect(Core.atlas.find(region), x, y);
    }

    public static void rect(TextureRegion region, float x, float y, float w, float h, float originX, float originY, float rotation){
        Core.batch.draw(region, x - w /2f, y - h /2f, originX, originY, w, h, rotation);
    }

    public static void rect(String region, float x, float y, float w, float h, float originX, float originY, float rotation){
        Core.batch.draw(Core.atlas.find(region), x - w /2f, y - h /2f, originX, originY, w, h, rotation);
    }

    public static void rect(TextureRegion region, float x, float y, float w, float h, float rotation){
        rect(region, x, y, w, h, w/2f, h/2f, rotation);
    }

    public static void rect(String region, float x, float y, float w, float h, float rotation){
        rect(Core.atlas.find(region), x, y, w, h, w/2f, h/2f, rotation);
    }

    public static void rect(TextureRegion region, Position pos, float w, float h){
        rect(region, pos.getX(), pos.getY(), w, h);
    }

    public static void rect(TextureRegion region, Position pos, float w, float h, float rotation){
        rect(region, pos.getX(), pos.getY(), w, h, rotation);
    }

    public static void rect(TextureRegion region, Position pos, float rotation){
        rect(region, pos.getX(), pos.getY(), rotation);
    }

    public static void rect(TextureRegion region, float x, float y, float rotation){
        rect(region, x, y, region.getWidth() * scl, region.getHeight() * scl, rotation);
    }

    public static void rect(String region, float x, float y, float rotation){
        rect(Core.atlas.find(region), x, y, rotation);
    }

    public static void vert(Texture texture, float[] vertices, int offset, int length){
        Core.batch.draw(texture, vertices, offset, length);
    }

    public static void vert(float[] vertices){
        vert(Core.atlas.texture(), vertices, 0, vertices.length);
    }

    public static void flush(){
        Core.batch.flush();
    }

    public static void proj(Mat proj){
        Core.batch.setProjection(proj);
    }

    public static Mat proj(){
        return Core.batch.getProjection();
    }

    public static void trans(Mat trans){
        Core.batch.setTransform(trans);
    }

    public static Mat trans(){
        return Core.batch.getTransform();
    }

    public static TextureRegion wrap(Texture texture){
        Tmp.tr2.set(texture);
        return Tmp.tr2;
    }

    // zones add begon

    // extends arc begon
    public static void rectGdx(TextureRegion region, float x, float y){
        rectGdx(region, x, y, region.getWidth() * scl, region.getHeight() * scl);
    }

    public static void rectGdx(TextureRegion region, float x, float y, float w, float h){
        Core.batch.draw(region, x, y, w, h);
    }

    /** 仅限权倾天下纹理使用*/
    public static void rectGdx(TextureRegion region, float dx, float dy, Rect rect){
        Core.batch.draw(region, dx - rect.x * scl, dy - rect.y * scl, rect.width * scl, rect.height * scl);
    }
    // extends arc end

    /** 测试数据权倾天下纹理缩放值*/
    private static final float debugScale = 1.5f;

    public static void rectScale(TextureRegion region, float x, float y){
        rect(region, x, y, region.getWidth() * scl * scaleCS, region.getHeight() * scl * scaleCS);
    }

    public static void rectScaleH(TextureRegion region, float x, float y){
        rect(region, x, y, region.getWidth() * scl * scaleCS, region.getHeight() * scl * scaleCS * scaleh);
    }

    public static void rectScale(TextureRegion region, float x, float y, float scale){
        rect(region, x, y, region.getWidth() * scl * scale, region.getHeight() * scl * scale);
    }

    public static void rectGdx(TextureRegion region, float x, float y, float w, float h, float originX, float originY, float rotation){
        Core.batch.draw(region, x , y , originX, originY, w, h, rotation);
    }

    public static void rectGdxOffsetScaleY(TextureRegion region, float x, float y, float offsetx, float offsety){
        float scale = scl * debugScale;
        rectGdx(region, x + offsetx * scale, y + offsety * scale, region.getWidth() * scale, region.getHeight() * scale);
    }

    public static void rectGdxOffset(TextureRegion region, float x, float y, float offsetx, float offsety){
        float scale = scl;
        rectGdx(region, x + offsetx * scale, y + offsety * scale, region.getWidth() * scale, region.getHeight() * scale);
    }

    // zones temp code begon
    public static void rectGdxScale(TextureRegion region, float x, float y, float w, float h){
        Core.batch.draw(region, (x - w /2f) * scl, (y - h /2f) * scl, w * scl, h * scl);
    }

    public static void rectGdxScale(TextureRegion region, float x, float y){
        Core.batch.draw(region, (x - region.getWidth() /2f) * scl, (y - region.getHeight() /2f) * scl, region.getWidth() * scl, region.getHeight() * scl);
    }
    // zones temp code end

    // shadow begon
    /** DiabloII公用function*/
    public static void shadow(TextureRegion region, float width, float height, Affine2 transform){  // Affine2
        Core.batch.draw(region, width, height, transform);
    }

    private static final Affine2 SHADOW_TRANSFORM = new Affine2();
    public static final Color SHADOW_TINT = new Color(0, 0, 0, 0.65f);
    /**
     *  draw shadow
     * */
    public static void shadowSG(TextureRegion region, float x, float y, float width, float height){  // Affine2
        float addx = -width;
        float addy = -(height / 2);

        SHADOW_TRANSFORM.idt();
        SHADOW_TRANSFORM.preTranslate(addx, addy);
        SHADOW_TRANSFORM.preShear(-1.0f, 0);
        SHADOW_TRANSFORM.preTranslate(x, y);
        SHADOW_TRANSFORM.scale(1, 0.5f);

        Core.batch.draw(region, width, height, SHADOW_TRANSFORM);
    }

    public static void shadowSG(TextureRegion region, float x, float y, Rect rect){  // Affine2
//        Core.batch.draw(region, dx - rect.x * scl, dy - rect.y * scl, rect.width * scl, rect.height * scl);

        float dx = x - rect.x * scl;
        float dy = y - rect.y * scl;
        float dw = rect.width * scl;
        float dh = rect.height * scl;

//        float addx = -width;
//        float addy = -(height / 2);

        SHADOW_TRANSFORM.idt();
//        SHADOW_TRANSFORM.preTranslate(addx, addy);
        SHADOW_TRANSFORM.preShear(-1.0f, 0);
        SHADOW_TRANSFORM.preTranslate(dx, dy);
        SHADOW_TRANSFORM.scale(1, 0.5f);

        Core.batch.draw(region, dw, dh, SHADOW_TRANSFORM);
    }
    // shadow end

    private static float scaleCS = 78 / 120f;
    /** 凯撒缩放值*/
    public static float getScl() {
        return scl * scaleCS;
    }
    public static float getSclH() {
        return scl * scaleCS * scaleh;
    }
    /** 测试数据*/
//    final int _W2 = 78, _H2 = 60;
//    //                final int _W = 58, _H = 30;
//    final int _W = 120, _H = 60;

    private static final float scaleh = (120 / (78 / (float)60)) / (float)60;
    // zones add end
}
