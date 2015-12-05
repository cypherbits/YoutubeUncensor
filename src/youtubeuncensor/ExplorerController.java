package youtubeuncensor;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

                addVideosFromKeyword(item);

            }
        } else {
            for (TaskItem item : Main.taskList) {
                if (item.getKeyword().equals(keyword)) {
                   
                    addVideosFromKeyword(item);
                    break;
                }
            }
        }
    }

    private void addVideosFromKeyword(TaskItem item) {

        //TODO BETTER
        File[] files = item.getVideoFiles();

        for (File file : files) {

            String name = file.getName().replace(".mp4", ".jpg");

            File imageFile = new File(item.getDirectory().getAbsolutePath() + "/" + name);

            if (imageFile.exists()) {

                ImageView image = new ImageView(imageFile.toURI().toString());
                image.setFitWidth(200);
                image.setFitHeight(200);

                MenuItem item1 = new MenuItem("Delete");
                item1.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        System.out.println("About");
                    }
                });

                ContextMenu cm = new ContextMenu();
                cm.getItems().add(item1);

                image.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.isSecondaryButtonDown()) {
                            cm.show(image, event.getScreenX(), event.getScreenY());
                        }
                    }
                });

                this.flowpane.getChildren().add(image);
            } else {
                this.flowpane.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream("youtube_uncensor.png"))));
            }

        }
    }

}
