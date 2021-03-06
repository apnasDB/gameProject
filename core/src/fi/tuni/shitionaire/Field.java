package fi.tuni.shitionaire;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Field extends Clickable  {
    private Texture texture;
    private boolean happened = false;

    public Field(float x, float y) {
        setWidth(4.266f);
        setHeight(3.2f);
        float posX = x;
        float posY = y;
        setBounds(posX, posY, getWidth(), getHeight());
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                happened = true;
                return true;
            }
        });
    }

    public void setFieldTexture(Texture t) {
        texture = t;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

}
