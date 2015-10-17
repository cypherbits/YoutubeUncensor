package youtubeuncensor.core;

import java.util.prefs.Preferences;
import youtubeuncensor.YoutubeUncensor;

/**
 *
 * @author juanjo
 */
public class PreferencesHelper {

    //Names for saving/restoring the preferences
    public static final String _PREFNAME_DOWNLOAD_DIR = "download_dir";
    public static final String _PREFNAME_DEFAULT_FILESIZE = "default_filesize";
    public static final String _PREFNAME_DEFAULT_WAITTIME = "default_waitime";
    public static final String _PREFNAME_DEFAULT_STOPONERROR = "default_stoponerror";

    //Runtime preferences
    public static String PREF_DOWNLOAD_DIR = "downloads";
    
    //Default preferences for new TaskItems
    public static int PREF_WAIT_TIME = 20000;
    public static float PREF_MAX_FILESIZE = 30.1f;
    public static boolean PREF_STOP_ON_ERROR = true;

    public static String getPreference(String name) {
        Preferences prefs = Preferences.userNodeForPackage(YoutubeUncensor.class);
        return prefs.get(name, null);
    }

    public static void setPreference(String name, String value) {
        Preferences prefs = Preferences.userNodeForPackage(YoutubeUncensor.class);
        prefs.put(name, value);
    }

}
