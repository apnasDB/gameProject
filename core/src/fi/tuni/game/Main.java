package fi.tuni.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {

    private final float WINDOW_WIDTH = 12.8f;
    private final float WINDOW_HEIGHT = 6.4f;

    SpriteBatch batch;
    private boolean inCity = true;

    public SpriteBatch getBatch() {
        return batch;
    }

    private FieldScreen field;
    private CityScreen city;

    private FreeTypeFontGenerator generator;
    private BitmapFont font;

    private Stage uiStage;
    private float width = WINDOW_WIDTH;
    private float height = WINDOW_HEIGHT;
    private Clickable sceneSwitch;


    private Balance cash = new Balance("Cash", 123124561);
    private Balance pee = new Balance("Pee", 1234123561);
    private Balance poo = new Balance("Poo", 1234512361);

    public Balance getBalance() {
        return cash;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        city = new CityScreen(this);
        field = new FieldScreen(this);
        setScreen(city);
        createFont(60);

        uiStage = new Stage(new FitViewport(width, height));
        sceneSwitch = new Clickable();
        uiStage.addActor(sceneSwitch);

        initValues();

        // work in progress
        //Localization test = new Localization();

    }

    private void initValues() {
        MemoryWriter memCash = new MemoryWriter(cash);
        MemoryWriter memPee = new MemoryWriter(pee);
        MemoryWriter memPoo = new MemoryWriter(poo);
    }

    private void createFont(int size) {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font2.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
    }

    public BitmapFont getFont() {
        return font;
    }

    public Stage getUIStage() {
        return uiStage;
    }


    public Clickable getSceneSwitch() {
        return sceneSwitch;
    }

    @Override
    public void render () {
        super.render();
    }

    public void switchScene() {
        if (Gdx.input.justTouched() && inCity == false) {
            inCity = true;
            setScreen(city);
            resetStage();
        } else if (Gdx.input.justTouched() && inCity == true) {
            inCity = false;
            setScreen(field);
            resetStage();
        }
    }

    public void resetStage() {
        uiStage.clear();
        uiStage.addActor(sceneSwitch);
    }




    @Override
    public void dispose () {
        batch.dispose();
        field.dispose();
        city.dispose();
        font.dispose();
        generator.dispose();
    }
}
