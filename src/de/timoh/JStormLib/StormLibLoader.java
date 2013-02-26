package de.timoh.JStormLib;

import com.sun.jna.Native;

/**
 *
 * @author Timo Hanisch
 */
class StormLibLoader {
    
    public static final byte OS_UNKNOWN = -1;
    public static final byte OS_WIN = 0;
    public static final byte OS_UNIX = 1;
    public static final byte OS_SOLARIS = 2;
    public static final byte OS_MAC = 3;

    public static final String WIN_STORMLIB_x86 = "StormLib_x86";
    public static final String WIN_STORMLIB_x64 = "StormLib_x64";
    
    public static StormLibWin INSTANCE;

    static {
        String arch = System.getProperty("os.arch");
        if(System.getProperty("jna.library.path") == null){
            System.setProperty("jna.library.path", System.getProperty("user.dir"));
        }
        INSTANCE = (StormLibWin) Native.loadLibrary(getPathForOS(!arch.contains("64")), StormLibWin.class);
    }

    public static String getPathForOS(boolean x86) {
        switch(getOS()){
            case OS_WIN :
                if(x86){
                    return WIN_STORMLIB_x86;
                }
                return WIN_STORMLIB_x64;
            case OS_UNIX:
                //NOT SUPPORTED YET
                return null;
            case OS_SOLARIS:
                //NOT SUPPORTED YET
                return null;
            case OS_MAC:
                //NOT SUPPORTED YET
                return null;
        }
        return null;
    }
    
    /**
     * Determines the current operating system.
     * 
     * @return 
     */
    public static byte getOS(){
        String os = System.getProperty("os.name").toLowerCase();
        if(os.indexOf("win") >= 0){
            return OS_WIN;
        }else if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0){
            return OS_UNIX;
        }else if(os.indexOf("sunos") >= 0){
            return OS_SOLARIS;
        }else if(os.indexOf("mac") >= 0){
            return OS_MAC;
        }
        return OS_UNKNOWN;
    }
}
