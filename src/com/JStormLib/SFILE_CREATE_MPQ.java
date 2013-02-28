package com.JStormLib;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Timo Hanisch (timohanisch@gmail.com)
 * @version 1.0
 */
public class SFILE_CREATE_MPQ extends Structure {

    SFILE_CREATE_MPQ() { }
    
    public int cbSize;                       // Size of this structure, in bytes
    public int dwMpqVersion;                 // Version of the MPQ to be created
    public Pointer pvUserData;               // Reserved, must be NULL
    public int cbUserData;                   // Reserved, must be 0
    public int dwStreamFlags;                // Stream flags for creating the MPQ
    public int dwFileFlags1;                 // File flags for (listfile). 0 = default
    public int dwFileFlags2;                 // File flags for (attributes). 0 = default
    public int dwAttrFlags;                  // Flags for the (attributes) file. If 0, no attributes will be created
    public int dwSectorSize;                 // Sector size for compressed files
    public int dwRawChunkSize;               // Size of raw data chunk
    public int dwMaxFileCount;               // File limit for the MPQ

    @Override
    protected List getFieldOrder() {
        return new ArrayList(Arrays.asList(new String[]{"cbSize", "dwMpqVersion", "pvUserData", "cbUserData", "dwStreamFlags", "dwFileFlags1", "dwFileFlags2", "dwAttrFlags", "dwSectorSize", "dwRawChunkSize", "dwMaxFileCount"}));
    }

    static class ByReference extends SFILE_CREATE_MPQ implements Structure.ByReference {
    }

    static class ByValue extends SFILE_CREATE_MPQ implements Structure.ByValue {
    }
}
