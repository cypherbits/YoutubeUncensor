package youtubeuncensor.core;

import java.util.prefs.Preferences;
import youtubeuncensor.YoutubeUncensor;

/**
 *
 * @author juanjo
 */
public class PreferencesHelper {

    public static String getPreference(String name) {
        Preferences prefs = Preferences.userNodeForPackage(YoutubeUncensor.class);
        return prefs.get(name, null);
    }

    public static void setPreference(String name, String value) {
        Preferences prefs = Preferences.userNodeForPackage(YoutubeUncensor.class);
        prefs.put(name, value);
    }

}
