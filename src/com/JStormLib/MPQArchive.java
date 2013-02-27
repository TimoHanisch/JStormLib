package com.JStormLib;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.WinNT.HANDLEByReference;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents an MPQ-Archive. Implements some of the usefull functions which can
 * be accessed with StormLib. For maximal functionality use JStormLibWin.
 *
 * Class can not be initialized via standard constructor, but has to be created
 * with MPQArchive.openArchive/createArchive .
 *
 * @author Timo Hanisch (timohanisch@gmail.com)
 * @version 1.1
 */
public class MPQArchive {

    private static StormLibWin lib;
    private HANDLEByReference ref;
    private boolean closed = true;

    private MPQArchive() {
        ref = new HANDLEByReference();
        if (lib == null) {
            lib = StormLibWin.INSTANCE;
        }
    }

    /**
     * Adds a new listfile to the archive, {@link #flush()} should be called
     * afterwards, since addListfile only adds to the in-memory archive. 
     * 
     * @param f The listfile
     * @throws FileNotFoundException Thrown if f could not be found
     * @throws MPQArchiveException Thrown if archive is inactive or an error 
     * occured while adding the listfile
     */
    public void addListFile(File f) throws FileNotFoundException, MPQArchiveException {
        if (!closed) {
            if (!f.exists()) {
                throw new FileNotFoundException("Could not find " + f);
            }
            int e = lib.SFileAddListFile(ref.getValue(), f.getAbsolutePath());
            if (e != 0) {
                throw new MPQArchiveException("Error occured while adding a new list file. Errorcode " + e);
            }
        } else {
            throw new MPQArchiveException("Inactive archive");
        }
    }

    /**
     * Extracts a file from the archive to the given path.
     * 
     * @param fileName File within the archive
     * @param dest The file destination
     * @throws MPQArchiveException Thrown if archive is inactive or an error 
     * occured while extracting the file.
     */
    public void extractFile(String fileName, File dest) throws MPQArchiveException {
        if (!closed) {
            if (lib.SFileExtractFile(ref.getValue(), fileName, dest.getAbsolutePath(), StormLibWin.SFILE_OPEN_FROM_MPQ) == 0) {
                throw new MPQArchiveException("Error occured while extracting a file. Errorcode " + Kernel32.INSTANCE.GetLastError());
            }
        } else {
            throw new MPQArchiveException("Inactive archive");
        }
    }

    /**
     * Adds an external file to the archive. Internally {@link StormLibWin#SFileCompactArchive} 
     * is called for defragmenting the archive.
     * 
     * @param f The file which should be added
     * @param name The name for the file within the archive
     * @throws MPQArchiveException Thrown if archive is inactive or an error occured
     * while adding the file or compacting the archive.
     */
    public void addFile(File f, String name) throws MPQArchiveException {
        if (!closed) {
            if (lib.SFileAddFileEx(ref.getValue(), f.getAbsolutePath(), name, StormLibWin.MPQ_FILE_COMPRESS, StormLibWin.MPQ_COMPRESSION_HUFFMANN, StormLibWin.MPQ_COMPRESSION_HUFFMANN) != 0) {
                if (lib.SFileCompactArchive(ref.getValue(), null, (byte) 0) == 0) {
                    throw new MPQArchiveException("Error while compacting archive. Errorcode " + Kernel32.INSTANCE.GetLastError());
                }
            } else {
                int error = Kernel32.INSTANCE.GetLastError();
                throw new MPQArchiveException("Error while adding file to archive. " + (error == 183 ? "File already exists.":"Errorcode "+error));
            }
        } else {
            throw new MPQArchiveException("Inactive archive");
        }
    }
    
