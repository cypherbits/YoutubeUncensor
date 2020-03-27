package youtubeuncensor.core;

public class Utils {

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static String getOSString(){
        return OS;
    }

    public static boolean isOSWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isOSMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isOSUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }

    public static boolean isOSSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }

    public static String getYoutubedlPath() {
        String path = Constants.rutaBin + "/";
        if (isOSUnix()){
            path += Constants.exeLinux;
        }else if(isOSWindows()){
            path += Constants.exeWindows;
        }

        return path;
    }
}
