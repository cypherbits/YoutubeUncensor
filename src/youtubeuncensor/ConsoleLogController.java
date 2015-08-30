package youtubeuncensor;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juanjo
 */
public class ConsoleLogController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static TaskItem ti;

    @FXML
    private TextArea txtConsoleLog;
    
    public static Thread th;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ConsoleLogController.th = new Thread() {
            public void run() {
                while (true) {

                   updateLog();

                    try {
                        Thread.sleep(1100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        };

        ConsoleLogController.th.start();
    }
    
    public synchronized void updateLog(){
         txtConsoleLog.setText(ti.getConsoleLog());
    }

   

}
