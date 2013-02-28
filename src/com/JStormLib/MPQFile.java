package com.JStormLib;

/**
 * Represents a file within an MPQ-Archive.
 *
 * @author Timo Hanisch (timohanisch@gmail.com)
 * @version 1.2
 */
public class MPQFile {

    private String name;
    private int size;
    private int compressedSize;

    MPQFile(SFILE_FIND_DATA data) {
        this.name = data.szPlainName;
        this.size = data.dwFileSize;
        this.compressedSize = data.dwCompSize;
    }

    /**
     * Returns the name of a file within an MPQ-Archive.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the size in bytes of a file within an MPQ-Archive.
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the compressed size in bytes of a file within an MPQ-Archive.
     *
     * @return
     */
    public int getCompressedSize() {
        return compressedSize;
    }

    @Override
    public String toString() {
        return "[" + name + "," + size + " Bytes," + compressedSize + " Bytes]";
    }
}
