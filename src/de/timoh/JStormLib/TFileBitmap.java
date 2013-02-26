package de.timoh.JStormLib;

import com.sun.jna.Structure;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Timo
 */
public class TFileBitmap extends Structure{
    
    public int StartOffset;                      // Starting offset of the file, covered by bitmap
    public int EndOffset;                        // Ending offset of the file, covered by bitmap
    public int IsComplete;                           // If nonzero, no blocks are missing
    public int BitmapSize;                           // Size of the file bitmap (in bytes)
    public int BlockSize;                            // Size of one block, in bytes
    public int Reserved;                             // Alignment
    
    @Override
    protected List getFieldOrder() {
        return new ArrayList(Arrays.asList(new String[]{"StartOffset", "EndOffset", "IsComplete", "BitmapSize", "BlockSize", "Reserved"}));
    }
    
    public static class ByReference extends TFileBitmap implements Structure.ByReference {}

    public static class ByValue extends TFileBitmap implements Structure.ByValue {}
}
