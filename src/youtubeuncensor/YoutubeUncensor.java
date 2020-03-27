package youtubeuncensor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import youtubeuncensor.core.PreferencesHelper;

/**
 *
 * @author juanjo
 */
public class YoutubeUncensor extends Application {

    public static final String appversion = "v1.0 Beta 5 (2020/03/27)";

    @Override
    public void start(Stage stage) throws Exception {

        this.loadPreferences();

        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("YoutubeUncensor " + appversion);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("youtube_uncensor.png")));
        stage.setScene(scene);

        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        Main.properExit();
    }

    public void loadPreferences() {
        if (PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DOWNLOAD_DIR) == null) {
            PreferencesHelper.setPreference(PreferencesHelper._PREFNAME_DOWNLOAD_DIR, PreferencesHelper.PREF_DOWNLOAD_DIR);
        }
        if (PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DEFAULT_FILESIZE) == null) {
            PreferencesHelper.setPreference(PreferencesHelper._PREFNAME_DEFAULT_FILESIZE, String.valueOf(PreferencesHelper.PREF_MAX_FILESIZE));
        }
        if (PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DEFAULT_WAITTIME) == null) {
            PreferencesHelper.setPreference(PreferencesHelper._PREFNAME_DEFAULT_WAITTIME, String.valueOf(PreferencesHelper.PREF_WAIT_TIME));
        }
        if (PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DEFAULT_STOPONERROR) == null) {
            PreferencesHelper.setPreference(PreferencesHelper._PREFNAME_DEFAULT_STOPONERROR, String.valueOf(PreferencesHelper.PREF_STOP_ON_ERROR));
        }
        if (PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DEFAULT_LOGDEBUG) == null) {
            PreferencesHelper.setPreference(PreferencesHelper._PREFNAME_DEFAULT_LOGDEBUG, String.valueOf(PreferencesHelper.PREF_LOG_DEBUG));
        }

        PreferencesHelper.PREF_DOWNLOAD_DIR = PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DOWNLOAD_DIR);
        PreferencesHelper.PREF_MAX_FILESIZE = Float.parseFloat(PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DEFAULT_FILESIZE));
        PreferencesHelper.PREF_WAIT_TIME = Integer.parseInt(PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DEFAULT_WAITTIME));
        PreferencesHelper.PREF_STOP_ON_ERROR = Boolean.parseBoolean(PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DEFAULT_STOPONERROR));
        PreferencesHelper.PREF_LOG_DEBUG = Boolean.parseBoolean(PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DEFAULT_LOGDEBUG));
    }

}
