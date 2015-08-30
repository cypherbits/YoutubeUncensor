package youtubeuncensor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author juanjo
 */
public class Main implements Initializable {

    @FXML
    private Button btnStartAll;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnStopAll;
    @FXML
    private Button btnStop;
    @FXML
    private Button btnAddTask;
    @FXML
    private Button btnRemoveTask;
    @FXML
    private Button btnShowLog;
    @FXML
    private Button btnAbout;

    @FXML
    private TableView tableView_tasks;

    public static ObservableList<TaskItem> taskList;

    private static Thread updateThread;

    public static String DOWNLOAD_DIR = "downloads";

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Object source = event.getSource();

        if (source == this.btnAbout) {
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("About.fxml"));
                Stage stage = new Stage();
                stage.setTitle("About");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (source == this.btnStartAll) {

            startUpdateListThread();

            for (int i = 0; i < taskList.size(); i++) {
                if (!taskList.get(i).getThread().isAlive()) {
                    taskList.get(i).startNewThread();

                }
            }

        } else if (source == this.btnStart) {
            TaskItem ti = (TaskItem) tableView_tasks.getSelectionModel().getSelectedItem();

            if (ti != null) {

                startUpdateListThread();
                ti.startNewThread();
            }
        } else if (source == this.btnStop) {
            TaskItem ti = (TaskItem) tableView_tasks.getSelectionModel().getSelectedItem();

            if (ti != null) {
                ti.stopThread();
            }
        } else if (source == this.btnStopAll) {
            for (int i = 0; i < taskList.size(); i++) {
                if (taskList.get(i).getThread().isAlive()) {
                    taskList.get(i).stopThread();
                }
            }
        } else if (source == this.btnAddTask) {
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("AddTask.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Add new task");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (source == this.btnRemoveTask) {

        } else if (source == this.btnShowLog) {
            TaskItem ti = (TaskItem) tableView_tasks.getSelectionModel().getSelectedItem();
            if (ti != null) {
                try {
                    ConsoleLogController.ti = ti;
                    Parent root;
                    root = FXMLLoader.load(getClass().getResource("ConsoleLog.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Console log for " + ti.getKeyword());
                    stage.setScene(new Scene(root));
                    //stage.setResizable(false);
                    stage.show();

                    stage.setOnCloseRequest(e -> {
                        ConsoleLogController.th.stop();
                    });
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        taskList = FXCollections.observableArrayList();

        tableView_tasks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn colId = new TableColumn("id");
        TableColumn colKeyword = new TableColumn("keywork");
        TableColumn colNvideos = new TableColumn("number of videos");
        TableColumn colStatus = new TableColumn("status");

        colId.setCellValueFactory(
                new PropertyValueFactory<TaskItem, String>("id")
        );
        colKeyword.setCellValueFactory(
                new PropertyValueFactory<TaskItem, String>("keyword")
        );
        colNvideos.setCellValueFactory(
                new PropertyValueFactory<TaskItem, String>("nvideos")
        );
        colStatus.setCellValueFactory(
                new PropertyValueFactory<TaskItem, String>("status")
        );

        tableView_tasks.getColumns().addAll(colId, colKeyword, colNvideos, colStatus);

        tableView_tasks.setItems(taskList);

        tableView_tasks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                if (newValue != null) {
                    TaskItem tr = (TaskItem) newValue;

                    //btns
                    btnStart.setDisable(tr.getThread().isAlive());
                    btnStop.setDisable(!tr.getThread().isAlive());
                    btnRemoveTask.setDisable(tr.getThread().isAlive());
                    btnShowLog.setDisable(false);

                } else {
                    //btns
                    btnStart.setDisable(true);
                    btnStop.setDisable(true);
                    btnRemoveTask.setDisable(true);
                    btnShowLog.setDisable(true);
                }

            }
        });

        //test
//        TaskItem tk = new TaskItem(1, "obama", 0);
//        taskList.add(tk);
        checkFiles();
        startListDir();

    }

    public void startListDir() {

        File downloadDir = new File(Main.DOWNLOAD_DIR);
        if (!downloadDir.exists() || !downloadDir.isDirectory()) {
            downloadDir.mkdir();
        }

        File[] keywordListDirs = downloadDir.listFiles();

        int j = 0;
        for (int i = 0; i < keywordListDirs.length; i++) {
            if (keywordListDirs[i].isDirectory()) {
                String keyword = keywordListDirs[i].getName();

                TaskItem tk = new TaskItem(j, keyword);

                taskList.add(tk);

                //Workaround para youtube-dl en el que descarga el json aunque no descargue el video por los filtros
//                File[] files = keywordListDirs[i].listFiles(new FilenameFilter() {
//
//                    @Override
//                    public boolean accept(File dir, String name) {
//                        if (name.endsWith(".json")) {
//                            return true;
//                        } else {
//                            return false;
//                        }
//                    }
//                });
//
//                for (int k = 0; k < files.length; k++) {
//                    if (!new File(files[k].getAbsolutePath() + "/" + files[k].getName().split(".")[1] + ".mp4").exists()) {
//                        //borrar json
//                        
//                    }
//                }

                j++;
            }
        }

    }
    
    public void checkFiles(){
        File youtubedl = new File("youtube-dl");
        if (youtubedl.exists()){
            if (!youtubedl.canExecute()){
                youtubedl.setExecutable(true);
            }
        }else{
            //TODO
            //error message etc...
        }
    }
            

    public void startUpdateListThread() {
        if (updateThread == null || !updateThread.isAlive()) {
            updateThread = new Thread() {
                public void run() {
                    //System.out.println("Thread: " + getName() + " running");
                    while (true) {
                        updateList();

                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                }
            };

            updateThread.start();
        }
    }

    public synchronized void updateList() {

        boolean somethingRunning = false;
        for (int i = 0; i < taskList.size(); i++) {
            taskList.set(i, taskList.get(i));
            if (taskList.get(i).getThread().isAlive()) {
                somethingRunning = true;
            }
        }

        //this.btnStartAll.setDisable(somethingRunning);
        this.btnStopAll.setDisable(!somethingRunning);

        if (!somethingRunning) {
            updateThread.stop();
        }
    }

}
