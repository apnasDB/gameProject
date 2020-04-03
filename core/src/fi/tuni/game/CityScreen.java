package fi.tuni.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;

public class CityScreen implements Screen {


    private final float WINDOW_WIDTH = 12.8f;
    private final float WINDOW_HEIGHT = 6.4f;
    private SpriteBatch batch;
    private Main objectMain;
    private OrthographicCamera camera;
    private OrthographicCamera fontCamera;
    private TiledMap cityTiledMap;
    private TiledMapRenderer cityTiledMapRenderer;

    private Texture cashBackground;

    private Toilet toilet;
    private Toilets toilets;
    private ArrayList<Toilets> allToilets;

    public CityScreen(Main x) {
        batch = x.getBatch();
        objectMain = x;
        camera = new OrthographicCamera();
        fontCamera = new OrthographicCamera();
        fontCamera.setToOrtho(false,
                WINDOW_WIDTH*100,
                WINDOW_HEIGHT*100);
        //Camera for tiledmap
        camera.setToOrtho(false,
                WINDOW_WIDTH,
                WINDOW_HEIGHT);
        cityTiledMap = new TmxMapLoader().load("kaupunkikesken.tmx");
        cityTiledMapRenderer = new OrthogonalTiledMapRenderer(cityTiledMap, 1 / 100f);

        cashBackground = new Texture("coin.png");

        allToilets = new ArrayList<>();
        generateToilets();
    }

    private void generateToilets() {
        generateToilet(2.86f,1f);
        generateToilet(2.22f,3.88f);
        generateToilet(9.58f,3.88f);
        generateToilet(10.55f,0.36f);
        generateToilet(5.75f,2.6f);
        generateToilet(7.98f,2.28f);
        generateToilet(10.87f,4.53f);
    }

    private void generateToilet(float posX, float posY) {
        toilet = new Toilet(posX, posY);
        toilets = new Toilets(toilet);
        allToilets.add(toilets);
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        cityTiledMapRenderer.setView(camera);
        cityTiledMapRenderer.render();

        //cash
        batch.setProjectionMatrix(fontCamera.combined);
        batch.begin();
        objectMain.getFont().draw(batch, objectMain.getBalanceCash().getValueToString(), 825, 615);
        batch.draw(cashBackground, 740, 555);
        batch.end();

        //stage
        objectMain.getUIStage().act(Gdx.graphics.getDeltaTime());
        objectMain.getUIStage().draw();

        //add actors
        for(Toilets huus: allToilets) {
            Toilet tmpHuussi = huus.getToilet();
            Menu tmpMenu = huus.getMenu();
            BackButton tmpBackButton = huus.getBackButton();
            objectMain.getUIStage().addActor(huus.getToilet());
            objectMain.getUIStage().addActor(objectMain.getSceneSwitch());

            //toilet menu
            if (tmpHuussi.getHappened()) {
                objectMain.getUIStage().addActor(tmpMenu);
                objectMain.getUIStage().addActor(tmpBackButton);

                if (tmpBackButton.getHappened()) {
                    closeMenu();
                }
            }
        }

        //sceneswitch function
        Gdx.input.setInputProcessor(objectMain.getUIStage());
        if (objectMain.getSceneSwitch().getHappened()) {
            closeMenu();
            objectMain.switchScene();
            objectMain.getSceneSwitch().setHappened(false);
        }
    }

    private void closeMenu() {
        for(Toilets huus: allToilets) {
            Toilet tmpHuussi = huus.getToilet();
            Menu tmpMenu = huus.getMenu();
            BackButton tmpBackButton = huus.getBackButton();

            tmpHuussi.setHappened(false);
            tmpMenu.setHappened(false);
            tmpBackButton.setHappened(false);
        }
        objectMain.getUIStage().clear();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
