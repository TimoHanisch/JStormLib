package de.timoh.JStormLib;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.WinNT.HANDLEByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.win32.StdCallLibrary;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * StormLibWin is used to open and manipulate MPQ-Archives from JAVA on Windows-Systems.
 * <br><br>
 * This interface is compatible with x86 and x64 systems if both DLLs are present.
 * (StormLib_x86.dll and StormLib_x64.dll). By default the path to the DLLs is
 * {@code  System.getProperty("user.dir")}. 
 * <br><br>
 * This Interface is a wrapper for the StormLib.dll developed by <a href="mailto:ladik@zezula.net">Ladislav Zezula</a>
 * for Windows. 
 * 
 * @author <a href="mailto:timohanisch@gmail.com">Timo Hanisch</a>
 * @version 1.0
 */
public interface StormLibWin extends StdCallLibrary {

    public static final StormLibWin INSTANCE = StormLibLoader.INSTANCE;
    
    public static final int STORMLIB_VERSION = 0x0815;
    public static final String STORMLIB_VERSION_STRING = "8.21";
    
    public static final int ID_MPQ = 0x1A51504D;
    public static final int ID_MPQ_USERDATA = 0x1B51504D;
    
    public static final int ERROR_AVI_FILE = 10000;
    public static final int ERROR_UNKNOWN_FILE_KEY = 10001;
    public static final int ERROR_CHECKSUM_ERROR = 10002;
    public static final int ERROR_INTERNAL_FILE = 10003;
    public static final int ERROR_BASE_FILE_MISSING = 10004;
    public static final int ERROR_MARKED_FOR_DELETE = 10005;
    
    // Values for SFileCreateArchive
    public static final int HASH_TABLE_SIZE_MIN = 0x00000004;
    public static final int HASH_TABLE_SIZE_DEFAULT = 0x00001000;
    public static final int HASH_TABLE_SIZE_MAX = 0x00080000;
    public static final int HASH_ENTRY_DELETED = 0xFFFFFFFE;
    public static final int HASH_ENTRY_FREE = 0xFFFFFFFF;
    public static final int HET_ENTRY_DELETED = 0x80;
    public static final int HET_ENTRY_FREE = 0x00;
    public static final int HASH_STATE_SIZE = 0x60;
    public static final int MPQ_PATCH_PREFIX_LEN = 0x20;
    
    // Values for SFileOpenArchive
    public static final int SFILE_OPEN_HARD_DISK_FILE = 2;
    public static final int SFILE_OPEN_CDROM_FILE = 3;
    
    // Values for SFileOpenFile
    public static final int SFILE_OPEN_FROM_MPQ = 0x00000000;
    public static final int SFILE_OPEN_BASE_FILE = 0xFFFFFFFD;
    public static final int SFILE_OPEN_ANY_LOCALE = 0xFFFFFFFE;
    public static final int SFILE_OPEN_LOCAL_FILE = 0xFFFFFFFF;
    
    // Flags for TMPQArchive::dwFlags
    public static final int MPQ_FLAG_READ_ONLY = 0x00000001;
    public static final int MPQ_FLAG_CHANGED = 0x00000002;
    public static final int MPQ_FLAG_PROTECTED = 0x00000004;
    public static final int MPQ_FLAG_CHECK_SECTOR_CRC = 0x00000008;
    public static final int MPQ_FLAG_NEED_FIX_SIZE = 0x00000010;
    public static final int MPQ_FLAG_INV_LISTFILE = 0x00000020;
    public static final int MPQ_FLAG_INV_ATTRIBUTES = 0x00000040;
    
    // Return value for SFileGetFileSize and SFileSetFilePointer
    public static final int SFILE_INVALID_SIZE = 0xFFFFFFFF;
    public static final int SFILE_INVALID_POS = 0xFFFFFFFF;
    public static final int SFILE_INVALID_ATTRIBUTES = 0xFFFFFFFF;
    
    // Flags for SFileAddFile
    public static final int MPQ_FILE_IMPLODE = 0x00000100;
    public static final int MPQ_FILE_COMPRESS = 0x00000200;
    public static final int MPQ_FILE_COMPRESSED = 0x0000FF00;
    public static final int MPQ_FILE_ENCRYPTED = 0x00010000;
    public static final int MPQ_FILE_FIX_KEY = 0x00020000;
    public static final int MPQ_FILE_PATCH_FILE = 0x00100000;
    public static final int MPQ_FILE_SINGLE_UNIT = 0x01000000;
    public static final int MPQ_FILE_DELETE_MARKER = 0x02000000;
    public static final int MPQ_FILE_SECTOR_CRC = 0x04000000;
    public static final int MPQ_FILE_EXISTS = 0x80000000;
    public static final int MPQ_FILE_REPLACEEXISTING = 0x80000000;
    public static final int MPQ_FILE_VALID_FLAGS = ( MPQ_FILE_IMPLODE       | 
                                                     MPQ_FILE_COMPRESS      | 
                                                     MPQ_FILE_ENCRYPTED     | 
                                                     MPQ_FILE_FIX_KEY       | 
                                                     MPQ_FILE_PATCH_FILE    | 
                                                     MPQ_FILE_SINGLE_UNIT   | 
                                                     MPQ_FILE_DELETE_MARKER | 
                                                     MPQ_FILE_SECTOR_CRC    | 
                                                     MPQ_FILE_EXISTS);
    
    // Compression types for multiple compressions
    public static final int MPQ_COMPRESSION_HUFFMANN = 0x01;
    public static final int MPQ_COMPRESSION_ZLIB = 0x02;
    public static final int MPQ_COMPRESSION_PKWARE = 0x08;
    public static final int MPQ_COMPRESSION_BZIP2 = 0x10;
    public static final int MPQ_COMPRESSION_SPARSE = 0x20;
    public static final int MPQ_COMPRESSION_ADPCM_MONO = 0x40;
    public static final int MPQ_COMPRESSION_ADPCM_STEREO = 0x80;
    public static final int MPQ_COMPRESSION_LZMA = 0x12;
    public static final int MPQ_COMPRESSION_NEXT_SAME = 0xFFFFFFFF;
    
    // Constants for SFileAddWave
    public static final int MPQ_WAVE_QUALITY_HIGH = 0;
    public static final int MPQ_WAVE_QUALITY_MEDIUM = 1;
    public static final int MPQ_WAVE_QUALITY_LOW = 2;
    
