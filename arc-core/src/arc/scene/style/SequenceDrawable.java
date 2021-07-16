package arc.scene.style;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.*;
import arc.util.Tmp;

/**
 * Drawable for a {@link TextureRegion}.
 * @author Nathan Sweet
 */
public class SequenceDrawable<D extends Drawable> extends BaseDrawable implements TransformDrawable{
//    protected TextureRegion region;
//    protected Color tint = new Color(1f, 1f, 1f);
//    protected float scale = 1f;

    private D[] keyFrames;

    /** Creates an uninitialized TextureRegionDrawable. The texture region must be set before use. */
    public SequenceDrawable(){
    }

    public SequenceDrawable(D... regions) {
        this.keyFrames = regions;
    }

    @Override
    public void draw(float x, float y, float width, float height){
        for (D drawable : keyFrames) {
            drawable.draw( x, y, width, height);
        }
    }

    @Override
    public void draw(float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation){
        for (D drawable : keyFrames) {
            drawable.draw( x, y, originX, originY, width, height, scaleX, scaleY, rotation);
        }
    }


    @Override
    public float imageSize(){
        if (keyFrames == null)  return 0;

        for (D d : keyFrames) {
            return d.imageSize();
        }
        return 0;
    }

    @Override
    public float getLeftWidth() {
        return keyFrames[0].getLeftWidth();
    }

    @Override
    public void setLeftWidth(float leftWidth) {
        for (D drawable : keyFrames) {
            drawable.setLeftWidth(leftWidth);
        }
    }

    @Override
    public float getRightWidth() {
        return keyFrames[0].getRightWidth();
    }

    @Override
    public void setRightWidth(float rightWidth) {
        for (D drawable : keyFrames) {
            drawable.setRightWidth(rightWidth);
        }
    }

    @Override
    public float getTopHeight() {
        return keyFrames[0].getTopHeight();
    }

    @Override
    public void setTopHeight(float topHeight) {
        for (D drawable : keyFrames) {
            drawable.setTopHeight(topHeight);
        }
    }

    @Override
    public float getBottomHeight() {
        return keyFrames[0].getBottomHeight();
    }

    @Override
    public void setBottomHeight(float bottomHeight) {
        for (D drawable : keyFrames) {
            drawable.setBottomHeight(bottomHeight);
        }
    }

    @Override
    public float getMinWidth() {
        return keyFrames[0].getMinWidth();
    }

    @Override
    public void setMinWidth(float minWidth) {
        for (D drawable : keyFrames) {
            drawable.setMinWidth(minWidth);
        }
    }

    @Override
    public float getMinHeight() {
        return keyFrames[0].getMinHeight();
    }

    @Override
    public void setMinHeight(float minHeight) {
        for (D drawable : keyFrames) {
            drawable.setMinHeight(minHeight);
        }
    }
}
