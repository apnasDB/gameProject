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
        generateToilet(2.86f,1f, "Toilet_1");
        generateToilet(2.22f,3.88f, "Toilet_2");
        generateToilet(9.58f,3.88f, "Toilet_3");
        generateToilet(10.55f,0.36f, "Toilet_4");
        generateToilet(5.75f,2.6f, "Toilet_5");
        generateToilet(7.98f,2.28f, "Toilet_6");
        generateToilet(10.87f,4.53f, "Toilet_7");
    }

    private void generateToilet(float posX, float posY, String key) {
        toilet = new Toilet(posX, posY);
        toilets = new Toilets(toilet, key);
        allToilets.add(toilets);
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        MemoryWriter.writeCurrentTimestamp();
        cityTiledMapRenderer.setView(camera);
        cityTiledMapRenderer.render();

        //fontCamera
        batch.setProjectionMatrix(fontCamera.combined);
        //stage
        objectMain.getUIStage().act(Gdx.graphics.getDeltaTime());
        objectMain.getUIStage().draw();

        batch.begin();
        drawToilets();
        //balances
        objectMain.getFontBig().draw(batch, objectMain.getBalanceCash().getValueToString(), 825, 615);
        batch.draw(cashBackground, 740, 555);
        batch.end();

        //sceneswitch function
        objectMain.getUIStage().addActor(objectMain.getSceneSwitch());
        Gdx.input.setInputProcessor(objectMain.getUIStage());
        if (objectMain.getSceneSwitch().getHappened()) {
            closeMenu();
            objectMain.switchScene();
            objectMain.getSceneSwitch().setHappened(false);
        }
        //settings function
        objectMain.getUIStage().addActor(objectMain.getSettings());
        Gdx.input.setInputProcessor(objectMain.getUIStage());
        if (objectMain.getSettings().getHappened()) {

            closeMenu();
            objectMain.getSettings().setHappened(false);
        }
    }

    private void drawToilets() {
        //add actors
        for(Toilets tmpToilets: allToilets) {
            Toilet tmpToilet = tmpToilets.getToilet();
            Menu tmpMenu = tmpToilets.getMenu();
            BackButton tmpBackButton = tmpToilets.getBackButton();
            ButtonBackground tmpContract = tmpToilets.getContractButton();
            ButtonBackground tmpContract2 = tmpToilets.getContractButton2();
            ButtonBackground tmpUpgrade = tmpToilets.getUpgradeButton();
            objectMain.getUIStage().addActor(tmpToilets.getToilet());

            //toilet menu
            if (tmpToilet.getHappened()) {
                objectMain.getUIStage().addActor(tmpMenu);
                objectMain.getUIStage().addActor(tmpBackButton);
                if (tmpToilets.getTier() > 0 && !tmpToilets.getState()) {
                    objectMain.getUIStage().addActor(tmpContract);
                    objectMain.getFontSmall().draw(batch, "Virtsa", 740, 440);
                    objectMain.getFontSmall().draw(batch, "Kerää kuses horo",260, 460);
                    objectMain.getFontSmall().draw(batch, "Prosessi kestää 15s",260, 430);
                    objectMain.getUIStage().addActor(tmpContract2);
                    objectMain.getFontSmall().draw(batch, "Uloste", 740, 340);
                    objectMain.getFontSmall().draw(batch, "Kerää paskaa horo",260, 360);
                    objectMain.getFontSmall().draw(batch, "Prosessi kestää 15s",260, 330);
                }

                if (tmpToilets.getTier() < 4) {
                    objectMain.getUIStage().addActor(tmpUpgrade);
                    objectMain.getFontSmall().draw(batch, tmpToilets.getPrice(), 810, 240);
                    objectMain.getFontSmall().draw(batch, "Päivitys kuivakäymälään",260, 260);
                    objectMain.getFontSmall().draw(batch, "Lisää tuotanto nopeutta",260, 230);
                }

                if (tmpContract.getHappened()) {
                    tmpToilets.startProduction(0);
                    closeMenu();
                } else if (tmpContract2.getHappened()) {
                    tmpToilets.startProduction(1);
                    closeMenu();
                }


                if (tmpUpgrade.getHappened()) {
                    if (objectMain.getBalanceCash().getValue() > Integer.parseInt(tmpToilets.getPrice())) {
                        objectMain.getBalanceCash().removeValue(Integer.parseInt(tmpToilets.getPrice()));
                        tmpToilets.upgrade();
                        closeMenu();
                    }
                }

                if (tmpBackButton.getHappened()) {
                    closeMenu();
                }
            }

            //production check
            if (tmpToilets.getState()) {
                tmpToilets.checkProduction(objectMain.getBalancePee(), objectMain.getBalancePoo());
            } else {
                //tmpHuussi.bounce();
            }
        }
    }

    private void closeMenu() {
        for(Toilets tmpToilets: allToilets) {
            Toilet tmpToilet = tmpToilets.getToilet();
            Menu tmpMenu = tmpToilets.getMenu();
            BackButton tmpBackButton = tmpToilets.getBackButton();
            ButtonBackground tmpContract = tmpToilets.getContractButton();
            ButtonBackground tmpContract2 = tmpToilets.getContractButton2();
            ButtonBackground tmpUpgrade = tmpToilets.getUpgradeButton();

            tmpToilet.setHappened(false);
            tmpMenu.setHappened(false);
            tmpBackButton.setHappened(false);
            tmpContract.setHappened(false);
            tmpContract2.setHappened(false);
            tmpUpgrade.setHappened(false);
        }
        objectMain.getUIStage().clear();
        drawToilets();
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
