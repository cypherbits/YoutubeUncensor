package youtubeuncensor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private File directory;

    public TaskItem(int id, String keyword) {
        this.id = id;
        this.keyword = keyword;
        this.checkDir();
        this.countVideos();
        this.status = TaskItem.STATUS_STOPPED;
        this.consoleLog = "";
        this.thread = new Thread(this);
        this.directory = new File(Main.DOWNLOAD_DIR + "/" + keyword);
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
        if (!this.directory.exists() || !this.directory.isDirectory()) {
            this.directory.mkdir();
        }
    }

    public synchronized void countVideos() {

        this.deleteJSONwithNoVideos();

        this.nvideos = this.directory.listFiles(new FilenameFilter() {

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

    public synchronized void deleteJSONwithNoVideos() {
        //Workaround for bug #8
        //Workaround para youtube-dl en el que descarga el json aunque no descargue el video por los filtros
        File[] files = this.directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        });

        for (File file : files) {
            if (!new File(file.getAbsolutePath() + "/" + file.getName().split(".")[1] + ".mp4").exists()) {
                file.delete();
            }
        }

    }

    @Override
    public void run() {

        while (true) {

            //Reset console log
            this.consoleLog = "";

            String youtubedl = "./youtube-dl";
            String youtubeURL = "https://www.youtube.com/results?search_sort=video_date_uploaded&filters=hour&search_query=" + this.keyword;
            String downloadDir = this.directory.getAbsolutePath() + "/" + "%(id)s.%(ext)s";
            String logfile = "already_listed_log.log";

            String[] command = {youtubedl, youtubeURL, "-o", downloadDir, "--max-filesize", "30.1m", "--download-archive", logfile, "--no-playlist", "--max-downloads", "2", "--write-info-json"};
            Runtime runtime = Runtime.getRuntime();

            try {
                Process process = runtime.exec(command);

                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;

                while ((line = br.readLine()) != null) {
                    this.consoleLog += line + "\n";
                }

                //Wait to get exit value
                try {
                    int exitValue = process.waitFor();
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