    // Signatures for HET and BET table
    public static final int HET_TABLE_SIGNATURE = 0x1A544548;
    public static final int BET_TABLE_SIGNATURE = 0x1A544542;
    
    // Decryption keys for MPQ tables
    public static final int MPQ_KEY_HASH_TABLE = 0xC3AF3770;
    public static final int MPQ_KEY_BLOCK_TABLE = 0xEC83B3A3;
    
    // Block map defines
    public static final int MPQ_DATA_BITMAP_SIGNATURE = 0x33767470;
    
    // Constants for SFileGetFileInfo
    public static final int SFILE_INFO_ARCHIVE_NAME = 1;
    public static final int SFILE_INFO_ARCHIVE_SIZE = 2;
    public static final int SFILE_INFO_MAX_FILE_COUNT = 3;
    public static final int SFILE_INFO_HASH_TABLE_SIZE = 4;
    public static final int SFILE_INFO_BLOCK_TABLE_SIZE = 5;
    public static final int SFILE_INFO_SECTOR_SIZE = 6;
    public static final int SFILE_INFO_HASH_TABLE = 7;
    public static final int SFILE_INFO_BLOCK_TABLE = 8;
    public static final int SFILE_INFO_NUM_FILES = 9;
    public static final int SFILE_INFO_STREAM_FLAGS = 10;
    public static final int SFILE_INFO_IS_READ_ONLY = 11;
    public static final int SFILE_INFO_HASH_INDEX = 100;
    public static final int SFILE_INFO_CODENAME1 = 101;
    public static final int SFILE_INFO_CODENAME2 = 102;
    public static final int SFILE_INFO_LOCALEID = 103;
    public static final int SFILE_INFO_BLOCKINDEX = 104;
    public static final int SFILE_INFO_FILE_SIZE = 105;
    public static final int SFILE_INFO_COMPRESSED_SIZE = 106;
    public static final int SFILE_INFO_FLAGS = 107;
    public static final int SFILE_INFO_POSITION = 108;
    public static final int SFILE_INFO_KEY = 109;
    public static final int SFILE_INFO_KEY_UNFIXED = 110;
    public static final int SFILE_INFO_FILETIME = 111;
    public static final int SFILE_INFO_PATCH_CHAIN = 112;
    
    public static final String LISTFILE_NAME = (String) "(listfile)";
    public static final String PATCH_METADATA_NAME = (String) "(patch_metadata)";
    public static final String SIGNATURE_NAME = (String) "(signature)";
    public static final String ATTRIBUTES_NAME = (String) "(attributes)";
    
    public static final int MPQ_FORMAT_VERSION_1 = 0;
    public static final int MPQ_FORMAT_VERSION_2 = 1;
    public static final int MPQ_FORMAT_VERSION_3 = 2;
    public static final int MPQ_FORMAT_VERSION_4 = 3;
    
    // Flags for MPQ attributes
    public static final int MPQ_ATTRIBUTE_CRC32 = 0x00000001;
    public static final int MPQ_ATTRIBUTE_FILETIME = 0x00000002;
    public static final int MPQ_ATTRIBUTE_MD5 = 0x00000004;
    public static final int MPQ_ATTRIBUTE_PATCH_BIT = 0x00000008;
    public static final int MPQ_ATTRIBUTE_ALL = 0x0000000F;
    
    public static final int MPQ_ATTRIBUTES_V1 = 100;
    
    // Flags for SFileOpenArchive
    public static final int BASE_PROVIDER_FILE = 0x00000000;
    public static final int BASE_PROVIDER_MAP = 0x00000001;
    public static final int BASE_PROVIDER_HTTP = 0x00000002;
    public static final int BASE_PROVIDER_MASK = 0x0000000F;
    
    public static final int STREAM_PROVIDER_LINEAR = 0x00000000;
    public static final int STREAM_PROVIDER_PARTIAL = 0x00000010;
    public static final int STREAM_PROVIDER_ENCRYPTED = 0x00000020;
    public static final int STREAM_PROVIDER_MASK = 0x000000F0;
    
    public static final int STREAM_FLAG_READ_ONLY = 0x00000100;
    public static final int STREAM_FLAG_WRITE_SHARE = 0x00000200;
    public static final int STREAM_FLAG_MASK = 0x0000FF00;
    public static final int STREAM_OPTIONS_MASK = 0x0000FFFF;
    
    public static final int MPQ_OPEN_NO_LISTFILE = 0x00010000;
    public static final int MPQ_OPEN_NO_ATTRIBUTES = 0x00020000;
    public static final int MPQ_OPEN_FORCE_MPQ_V1 = 0x00040000;
    public static final int MPQ_OPEN_CHECK_SECTOR_CRC = 0x00080000;
    
    //Deprecated
    public static final int MPQ_OPEN_READ_ONLY = STREAM_FLAG_READ_ONLY;
    public static final int MPQ_OPEN_ENCRYPTED = STREAM_PROVIDER_ENCRYPTED;
    
    // Flags for SFileCreateArchive
    public static final int MPQ_CREATE_ATTRIBUTES = 0x00100000;
    public static final int MPQ_CREATE_ARCHIVE_V1 = 0x00000000;
    public static final int MPQ_CREATE_ARCHIVE_V2 = 0x01000000;
    public static final int MPQ_CREATE_ARCHIVE_V3 = 0x02000000;
    public static final int MPQ_CREATE_ARCHIVE_V4 = 0x03000000;
    public static final int MPQ_CREATE_ARCHIVE_VMASK = 0x0F000000;
    
    public static final int FLAGS_TO_FORMAT_SHIFT = 24;
    
    // Flags for SFileVerifyFile
    public static final int SFILE_VERIFY_SECTOR_CRC = 0x00000001;
    public static final int SFILE_VERIFY_FILE_CRC = 0x00000002;
    public static final int SFILE_VERIFY_FILE_MD5 = 0x00000004;
    public static final int SFILE_VERIFY_RAW_MD5 = 0x00000008;
    public static final int SFILE_VERIFY_ALL = 0x0000000F;
    
