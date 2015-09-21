package youtubeuncensor;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author juanjo
 */
public class GlobalConfigController implements Initializable {
    
    @FXML
    private TextField global_txtDownloadPath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        global_txtDownloadPath.setText(new File(Main.DOWNLOAD_DIR).getAbsolutePath());
        
    }    
    
}
