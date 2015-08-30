package youtubeuncensor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author juanjo
 */
public class AddTaskController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtKeyword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Object source = event.getSource();

        if (source == this.btnAdd) {
            if (!txtKeyword.getText().trim().equals("")) {
                Main.taskList.add(new TaskItem(Main.taskList.size(), txtKeyword.getText()));
                //close
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        }
    }

}