    // Return values for SFileVerifyFile
    public static final int VERIFY_OPEN_ERROR = 0x0001;
    public static final int VERIFY_READ_ERROR = 0x0002;
    public static final int VERIFY_FILE_HAS_SECTOR_CRC = 0x0004;
    public static final int VERIFY_FILE_SECTOR_CRC_ERROR = 0x0008;
    public static final int VERIFY_FILE_HAS_CHECKSUM = 0x0010;
    public static final int VERIFY_FILE_CHECKSUM_ERROR = 0x0020;
    public static final int VERIFY_FILE_HAS_MD5 = 0x0040;
    public static final int VERIFY_FILE_MD5_ERROR = 0x0080;
    public static final int VERIFY_FILE_HAS_RAW_MD5 = 0x0100;
    public static final int VERIFY_FILE_RAW_MD5_ERROR = 0x0200;
    public static final int VERIFY_FILE_ERROR_MASK = (VERIFY_OPEN_ERROR | VERIFY_READ_ERROR | VERIFY_FILE_SECTOR_CRC_ERROR | VERIFY_FILE_HAS_CHECKSUM | VERIFY_FILE_MD5_ERROR | VERIFY_FILE_RAW_MD5_ERROR);
    
    // Flags for SFileVerifyRawData (for MPQs version 4.0 or higher)
    public static final int SFILE_VERIFY_MPQ_HEADER = 0x0001;
    public static final int SFILE_VERIFY_HET_TABLE = 0x0002;
    public static final int SFILE_VERIFY_BET_TABLE = 0x0003;
    public static final int SFILE_VERIFY_HASH_TABLE = 0x0004;
    public static final int SFILE_VERIFY_BLOCK_TABLE = 0x0005;
    public static final int SFILE_VERIFY_HIBLOCK_TABLE = 0x0006;
    public static final int SFILE_VERIFY_FILE = 0x0007;
    
    // Return values for SFileVerifyArchive
    public static final int ERROR_NO_SIGNATURE = 0;
    public static final int ERROR_VERIFY_FAILED = 1;
    public static final int ERROR_WEAK_SIGNATURE_OK = 2;
    public static final int ERROR_WEAK_SIGNATURE_ERROR = 3;
    public static final int ERROR_STRONG_SIGNATURE_OK = 4;
    public static final int ERROR_STRONG_SIGNATURE_ERROR = 5;
    
    public static final int MD5_DIGEST_SIZE = 0x10;
    public static final int SHA1_DIGEST_SIZE = 0x14;
    public static final int LANG_NEUTRAL = 0;
    
    // Values for compact callback
    public static final int CCB_CHECKING_FILES = 1;
    public static final int CCB_CHECKING_HASH_TABLE = 2;
    public static final int CCB_COPYING_NON_MPQ_DATA = 3;
    public static final int CCB_COMPACTING_FILES = 4;
    public static final int CCB_CLOSING_ARCHIVE = 5;
    
    public static final int MPQ_HEADER_SIZE_V1 = 0x20;
    public static final int MPQ_HEADER_SIZE_V2 = 0x2C;
    public static final int MPQ_HEADER_SIZE_V3 = 0x44;
    public static final int MPQ_HEADER_SIZE_V4 = 0xD0;
    
    public static final int SIZE_OF_XFRM_HEADER = 12;

    /**
     * Opens a MPQ archive. During the open operation,
     * the archive is checked for corruptions, internal (listfile) and (attributes)
     * are loaded, unless specified otherwise. The archive is open for read and write
     * operations, unless {@link #MPQ_OPEN_READ_ONLY} is specified.
     * Note that StormLib maintains list of all files within the MPQ, as long as the MPQ is open.
     * At the moment of MPQ opening, when the MPQ contains an internal list file,
     * that listfile is parsed and all files in the listfile are checked against
     * the hash table. Every file name that exists within the MPQ is added to the
     * internal name list. The name list can be fuhrter extended by calling
     * {@link #SFileAddListFile}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileopenarchive.html">http://www.zezula.net/en/mpq/stormlib/sfileopenarchive.html</a>
     * 
     * @param szMpqName Archive file name to open
     * @param dwPriority Priority of the archive for later search. StormLib does not use this parameter, set it to zero.
     * @param dwFlags Flags that specify additional options about how to open the file.
     * @param phMpq  Pointer to a variable of HANDLE type, where the opened archive handle will be stored
     * @return When the function succeeds, it returns nonzero and phMPQ contains the handle of the opened archive. When the archive cannot be open, function returns false and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileOpenArchive(String szMpqName, int dwPriority, int dwFlags, HANDLEByReference phMpq);

    /**
     * Opens or creates the MPQ archive. The function can also convert an existing 
     * file to MPQ archive. The MPQ archive is always open for write operations.
     * The function internally verifies the file using {@link #SFileOpenArchive}. 
     * If the file already exists and it is an MPQ archive, the function fails and 
     * {@link com.sun.jna.platform.win32.Kernel32#GetLastError()} returns ERROR_ALREADY_EXISTS.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilecreatearchive.html">http://www.zezula.net/en/mpq/stormlib/sfilecreatearchive.html</a>
     * 
     * @param szMpqName Archive file name to be created
     * @param dwFlags Specifies additional flags for MPQ creation process
     * @param dwMaxFileCount File count limit. Must be in range of HASH_TABLE_SIZE_MIN (0x04) and HASH_TABLE_SIZE_MAX (0x80000)
     * @param phMpq Pointer to a variable of HANDLE type, where the opened archive handle will be stored.
     * @return When the function succeeds, it returns nonzero and phMPQ contains the handle of the new archive. On an error, the function returns false and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileCreateArchive(String szMpqName, int dwFlags, int dwMaxFileCount, HANDLEByReference phMpq);

    /**
     * Saves any in-memory structures to the MPQ archive on disk. Due to performance 
     * reasons, StormLib caches several data structures in memory (e.g. block table or hash table). 
     * When a file is added to the MPQ, those structures are only updated in memory. 
     * Calling SFileFlushArchive forces saving in-memory MPQ tables to the file, 
     * preventing a MPQ corruption incase of power down or crash of the calling application.
     * Note that this function is called internally when the archive is closed.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileflusharchive.html">http://www.zezula.net/en/mpq/stormlib/sfileflusharchive.html</a>
     * 
     * @param hMpq Handle to an open MPQ.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns false and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileFlushArchive(HANDLE hMpq);

    /**
     * Closes the MPQ archive. All in-memory data are freed and also any unsaved 
     * MPQ tables are saved to the archive. After this function finishes, the 
     * hMpq handle is no longer valid and may not be used in any MPQ operations.
     * 
     * Note that this function calls {@link #SFileFlushArchive} internally.
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileclosearchive.html">http://www.zezula.net/en/mpq/stormlib/sfileclosearchive.html</a>
     * 
     * @param hMpq Handle to an open MPQ.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns false and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileCloseArchive(HANDLE hMpq);

    /**
     * StormLib maintains list of all files within the MPQ, as long as the MPQ is open. 
     * At the moment of MPQ opening, when the MPQ contains an internal list file, 
     * that listfile is parsed and all files in the listfile are checked against 
     * the hash table. Every file name that exists within the MPQ is added to the 
     * internal name list. This is done by calling SFileAddListFile internally.
     * SFileAddListFile adds an external listfile to the open MPQ. Note that the 
     * listfile is merely added to the memory structures of the open MPQ. On-disk 
     * structures of the MPQ are not changed. Use this function to specify an extra 
     * listfile to an opened MPQ, for example when there is no internal listfile, 
     * or if the internal listfile is not complete.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileaddlistfile.html">http://www.zezula.net/en/mpq/stormlib/sfileaddlistfile.html</h>
     * 
     * @param hMpq Handle to an open MPQ.
     * @param szListFile Listfile name to add. If this parameter is NULL, the function adds the internal listfile from the MPQ, if present. Adding the same listfile multiple times has no effect.
     * @return When the function succeeds, it returns ERROR_SUCCESS. On an error, the function returns error code. 
     */
    int SFileAddListFile(HANDLE hMpq, String szListFile);

