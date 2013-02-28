package com.JStormLib;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Kernel32;
import com.JStormLib.StormLibWin.LCID;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Timo Hanisch (timohanisch@gmail.com)
 * @version 1.0
 */
public class SFILE_FIND_DATA extends Structure{
    
    SFILE_FIND_DATA() { }
    
    public byte[] cFileName = new byte[Kernel32.MAX_PATH]; // Full name of the found file
    public String szPlainName;               // Plain name of the found file
    public int  dwHashIndex;                 // Hash table index for the file
    public int  dwBlockIndex;                // Block table index for the file
    public int  dwFileSize;                  // File size in bytes
    public int  dwFileFlags;                 // MPQ file flags
    public int  dwCompSize;                  // Compressed file size
    public int  dwFileTimeLo;                // Low 32-bits of the file time (0 if not present)
    public int  dwFileTimeHi;                // High 32-bits of the file time (0 if not present)
    public LCID lcLocale;                    // Locale version
    
    @Override
    protected List getFieldOrder() {
        return new ArrayList(Arrays.asList(new String[]{"cFileName", "szPlainName", "dwHashIndex", "dwBlockIndex", "dwFileSize", "dwFileFlags", "dwCompSize", "dwFileTimeLo", "dwFileTimeHi", "lcLocale"}));
    }
    
    static class ByReference extends SFILE_FIND_DATA implements Structure.ByReference {}

    static class ByValue extends SFILE_FIND_DATA implements Structure.ByValue {}
}