    /**
     * Removes the file with the given name from the archive. Internally {@link StormLibWin#SFileCompactArchive} 
     * is called for defragmenting the archive.
     * 
     * @param name Name of the file
     * @throws MPQArchiveException Thrown if archive is inactive or an error occured
     * while removing the file or compacting the archive.
     */
    public void removeFile(String name) throws MPQArchiveException{
        if (!closed) {
            if (lib.SFileRemoveFile(ref.getValue(), name, 0) != 0) {
                if (lib.SFileCompactArchive(ref.getValue(), null, (byte) 0) == 0) {
                    throw new MPQArchiveException("Error while compacting archive. Errorcode " + Kernel32.INSTANCE.GetLastError());
                }
            } else {
                int error = Kernel32.INSTANCE.GetLastError();
                throw new MPQArchiveException("Error while removing file from archive. " + (error == 2 ? "File not found.":"Errorcode "+error));
            }
        } else {
            throw new MPQArchiveException("Inactive archive");
        }
    }
    
    /**
     * Renames a file within the archive.
     * 
     * @param oldName The old name of the file
     * @param newName The new name of the file
     * @throws MPQArchiveException Thrown if archive is inactive or an error occured
     * while renaming the file.
     */
    public void renameFile(String oldName, String newName) throws MPQArchiveException{
        if (!closed) {
            if (lib.SFileRenameFile(ref.getValue(), oldName, newName) == 0) {
                int error = Kernel32.INSTANCE.GetLastError();
                throw new MPQArchiveException("Error while removing file from archive. " + (error == 2 ? "File not found.":"Errorcode "+error));
            }
        } else {
            throw new MPQArchiveException("Inactive archive");
        }
    }
    
    /**
     * Checks if the given file exists within the MPQ-Archive.
     * 
     * @param fileName The name which should be looked up.
     * @return True when file exists within the archive, otherwise false.
     * @throws MPQArchiveException Thrown if archive is inactive.
     */
    public boolean containsFile(String fileName) throws MPQArchiveException {
        if (closed) {
            throw new MPQArchiveException("Inactive archive");
        }
        return lib.SFileHasFile(ref.getValue(), fileName) != 0;
    }

    /**
     * Lists all files within the archive.
     * 
     * @return List of filenames within the archive.
     * @throws MPQArchiveException Thrown if archive is inactive.
     */
    public List<String> listFiles() throws MPQArchiveException {
        if (closed) {
            throw new MPQArchiveException("Inactive archive");
        }
        List<String> list = new LinkedList<String>();
        SFILE_FIND_DATA.ByReference data = new SFILE_FIND_DATA.ByReference();
        HANDLE h = lib.SFileFindFirstFile(ref.getValue(), "*", data, null);
        list.add(data.szPlainName);
        while (lib.SFileFindNextFile(h, data) != 0) {
            list.add(data.szPlainName);
        }
        lib.SFileFindClose(h);
        return list;
    }
    
    /**
     * Adds any in-memory changes to the drive.
     * 
     * @throws MPQArchiveException Thrown if archive is inactive or an error occured.
     */
    public void flush() throws MPQArchiveException {
        if (!closed) {
            if (lib.SFileFlushArchive(ref.getValue()) == 0) {
                throw new MPQArchiveException("Error occured while flushing the archive. Errorcode " + Kernel32.INSTANCE.GetLastError());
            }
        } else {
            throw new MPQArchiveException("Inactive archive");
        }
    }

    /**
     * Closes the archive and marks it as inactive. Internally calls {@link #flush()}.
     * 
     * @throws IOException Thrown if archive could not be closed
     * @throws MPQArchiveException Thrown if archive is allready inactive
     */
    public void close() throws IOException, MPQArchiveException {
        if (!closed) {
            flush();
            if (lib.SFileCloseArchive(ref.getValue()) == 0) {
                throw new IOException("Could not close the archive " + this);
            }
            closed = true;
        } else {
            throw new MPQArchiveException("Inactive archive");
        }
    }