    /**
     * Sets a callback that will be called during operations performed by {@link #SFileCompactArchive}. 
     * Registering a callback will help the calling application to show a progress 
     * about the operation, which will enhance user experience with the application.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilesetcompactcallback.html">http://www.zezula.net/en/mpq/stormlib/sfilesetcompactcallback.html</a>
     * 
     * @param hMpq Handle to the MPQ that will be compacted. Current version of StormLib ignores the parameter, but it is recommended to set it to the handle of the archive.
     * @param CompactCB Pointer to the callback function. For the prototype and parameters, see below.
     * @param pvData User defined data that will be passed to the callback function.
     * @return The function never fails and always sets the callback. 
     */
    byte SFileSetCompactCallback(HANDLE hMpq, SFILE_COMPACT_CALLBACK CompactCB, Pointer pvData);

    /**
     * Performs a complete archive rebuild, effectively defragmenting the MPQ archive, 
     * removing all gaps that have been created by adding, replacing, renaming or 
     * deleting files within the archive. To succeed, the function requires all files 
     * in MPQ archive to be accessible. See Remarks section for more information. 
     * SFileCompactArchive might take several minutes to complete, depending on size 
     * of the archive being rebuilt. If you want to use SFileCompactArchive in your 
     * application, you can utilize a compact callback, which can be set by {@link #SFileSetCompactCallback}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilecompactarchive.html">http://www.zezula.net/en/mpq/stormlib/sfilecompactarchive.html</a>
     * 
     * @param hMpq Handle to an open MPQ. The MPQ must have been open by {@link #SFileOpenArchive} or created by {@link #SFileCreateArchive}.
     * @param szListFile Allows to specify an additional listfile, that will be used together with internal listfile. Can be null.
     * @param bReserved Not used, set to zero.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns false and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileCompactArchive(HANDLE hMpq, String szListFile, byte bReserved);
    
    /**
     * Returns locale that is set as a preferred locale for files that will be 
     * open by {@link #SFileOpenFileEx} and added by {@link #SFileAddFileEx}. 
     * The locale is stored as a global variable and thus affects every open or 
     * add operation.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilegetlocale.html">http://www.zezula.net/en/mpq/stormlib/sfilegetlocale.html</a>
     * 
     * @return The function never fails and always returns current locale ID.
     */
    LCID SFileGetLocale();
    
    /**
     * Sets a preferred locale for file functions, such as {@link #SFileOpenFileEx} 
     * or {@link #SFileAddFileEx}. The locale is stored as a global variable and 
     * thus affects every open or add operation. Note that this function does not 
     * change locale ID of any existing file in the MPQ.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilesetlocale.html">http://www.zezula.net/en/mpq/stormlib/sfilesetlocale.html</a>
     * 
     * @param locale Locale ID to be set.
     * @return The function never fails and always returns locale.
     */
    LCID SFileSetLocale(LCID locale);
    
    /**
     * Changes the limit for number of files that can be stored in the archive. 
     * No files are changed during this operation.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilesetmaxfilecount.html">http://www.zezula.net/en/mpq/stormlib/sfilesetmaxfilecount.html</a>
     * 
     * @param hMpq Handle to an open archive. This handle must have been obtained by {@link #SFileOpenArchive} or {@link #SFileCreateArchive}.
     * @param dwMaxFileCount New size of the hash table. This parameter must be in range of {@link #HASH_TABLE_SIZE_MIN} and {@link #HASH_TABLE_SIZE_MAX} 
     * @return When the function succeeds, it returns nonzero. On an error, the function returns false and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileSetMaxFileCount(HANDLE hMpq, int dwMaxFileCount);
    
    /**
     * Adds a patch archive to the existing open MPQ. The MPQ must have been open 
     * by {@link #SFileOpenArchive}, and also with {@link #MPQ_OPEN_READ_ONLY} specified. 
     * The patch archive is added to the list of patches that belong to the primary 
     * MPQ. No handle is returned, and the patch(es) is closed when the primary 
     * MPQ handle is closed. The patch MPQ opened during the process is maintained 
     * internally by StormLib and cannot be accessed directly.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileopenpatcharchive.html">http://www.zezula.net/en/mpq/stormlib/sfileopenpatcharchive.html</a>
     * 
     * @param hMpq Handle to a MPQ that serves as primary MPQ when patched.
     * @param szPatchMpqName Name of the patch MPQ to be added.
     * @param szPatchPathPrefix Pointer to patch prefix for file names. This parameter can be null, which makes StormLib to determine patch prefix for the specific combination of names of the base MPQ and patch MPQ.
     * @param dwFlags Reserved for future use.
     * @return When the function succeeds, it returns nonzero. When the archive cannot be added as patch archive, function returns false and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileOpenPatchArchive(HANDLE hMpq, String szPatchMpqName, String szPatchPathPrefix, int dwFlags);

    /**
     * Returns 1, if the given MPQ has one or more patches added.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileispatcharchive.html">http://www.zezula.net/en/mpq/stormlib/sfileispatcharchive.html</a>
     * 
     * @param hMpq Handle to a MPQ in question.
     * @return The function returns 1, when there is at least one patch added to the MPQ. Otherwise, it returns 0.
     */
    byte SFileIsPatchedArchive(HANDLE hMpq);

