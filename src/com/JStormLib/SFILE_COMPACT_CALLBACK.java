/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JStormLib;

import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;

/**
 *
 * @author Timo
 */
public interface SFILE_COMPACT_CALLBACK extends StdCallCallback {

    void apply(Pointer pvUserData, int dwWorkType, long BytesProcessed, long TotalBytes);
    
}