    /**
     * Creates an archive. When an archive for the given file already exists, the
     * archive is opened.
     * 
     * @param f File for the archive to be created
     * @return The created or opened archive
     * @throws IOException Thrown if the archive could not be created or opened
     */
    public static MPQArchive createArchive(File f) throws IOException {
        return createArchive(f, StormLibWin.MPQ_CREATE_ARCHIVE_V2, StormLibWin.HASH_TABLE_SIZE_MIN);
    }

    /**
     * Creates an archive with given version: <br>{@link StormLibWin#MPQ_CREATE_ARCHIVE_V1}, 
     * <br>{@link StormLibWin#MPQ_CREATE_ARCHIVE_V2}, 
     * <br>{@link StormLibWin#MPQ_CREATE_ARCHIVE_V3}, 
     * <br>{@link StormLibWin#MPQ_CREATE_ARCHIVE_V4}, 
     * <br>Default is {@link StormLibWin#MPQ_CREATE_ARCHIVE_V2}. The maxFiles should be
     * within {@link StormLibWin#HASH_TABLE_SIZE_MIN} and {@link StormLibWin#HASH_TABLE_SIZE_MAX}. 
     * When an archive for the given file already exists, the archive is opened.
     * 
     * @param f File for the archive to be created
     * @param mpqVersion MPQ-Version for the archive
     * @param maxFiles The max files contained by the archive
     * @return The created or opened archive
     * @throws IOException Thrown if the archive could not be created or opened
     */
    public static MPQArchive createArchive(File f, int mpqVersion, int maxFiles) throws IOException {
        if (mpqVersion != StormLibWin.MPQ_CREATE_ARCHIVE_V1 && mpqVersion != StormLibWin.MPQ_CREATE_ARCHIVE_V2
                && mpqVersion != StormLibWin.MPQ_CREATE_ARCHIVE_V3 && mpqVersion != StormLibWin.MPQ_CREATE_ARCHIVE_V4) {
            mpqVersion = StormLibWin.MPQ_CREATE_ARCHIVE_V2;
        }
        if (maxFiles < StormLibWin.HASH_TABLE_SIZE_MIN) {
            maxFiles = StormLibWin.HASH_TABLE_SIZE_MIN;
        } else if (maxFiles > StormLibWin.HASH_TABLE_SIZE_MAX) {
            maxFiles = StormLibWin.HASH_TABLE_SIZE_MAX;
        }
        MPQArchive archive = new MPQArchive();
        if (lib.SFileCreateArchive(f.getAbsolutePath(), mpqVersion, maxFiles, archive.ref) != 0) {
            archive.closed = false;
            return archive;
        }
        throw new IOException("Could not create the archive " + f);
    }

    /**
     * Opens the archive for the given file.
     * 
     * @param f The archive file
     * @return The opened archive 
     * @throws FileNotFoundException Thrown if f could not be found
     * @throws IOException Thrown if the archive could not be created or opened
     */
    public static MPQArchive openArchive(File f) throws FileNotFoundException, IOException {
        return openArchive(f, StormLibWin.BASE_PROVIDER_FILE);
    }

    /**
     * Opens the archive for the given file with chosen flag. For flags available
     * check <a href="http://www.zezula.net/en/mpq/stormlib/sfileopenarchive.html">http://www.zezula.net/en/mpq/stormlib/sfileopenarchive.html</a>
     * 
     * @param f The archive file
     * @param flag Specifies how to open the archive
     * @return The opened archive
     * @throws FileNotFoundException Thrown if f could not be found
     * @throws IOException Thrown if the archive could not be created or opened
     */
    public static MPQArchive openArchive(File f, int flag) throws FileNotFoundException, IOException {
        if (!f.exists()) {
            throw new FileNotFoundException("Could not find " + f);
        }
        MPQArchive archive = new MPQArchive();
        if (lib.SFileOpenArchive(f.getAbsolutePath(), 0, flag, archive.ref) != 0) {
            archive.closed = false;
            return archive;
        }
        throw new IOException("Could not open the archive " + f);
    }
}