    /**
     * Opens a file from MPQ archive. The file is only open for read. The file 
     * must be closed by calling {@link #SFileCloseFile}. All files must be closed 
     * before the MPQ archive is closed.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileopenfileex.html">http://www.zezula.net/en/mpq/stormlib/sfileopenfileex.html</a>
     * 
     * @param hMpq Handle to an open archive.
     * @param szFileName Name or index of the file to open.
     * @param dwSearchScope Value that specifies how exactly the file should be open.
     * @param phFile Pointer to a variable of HANDLE type, that will receive HANDLE to the open file.
     * @return When the function succeeds, it returns nonzero and phFile contains the handle of the opened file. When the file cannot be open, function returns false and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileOpenFileEx(HANDLE hMpq, String szFileName, int dwSearchScope, HANDLEByReference phFile);

    /**
     * Retrieves the size of an open file.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilegetfilesize.html">http://www.zezula.net/en/mpq/stormlib/sfilegetfilesize.html</a>
     * 
     * @param hFile Handle to an open file. The file handle must have been created by {@link #SFileOpenFileEx}.
     * @param pdwFileSizeHigh Receives high 32 bits of the a file size. This parameter can be null.
     * @return When the function succeeds, it returns lower 32-bit of the file size. On an error, it returns {@link #SFILE_INVALID_SIZE} and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} returns an error code.
     */
    int SFileGetFileSize(HANDLE hFile, IntByReference pdwFileSizeHigh);

    /**
     * Sets current position in an open file.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilesetfilepointer.html">http://www.zezula.net/en/mpq/stormlib/sfilesetfilepointer.html</a>
     * 
     * @param hFile Handle to an open file. The file handle must have been created by {@link #SFileOpenFileEx}.
     * @param lFilePos Low 32 bits of new position in the file.
     * @param plFilePosHigh Pointer to a high 32 bits of new position in the file.
     * @param dwMoveMethod The starting point for the file pointer move.
     * @return When the function succeeds, it returns lower 32-bit of the file size. On an error, it returns {@link #SFILE_INVALID_SIZE} and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} returns an error code.
     */
    int SFileSetFilePointer(HANDLE hFile, NativeLong lFilePos, NativeLongByReference plFilePosHigh, int dwMoveMethod);

    /**
     * Reads data from an open file.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilereadfile.html">http://www.zezula.net/en/mpq/stormlib/sfilereadfile.html</a>
     * 
     * @param hFile Handle to an open file. The file handle must have been created by {@link #SFileOpenFileEx}.
     * @param lpBuffer Pointer to buffer that will receive loaded data. The buffer size must be greater or equal to dwToRead.
     * @param dwToRead Number of bytes to be read.
     * @param pdwRead Pointer to DWORD that will receive number of bytes read.
     * @param lpOverlapped If hFile is handle to a local disk file, lpOverlapped is passed to ReadFile. Otherwise not used.
     * @return <li>When all requested bytes have been read, the function returns 1.</li>
     *         <li>When less than requested bytes have been read, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} returns ERROR_HANDLE_EOF.</li>
     *         <li>If an error occured, the function returns 0 and GetLastError returns an error code different from ERROR_HANDLE_EOF.</li>
     */
    byte SFileReadFile(HANDLE hFile, Pointer lpBuffer, int dwToRead, IntByReference pdwRead, LPOVERLAPPED lpOverlapped);

    /**
     * Closes an open MPQ file. All in-memory data are freed. After this function 
     * finishes, the hFile handle is no longer valid and must not be used in any 
     * file operations.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileclosefile.html">http://www.zezula.net/en/mpq/stormlib/sfileclosefile.html</a>
     * 
     * @param hFile Handle to an open file.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns false and {@link com.sun.jna.platform.win32.Kernel32#GetLastError}  gives the error code.
     */
    byte SFileCloseFile(HANDLE hFile);

    /**
     * Performs a quick check if a file exists within the MPQ archive. The function 
     * does not perform file open, not even internally. It merely checks hash table 
     * if the file is present.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilehasfile.html">http://www.zezula.net/en/mpq/stormlib/sfilehasfile.html</a>
     * 
     * @param hMpq Handle to an open MPQ.
     * @param szFileName Name of the file to check.
     * @return <li>When all requested bytes have been read, the function returns 1.</li>
     *         <li>When less than requested bytes have been read, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} returns ERROR_FILE_NOT_FOUND.</li>
     *         <li>If an error occured, the function returns 0 and GetLastError returns an error code different from ERROR_FILE_NOT_FOUND.</li>
     */
    byte SFileHasFile(HANDLE hMpq, String szFileName);

    /**
     * Retrieves the name of an open file.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilegetfilename.html">http://www.zezula.net/en/mpq/stormlib/sfilegetfilename.html</a>
     * 
     * @param hFile Handle to an open file. The file handle must have been created by {@link #SFileOpenFileEx}.
     * @param szFileName Receives the file name. The buffer must be at least {@link com.sun.jna.platform.win32.Kernel32#MAX_PATH} characters long.
     * @return When the function succeeds, it returns true and buffer pointed by szFileName contains name of the file. On an error, the function returns false and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} returns an error code.
     */
    byte SFileGetFileName(HANDLE hFile, ByteBuffer szFileName);

    /**
     * Retrieves an information about an open MPQ archive or a file.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilegetfileinfo.html">http://www.zezula.net/en/mpq/stormlib/sfilegetfileinfo.html</a>
     * 
     * @param hMpqOrFile Handle to an open file or to an open MPQ archive, depending on the value of dwInfoType.
     * @param dwInfoType Type of information to retrieve. See Return Value for more information.
     * @param pvFileInfo Pointer to buffer where to store the required information.
     * @param cbFileInfo Size of the buffer pointed by pvFileInfo.
     * @param pcbLengthNeeded Size, in bytes, needed to store the information into pvFileInfo.
     * @return When the function succeeds, it returns 1. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} returns error code. Possible error codes may be ERROR_INVALID_PARAMETER (unknown file info type) or ERROR_INSUFFICIENT_BUFFER (not enough space in the supplied buffer).
     */
    byte SFileGetFileInfo(HANDLE hMpqOrFile, int dwInfoType, Pointer pvFileInfo, int cbFileInfo, IntByReference pcbLengthNeeded);

