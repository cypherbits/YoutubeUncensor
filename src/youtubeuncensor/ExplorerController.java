package youtubeuncensor;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import youtubeuncensor.core.TaskItem;

/**
 * FXML Controller class
 *
 * @author juanjo
 */
public class ExplorerController implements Initializable {

    @FXML
    private ChoiceBox menu_choiceKeyword;
    @FXML
    private FlowPane flowpane;

    private ObservableList<String> keywordList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.keywordList = FXCollections.observableArrayList();

        this.keywordList.add("ALL KEYWORDS");

        for (TaskItem item : Main.taskList) {
            this.keywordList.add(item.getKeyword());
        }

        menu_choiceKeyword.setItems(this.keywordList);

        menu_choiceKeyword.getSelectionModel().selectFirst();

        menu_choiceKeyword.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (menu_choiceKeyword.getSelectionModel().getSelectedIndex() == 0) {
                listVideos(null);
            } else {
                listVideos(newValue.toString());
            }
        });

        listVideos(null);

    }

    private void listVideos(String keyword) {

        this.flowpane.getChildren().clear();

        if (keyword == null) {
            for (TaskItem item : Main.taskList) {

                //TODO BETTER
                File[] files = item.getVideoFiles();

                for (File file : files) {

                    String name = file.getName().replace(".mp4", ".jpg");

                    File imageFile = new File(item.getDirectory().getAbsolutePath() + "/" + name);

                    if (imageFile.exists()) {

                        ImageView image = new ImageView(imageFile.toURI().toString());
                        image.setFitWidth(200);
                        image.setFitHeight(200);

                        this.flowpane.getChildren().add(image);
                    } else {
                        this.flowpane.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream("youtube_uncensor.png"))));
                    }

                }

            }
        } else {
            for (TaskItem item : Main.taskList) {
                if (item.getKeyword().equals(keyword)) {
                    //TODO
                    break;
                }
            }
        }
    }

}
