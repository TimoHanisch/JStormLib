package de.timoh.JStormLib;

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
 * be accessed with StormLib. For maximal functionality use JStormLIbWin.
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

    public void extractFile(String fileName, File dest) throws MPQArchiveException {
        if (!closed) {
            if (lib.SFileExtractFile(ref.getValue(), fileName, dest.getAbsolutePath(), StormLibWin.SFILE_OPEN_FROM_MPQ) == 0) {
                throw new MPQArchiveException("Error occured while extracting a file. Errorcode " + Kernel32.INSTANCE.GetLastError());
            }
        } else {
            throw new MPQArchiveException("Inactive archive");
        }
    }

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
    
    public void renameFile(String oldName, String newName) throws MPQArchiveException{
        if (!closed) {
            if (lib.SFileRenameFile(ref.getValue(), oldName, newName) != 0) {
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

    public boolean containsFile(String fileName) throws MPQArchiveException {
        if (closed) {
            throw new MPQArchiveException("Inactive archive");
        }
        return lib.SFileHasFile(ref.getValue(), fileName) != 0;
    }

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

    public void flush() throws MPQArchiveException {
        if (!closed) {
            if (lib.SFileFlushArchive(ref.getValue()) == 0) {
                throw new MPQArchiveException("Error occured while flushing the archive. Errorcode " + Kernel32.INSTANCE.GetLastError());
            }
        } else {
            throw new MPQArchiveException("Inactive archive");
        }
    }

    public void close() throws IOException, MPQArchiveException {
        if (!closed) {
            if (lib.SFileCloseArchive(ref.getValue()) == 0) {
                throw new IOException("Could not close the archive " + this);
            }
            closed = true;
        } else {
            throw new MPQArchiveException("Inactive archive");
        }
    }

    public static MPQArchive createArchive(File f) throws IOException {
        return createArchive(f, StormLibWin.MPQ_CREATE_ARCHIVE_V2, StormLibWin.HASH_TABLE_SIZE_MIN);
    }

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

    public static MPQArchive openArchive(File f) throws FileNotFoundException, IOException {
        return openArchive(f, StormLibWin.BASE_PROVIDER_FILE);
    }

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