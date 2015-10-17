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
import javafx.scene.input.KeyEvent;
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

        global_txtDownloadPath.setText(new File(PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DOWNLOAD_DIR)).getAbsolutePath());

        default_checkStopError.setSelected(Boolean.parseBoolean(PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DEFAULT_STOPONERROR)));
        default_txtMaxSize.setText(PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DEFAULT_FILESIZE));
        default_txtTime.setText(PreferencesHelper.getPreference(PreferencesHelper._PREFNAME_DEFAULT_WAITTIME));

    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Object source = event.getSource();

        if (source == this.global_btnApply) {
            PreferencesHelper.setPreference(PreferencesHelper._PREFNAME_DOWNLOAD_DIR, global_txtDownloadPath.getText());

        } else if (source == this.global_btnChange) {

            DirectoryChooser directoryChooser = new DirectoryChooser();

            directoryChooser.setTitle("Choose an EMPTY folder");

            File selectedDirectory = directoryChooser.showDialog((Stage) root.getScene().getWindow());

            if (selectedDirectory != null && selectedDirectory.exists()) {
                //TODO: loop while directory is not empty.

                global_txtDownloadPath.setText(selectedDirectory.getAbsolutePath());
            }
        } else if (source == this.default_btnApply) {
            String txtMaxSize = this.default_txtMaxSize.getText();
            String txtWaitTime = this.default_txtTime.getText();
            String stopOnError = String.valueOf(this.default_checkStopError.isSelected());

            PreferencesHelper.setPreference(PreferencesHelper._PREFNAME_DEFAULT_FILESIZE, txtMaxSize);
            PreferencesHelper.setPreference(PreferencesHelper._PREFNAME_DEFAULT_WAITTIME, txtWaitTime);
            PreferencesHelper.setPreference(PreferencesHelper._PREFNAME_DEFAULT_STOPONERROR, stopOnError);

        }
    }

    @FXML
    public void handleTextChanged(KeyEvent event) {
        Object source = event.getSource();
        //System.out.println(event.getCharacter());
        try {
            if (source == this.default_txtMaxSize) {
                Float.parseFloat(this.default_txtMaxSize.getText() + event.getCharacter());

            } else if (source == this.default_txtTime) {
                Integer.parseInt(this.default_txtTime.getText() + event.getCharacter());
            }
        } catch (NumberFormatException e) {
            event.consume();
        }
    }
}
