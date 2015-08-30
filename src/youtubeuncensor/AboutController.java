
package youtubeuncensor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author juanjo
 */
public class AboutController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label labelVersion;
    @FXML
    private ImageView imageLogo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.labelVersion.setText(YoutubeUncensor.appversion);
        this.imageLogo.setImage(new Image(getClass().getResourceAsStream("youtube_uncensor.png")));
    }    
    
}
