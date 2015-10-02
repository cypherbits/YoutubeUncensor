package youtubeuncensor;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import youtubeuncensor.core.PreferencesHelper;

/**
 * FXML Controller class
 *
 * @author juanjo
 */
public class GlobalConfigController implements Initializable {

    @FXML
    Parent root;
    @FXML
    private TextField global_txtDownloadPath;
    @FXML
    private Button global_btnChange;
    @FXML
    private Button global_btnApply;
    @FXML
    private Button default_btnApply;
    @FXML
    private TextField default_txtMaxSize;
    @FXML
    private TextField default_txtTime;
     @FXML
    private CheckBox default_checkStopError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        global_txtDownloadPath.setText(new File(Main.NOW_DOWNLOAD_DIR).getAbsolutePath());

    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Object source = event.getSource();

        if (source == this.global_btnApply) {
            PreferencesHelper.setPreference(PreferencesHelper.DOWNLOAD_DIR_NAME, global_txtDownloadPath.getText());

        } else if (source == this.global_btnChange) {

            DirectoryChooser directoryChooser = new DirectoryChooser();

            directoryChooser.setTitle("Choose an EMPTY folder");

            File selectedDirectory = directoryChooser.showDialog((Stage) root.getScene().getWindow());

            if (selectedDirectory != null && selectedDirectory.exists()) {
                //TODO: loop while directory is not empty.

                global_txtDownloadPath.setText(selectedDirectory.getAbsolutePath());
            }
        }
    }
}
