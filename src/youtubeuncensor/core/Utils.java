package youtubeuncensor.core;

public class Utils {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static String getOSString(){
        return OS;
    }

    public static boolean isOSWindows() {
        return (OS.contains("win"));
    }

    public static boolean isOSMac() {
        return (OS.contains("mac"));
    }

    public static boolean isOSUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0 );
    }

    public static boolean isOSSolaris() {
        return (OS.contains("sunos"));
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