    /**
     * Verifies the file by its CRC and MD5. The (attributes) file must exist in 
     * the MPQ and must have been open by {@link #SFileOpenArchive} or created by 
     * {@link #SFileCreateArchive}. The entire file is always checked for readability. 
     * Additional flags in dwFlags turn on extra checks on the file.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileverifyfile.html">http://www.zezula.net/en/mpq/stormlib/sfileverifyfile.html</a>
     * 
     * @param hMpq Handle to an open MPQ archive.
     * @param szFileName Name of a file to verify. 
     * @param dwFlags Specifies what to verify.
     * @return Return value is zero when no problerms were found. Otherwise returns
     * a bitmask (see more in the link)
     */
    int SFileVerifyFile(HANDLE hMpq, String szFileName, int dwFlags);
    
    /**
     * Verifies digital signature of the archive, is a digital signature is present. 
     * in the MPQ and must have been open by {@link #SFileOpenArchive}.
     * 
     * Note that MPQ archives created by StormLib are never signed.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileverifyarchive.html">http://www.zezula.net/en/mpq/stormlib/sfileverifyarchive.html</a>
     * 
     * @param hMpq Handle to an open MPQ archive to be verified.
     * @return 
     */
    int SFileVerifyArchive(HANDLE hMpq);
    
    
    /**
     * Extracts one file from an MPQ archive.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileextractfile.html">http://www.zezula.net/en/mpq/stormlib/sfileextractfile.html</a>
     * 
     * @param hMpq Handle to an open MPQ archive.
     * @param szToExtract Name of a file within the MPQ that is to be extracted.
     * @param szExtracted Specifies the name of a local file that will be created and will contain data from the extracted MPQ file.
     * @param dwSearchScope This parameter refines the definition of what to extract. If you want ot extract an unpatched file, use {@link #SFILE_OPEN_FROM_MPQ} (this is the default parameter). If you want to extract patched version of the file, use {@link #SFILE_OPEN_PATCHED_FILE}.
     * @return If the MPQ file has been successfully extracted into the target file, the function returns 1. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} returns an error code.
     */
    byte SFileExtractFile(HANDLE hMpq, String szToExtract, String szExtracted, int dwSearchScope);

    /**
     * Searches an MPQ archive and returns name of the first file that matches the 
     * given search mask and exists in the MPQ archive. When the caller finishes 
     * searching, the returned handle must be freed by calling {@link #SFileFindClose}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilefindfirstfile.html">http://www.zezula.net/en/mpq/stormlib/sfilefindfirstfile.html</a>
     * 
     * @param hMpq Handle to an open archive.
     * @param szMask Name of the search mask. "*" will return all files.
     * @param lpFindFileData Pointer to {@link SFILE_FIND_DATA} structure that will receive information about the found file.
     * @param szListFile Name of an extra list file that will be used for searching. Note that {@link #SFileAddListFile} is called internally. The internal listfile in the MPQ is always used (if exists). This parameter can be null.
     * @return When the function succeeds, it returns handle to the MPQ search object and the {@link SFILE_FIND_DATA} structure is filled with information about the file. On an error, the function returns null and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    HANDLE SFileFindFirstFile(HANDLE hMpq, String szMask, SFILE_FIND_DATA lpFindFileData, String szListFile);

    /**
     * Continues search that has been initiated by {@link #SFileFindFirstFile}. 
     * When the caller finishes searching, the returned handle must be freed by 
     * calling {@link #SFileFindClose}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilefindnextfile.html">http://www.zezula.net/en/mpq/stormlib/sfilefindnextfile.html</a>
     * 
     * @param hFind Search handle. Must have been obtained by call to {@link #SFileFindFirstFile}.
     * @param lpFindFileData Pointer to {@link SFILE_FIND_DATA} structure that will receive information about the found file. For layout of the structure, see {@link #SFileFindFirstFile}.
     * @return When the function succeeds, it returns nonzero and the {@link SFILE_FIND_DATA} structure is filled with information about the file. On an error, the function returns zero and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileFindNextFile(HANDLE hFind, SFILE_FIND_DATA lpFindFileData);

    /**
     * Closes a find handle that has been created by {@link #SFileFindFirstFile}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilefindclose.html">http://www.zezula.net/en/mpq/stormlib/sfilefindclose.html</a>
     * 
     * @param hFind Search handle. Must have been obtained by call to {@link #SFileFindFirstFile}.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileFindClose(HANDLE hFind);

    /**
     * Searches a listfile and returns name of the first file that matches the 
     * given search mask. When the caller finishes searching, the returned handle 
     * must be freed by calling {@link #SListFileFindClose}. Note that unlike 
     * {@link #SFileFindFirstFile}, this function does not check if the file exists 
     * within the archive and doesn't call {@link #SFileAddListFile}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/slistfilefindfirstfile.html">http://www.zezula.net/en/mpq/stormlib/slistfilefindfirstfile.html</a>
     * 
     * @param hMpq Handle to an open archive. This parameter must only be valid if szListFile is null.
     * @param szListFile Name of the listfile that will be used for searching. If this parameter is null, the function searches the MPQ internal listfile (if any).
     * @param szMask Name of the search mask. "*" will return all files.
     * @param lpFindFileData Pointer to {@link #SFILE_FIND_DATA} structure that will receive name of the found file. For layout of this structure, see {@link #SFileFindFirstFile}.
     * @return When the function succeeds, it returns handle to the MPQ search object and the cFileName member of {@link #SFILE_FIND_DATA} structure is filled with name of the file. On an error, the function returns NULL and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    HANDLE SListFileFindFirstFile(HANDLE hMpq, String szListFile, String szMask, SFILE_FIND_DATA lpFindFileData);

    /**
     * Continues listfile searching initiated by {@link #SListFileFindFirstFile}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/slistfilefindnextfile.html">http://www.zezula.net/en/mpq/stormlib/slistfilefindnextfile.html</a>
     * 
     * @param hFind Search handle. Must have been obtained by call to {@link #SListFileFindFirstFile}.
     * @param lpFindFileData Pointer to {@link SFILE_FIND_DATA} structure that will receive name of the found file. For layout of the structure, see {@link #SFileFindFirstFile}.
     * @return When the function succeeds, it returns nonzero and the cFileName member of {@link SFILE_FIND_DATA} structure is filled with name of the file. On an error, the function returns zero and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SListFileFindNextFile(HANDLE hFind, SFILE_FIND_DATA lpFindFileData);

    /**
     * Closes a find handle that has been created by {@link #SListFileFindFirstFile}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/slistfilefindclose.html">http://www.zezula.net/en/mpq/stormlib/slistfilefindclose.html</a>
     * 
     * @param hFind Search handle. Must have been obtained by call to {@link #SListFileFindFirstFile}.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SListFileFindClose(HANDLE hFind);

    /**
     * Enumerates all locales for the given file that are present in the MPQ.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileenumlocales.html">http://www.zezula.net/en/mpq/stormlib/sfileenumlocales.html</a>
     * 
     * @param hMpq Handle to a MPQ.
     * @param szFileName Name of a file to enumerate the locales.
     * @param plcLocales An array of LCIDs that will receive locales. This parameter can be null if pdwMaxLocales points to zero.
     * @param pdwMaxLocales On input, this argument must point to a variable that contains maximum number of entries in plcLocales array. On output, this variable receives number of locales that are for the file. This argument cannot be NULL.
     * @param dwSearchScope This parameter is ignored.
     * @return When the function succeeds, it returns ERROR_SUCCESS. On an error, the function returns an error code.
     */
    int SFileEnumLocales(HANDLE hMpq, String szFileName, Pointer plcLocales, IntByReference pdwMaxLocales, int dwSearchScope);

