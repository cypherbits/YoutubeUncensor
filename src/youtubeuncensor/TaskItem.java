package youtubeuncensor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author juanjo
 */
public class TaskItem implements Runnable {

    private int id;
    private String keyword;
    private int nvideos;
    private String status;

    private Thread thread;

    private String consoleLog;

    public static String STATUS_RUNNING = "running";
    public static String STATUS_STOPPED = "stopped";

    public static int WAIT_TIME = 20000; //20 SEGUNDOS entre cada lista y descarga.

    public TaskItem(int id, String keyword) {
        this.id = id;
        this.keyword = keyword;
        this.checkDir();
        this.countVideos();
        this.status = TaskItem.STATUS_STOPPED;
        this.consoleLog = "";
        this.thread = new Thread(this);
    }

    public void startNewThread() {

        this.setStatus(TaskItem.STATUS_RUNNING);

        this.thread = new Thread(this);

        thread.start();
    }

    public void stopThread() {
        this.thread.stop();
        this.setStatus(TaskItem.STATUS_STOPPED);
    }

    public int getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getNvideos() {
        return nvideos;
    }

    public String getStatus() {
        return status;
    }

    public Thread getThread() {
        return this.thread;
    }

    public String getConsoleLog() {
        return this.consoleLog;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setNvideos(int nvideos) {
        this.nvideos = nvideos;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public synchronized void checkDir() {
        File keywordDir = new File(Main.DOWNLOAD_DIR + "/" + this.keyword);
        if (!keywordDir.exists() || !keywordDir.isDirectory()) {
            keywordDir.mkdir();
        }
    }

    public synchronized void countVideos() {
        this.nvideos = new File(Main.DOWNLOAD_DIR + "/" + keyword).listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".mp4")) {
                    return true;
                } else {
                    return false;
                }
            }
        }).length;
    }

    @Override
    public void run() {

        while (true) {

            //System.out.println("Downloading more of" + this.keyword);
            //Reset console log
            this.consoleLog = "";

            String youtubedl = "./youtube-dl";
            String youtubeURL = "https://www.youtube.com/results?search_sort=video_date_uploaded&filters=hour&search_query=" + this.keyword;
            String downloadDir = Main.DOWNLOAD_DIR + "/" + this.keyword + "/" + "%(id)s.%(ext)s";
            String logfile = "already_listed_log.log";

            String[] command = {youtubedl, youtubeURL, "-o", downloadDir, "--max-filesize", "30.1m", "--download-archive", logfile, "--no-playlist", "--max-downloads", "2", "--write-info-json"};
            Runtime runtime = Runtime.getRuntime();

            try {
                Process process = runtime.exec(command);

                //Read out dir output
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                //System.out.printf("Output of running %s is:\n",Arrays.toString(command));

                while ((line = br.readLine()) != null) {
                    //System.out.println(line);
                    this.consoleLog += line + "\n";
                }

                //Wait to get exit value
                try {
                    int exitValue = process.waitFor();
                    // System.out.println("\n\nExit Value is " + exitValue);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (IOException ex) {
                Logger.getLogger(TaskItem.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.countVideos();

            try {
                Thread.sleep(TaskItem.WAIT_TIME);
            } catch (InterruptedException ex) {
                Logger.getLogger(TaskItem.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
