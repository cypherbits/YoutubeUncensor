package youtubeuncensor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author juanjo
 */
public class YoutubeUncensor extends Application {

    public static final String appversion = "v1.0 Beta x (x/09/2015)";

    @Override
    public void start(Stage stage) throws Exception {
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

}
