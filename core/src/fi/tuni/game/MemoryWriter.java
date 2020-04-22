package fi.tuni.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.TimeUtils;


public class MemoryWriter {

    static public void writeBalance(String key, int value) {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.putInteger(key, value);
        prefs.flush();
    }

    static public void writeToilet(String key, int tier) {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.putInteger(key, tier);
        prefs.flush();
    }

    static public void writeField(String key, int cont) {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.putInteger(key, cont);
        prefs.flush();
    }

    static public void writeToiletCont(String key, int cont) {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.putInteger(key, cont);
        prefs.flush();
    }

    static public void writeTimer(String key, long saved) {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.putLong(key, saved);
        prefs.flush();
    }


    static public void writeCurrentTimestamp() {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.putLong("CurrentTimestamp", TimeUtils.millis());
        prefs.flush();
    }

    static public void writeVolume(String key, boolean ON) {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.putBoolean(key, ON);
        prefs.flush();
    }

    static public void writeLang(boolean finnish) {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.putBoolean("Language", finnish);
        prefs.flush();
    }
}