    /**
     * Creates a new file within archive and prepares it for storing the data.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilecreatefile.html">http://www.zezula.net/en/mpq/stormlib/sfilecreatefile.html</a>
     * 
     * @param hMpq Handle to an open MPQ. This handle must have been obtained by calling {@link #SFileOpenArchive} or {@link #SFileCreateArchive}.
     * @param szArchivedName A name under which the file will be stored into the MPQ.
     * @param FileTime Specifies the file date-time that will be stored into "(attributes)" file in MPQ. This parameter is optional and can be zero.
     * @param dwFileSize Specifies the size of the data that will be written to the file. This size of the file is set by the call and cannot be changed. The subsequent amount of data written must exactly match the size given by this parameter.
     * @param lcLocale Specifies the locale for the new file.
     * @param dwFlags Specifies additional options about how to add the file to the MPQ. For more information about these flags, see {@link #SFileAddFileEx}.
     * @param phFile Pointer to a variable of HANDLE type that receives a valid handle. Note that this handle can only be used in call to {@link #SFileWriteFile} and {@link #SFileFinishFile}. This handle must never be passed to another file function. Moreover, this handle must always be freed by {@link #SFileFinishFile}, if not null.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileCreateFile(HANDLE hMpq, String szArchivedName, long FileTime, int dwFileSize, LCID lcLocale, int dwFlags, HANDLEByReference phFile);

    /**
     * Writes data to the archive. The file must have been created by {@link #SFileCreateFile}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilewritefile.html">http://www.zezula.net/en/mpq/stormlib/sfilewritefile.html</a>
     * 
     * @param hFile Handle to a new file within MPQ. This handle must have been obtained by calling {@link #SFileCreateFile}.
     * @param pvData Pointer to data to be written to the file.
     * @param dwSize Size of the data that are to be written to the MPQ.
     * @param dwCompression Specifies the type of data compression that is to be applied to the data, in case the amount of the data will reach size of one file sector. For more information about the available compressions, see {@link #SFileAddFileEx}.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileWriteFile(HANDLE hFile, Pointer pvData, int dwSize, int dwCompression);

    /**
     * Finalized creation of the archived file. The file must have been created by {@link #SFileCreateFile}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilefinishfile.html">http://www.zezula.net/en/mpq/stormlib/sfilefinishfile.html</a>
     * 
     * @param hFile Handle to a new file within MPQ. This handle must have been obtained by calling {@link #SFileCreateFile}.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileFinishFile(HANDLE hFile);

    /**
     * Adds a file to the MPQ archive. The MPQ must have been open by {@link #SFileOpenArchive}
     * or created by {@link #SFileCreateArchive}. Note that this operation might 
     * cause MPQ fragmentation. To reduce size of the MPQ, use {@link #SFileCompactArchive}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileaddfileex.html">http://www.zezula.net/en/mpq/stormlib/sfileaddfileex.html</a>
     * 
     * @param hMpq Handle to an open MPQ. This handle must have been obtained by calling {@link #SFileOpenArchive} or {@link #SFileCreateArchive}.
     * @param szFileName Name of a file to be added to the MPQ.
     * @param szArchivedName A name under which the file will be stored into the MPQ. This does not have to be the same like the original file name.
     * @param dwFlags Specifies additional options about how to add the file to the MPQ. 
     * @param dwCompression Compression method of the first file block. This parameter is ignored if {@link #MPQ_FILE_COMPRESS} is not specified in dwFlags. 
     * @param dwCompressionNext Compression method of rest of the file. This parameter optional and is ignored if {@link #MPQ_FILE_COMPRESS} is not specified in dwFlags.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileAddFileEx(HANDLE hMpq, String szFileName, String szArchivedName, int dwFlags, int dwCompression, int dwCompressionNext);
    
    /**
     * Removes a file from MPQ. The MPQ must have been open by {@link #SFileOpenArchive} 
     * or created by {@link #SFileCreateArchive}. Note that this operation leaves a gap 
     * in the MPQ file. To reduce size of the MPQ, use {@link #SFileCompactArchive}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfileremovefile.html">http://www.zezula.net/en/mpq/stormlib/sfileremovefile.html</a>
     * 
     * @param hMpq Handle to an open MPQ. This handle must have been obtained by calling {@link #SFileOpenArchive} or {@link #SFileCreateArchive}.
     * @param szFileName Name of a file to be removed.
     * @param dwSearchScope This parameter is ignored in the current version of StormLib.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code.
     */
    byte SFileRemoveFile(HANDLE hMpq, String szFileName, int dwSearchScope);

    /**
     * Renames a file within MPQ. The MPQ must have been open by {@link #SFileOpenArchive} 
     * or created by {@link #SFileCreateArchive}. Note that this operation does 
     * not cause MPQ fragmentation and thus it is not necessary to compact the archive.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilerenamefile.html">http://www.zezula.net/en/mpq/stormlib/sfilerenamefile.html</a>
     * 
     * @param hMpq Handle to an open MPQ. This handle must have been obtained by calling {@link #SFileOpenArchive} or {@link #SFileCreateArchive}.
     * @param szOldFileName Name of a file to be renamed.
     * @param szNewFileName New name of the file.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code. 
     */
    byte SFileRenameFile(HANDLE hMpq, String szOldFileName, String szNewFileName);

