package com.JStormLib;

import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;

/**
 * @author Timo Hanisch (timohanisch@gmail.com)
 * @version 1.0
 */
interface SFILE_COMPACT_CALLBACK extends StdCallCallback {

    void apply(Pointer pvUserData, int dwWorkType, long BytesProcessed, long TotalBytes);
    
}
