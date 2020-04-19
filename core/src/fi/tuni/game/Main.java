package fi.tuni.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {

    private final float WINDOW_WIDTH = 12.8f;
    private final float WINDOW_HEIGHT = 6.4f;

    SpriteBatch batch;
    private boolean inCity = false;

    public SpriteBatch getBatch() {
        return batch;
    }

    private StartScreen startScreen;
    private FieldScreen fieldScreen;
    private CityScreen cityScreen;

    private FreeTypeFontGenerator generator;
    private BitmapFont fontBig;
    private BitmapFont fontSmall;

    private Stage uiStage;
    private float width = WINDOW_WIDTH;
    private float height = WINDOW_HEIGHT;
    private Clickable sceneSwitch;
    private Clickable settings;
    private Texture scen;
    private Texture settin;
    private Balance cash;
    private Balance pee;
    private Balance poo;

    @Override
    public void create () {
        batch = new SpriteBatch();
        cityScreen = new CityScreen(this);
        fieldScreen = new FieldScreen(this);
        startScreen = new StartScreen(this);
        setScreen(startScreen);
        fontBig = createFont(60);
        fontSmall = createFont(35);
        uiStage = new Stage(new FitViewport(width, height));
        scen = new Texture(Gdx.files.internal("sceneSwitch.png"));
        settin = new Texture(Gdx.files.internal("settingsButton.png"));
        sceneSwitch = new Clickable(scen, 11.9f,5.5f);
        settings = new Clickable(settin, 0.2f,5.5f);

        fetchValues();
        MemoryWriter.writeCurrentTimestamp();
    }

    private void fetchValues() {
        cash = new Balance("Cash");
        pee = new Balance("Pee");
        poo = new Balance("Poo");
    }

    public Balance getBalanceCash() {
        return cash;
    }
    public Balance getBalancePee() {
        return pee;
    }
    public Balance getBalancePoo() {
        return poo;
    }

    private BitmapFont createFont(int size) {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font2.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        return generator.generateFont(parameter);
    }

    public BitmapFont getFontBig() {
        return fontBig;
    }
    public BitmapFont getFontSmall() {
        return fontSmall;
    }
    public Stage getUIStage() {
        return uiStage;
    }
    public Clickable getSceneSwitch() {
        return sceneSwitch;
    }
    public Clickable getSettings() {
        return settings;
    }

    @Override
    public void render () {
        super.render();
    }

    public void switchScene() {
        if (Gdx.input.justTouched() && inCity == false) {
            inCity = true;
            setScreen(cityScreen);
            uiStage.clear();
        } else if (Gdx.input.justTouched() && inCity == true) {
            inCity = false;
            setScreen(fieldScreen);
            uiStage.clear();
        }
    }

    @Override
    public void dispose () {
        batch.dispose();
        fieldScreen.dispose();
        cityScreen.dispose();
        fontBig.dispose();
        fontSmall.dispose();
        generator.dispose();
        uiStage.dispose();
    }
}