    /**
     * Sets new locale ID for an open file. The locale ID is changed in the block 
     * table of the MPQ. The MPQ must have been open by {@link #SFileOpenArchive} 
     * or created by {@link #SFileCreateArchive}. Note that this operation does 
     * not cause MPQ fragmentation and thus it is not necessary to compact the archive.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilesetfilelocale.html">http://www.zezula.net/en/mpq/stormlib/sfilesetfilelocale.html</a>
     * 
     * @param hFile Handle to the file in the MPQ. This handle must have been obtained by calling {@link #SFileOpenFileEx}.
     * @param lcNewLocale New locale ID for the file. For more onformation about locales, see {@link #SFileSetLocale}.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code. 
     */
    byte SFileSetFileLocale(HANDLE hFile, LCID lcNewLocale);

    /**
     * Configures compression mask for subsequent calls to {@link #SFileAddFileEx}. 
     * The compression mask is remembered until changed.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilesetdatacompression.html">http://www.zezula.net/en/mpq/stormlib/sfilesetdatacompression.html</a>
     * 
     * @param DataCompression Bit mask of data compression.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code. 
     */
    byte SFileSetDataCompression(int DataCompression);

    /**
     * Sets a callback that will be called during operations performed by {@link #SFileAddFileEx}. 
     * Registering a callback will help the calling application to show a progress 
     * about the operation, which enhances user experience with the application.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/sfilesetaddfilecallback.html">http://www.zezula.net/en/mpq/stormlib/sfilesetaddfilecallback.html</a>
     * 
     * @param hMpq Handle to the MPQ that will be compacted. Current version of StormLib ignores the parameter, but it is recommended to set it to the handle of the archive.
     * @param AddFileCB Pointer to the callback function. For the prototype and parameters, see {@link SFILE_ADDFILE_CALLBACK}.
     * @param pvData User defined data that will be passed to the callback function.
     * @return The function never fails and always sets the callback.
     */
    byte SFileSetAddFileCallback(HANDLE hMpq, SFILE_ADDFILE_CALLBACK AddFileCB, Pointer pvData);

    /**
     * Compresses a data buffer, using Pkware Data Compression library's IMPLODE method.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/scompimplode.html">http://www.zezula.net/en/mpq/stormlib/scompimplode.html</a>
     * 
     * @param pvOutBuffer Pointer to buffer where the compressed data will be stored.
     * @param pcbOutBuffer On call, pointer to the length of the buffer in pbOutBuffer. When finished, this variable receives length of the compressed data.
     * @param pvInBuffer Pointer to data that are to be imploded.
     * @param cbInBuffer Length of the data pointed by pbInBuffer.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code. 
     */
    int SCompImplode(Pointer pvOutBuffer, IntBuffer pcbOutBuffer, Pointer pvInBuffer, int cbInBuffer);

    /**
     * Decompresses a data block compressed by {@link #SCompImplode}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/scompexplode.html">http://www.zezula.net/en/mpq/stormlib/scompexplode.html</a>
     * 
     * @param pvOutBuffer Pointer to buffer where the decompressed data will be stored.
     * @param pcbOutBuffer On call, pointer to the length of the buffer in pbOutBuffer. When finished, this variable receives length of the decompressed data.
     * @param pvInBuffer Pointer to data that are to be exploded.
     * @param cbInBuffer Length of the data pointed by pbInBuffer.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code. 
     */
    int SCompExplode(Pointer pvOutBuffer, IntBuffer pcbOutBuffer, Pointer pvInBuffer, int cbInBuffer);

    /**
     * Compresses a data buffer, using various compression methods.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/scompcompress.html">http://www.zezula.net/en/mpq/stormlib/scompcompress.html</a>
     * 
     * @param pvOutBuffer Pointer to buffer where the compressed data will be stored.
     * @param pcbOutBuffer On call, pointer to the length of the buffer in pbOutBuffer. When finished, this variable receives length of the compressed data.
     * @param pvInBuffer Pointer to data that are to be imploded.
     * @param cbInBuffer Length of the data pointed by pbInBuffer.
     * @param uCompressionMask Bit mask that specifies compression methods to use. For possible values of this parameter, see {@link #SFileAddFileEx}.
     * @param nCmpType An extra parameter, specific to compression type. This parameter is only used internally by Huffmann compression when applied after an ADPCM compression.
     * @param nCmpLevel An extra parameter, specific to compression type. This parameter is used by ADPCM compression and is related to WAVE quality. See Remarks section for additional information.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code. 
     */
    int SCompCompress(Pointer pvOutBuffer, IntBuffer pcbOutBuffer, Pointer pvInBuffer, int cbInBuffer, int uCompressionMask, int nCmpType, int nCmpLevel);

    /**
     * Decompresses a data block compressed by {@link #SCompCompress}.
     * 
     * @see <a href="http://www.zezula.net/en/mpq/stormlib/scompdecompress.html">http://www.zezula.net/en/mpq/stormlib/scompdecompress.html</a>
     * 
     * @param pvOutBuffer Pointer to buffer where the decompressed data will be stored.
     * @param pcbOutBuffer On call, pointer to the length of the buffer in pbOutBuffer. When finished, this variable receives length of the decompressed data.
     * @param pvInBuffer Pointer to data that are to be exploded.
     * @param cbInBuffer Length of the data pointed by pbInBuffer.
     * @return When the function succeeds, it returns nonzero. On an error, the function returns 0 and {@link com.sun.jna.platform.win32.Kernel32#GetLastError} gives the error code. 
     */
    int SCompDecompress(Pointer pvOutBuffer, IntBuffer pcbOutBuffer, Pointer pvInBuffer, int cbInBuffer);
    

    public static class LPOVERLAPPED extends PointerType {

        public LPOVERLAPPED(Pointer address) {
            super(address);
        }

        public LPOVERLAPPED() {
            super();
        }
    }

    public static class TFileStream extends PointerType {

        public TFileStream(Pointer address) {
            super(address);
        }

        public TFileStream() {
            super();
        }
    }
    
    public static class LCID extends PointerType {
        
        public LCID(Pointer address){
            super(address);
        }
        
        public LCID(){
            super();
        }
    }
}
