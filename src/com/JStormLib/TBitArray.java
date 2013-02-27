package com.JStormLib;

import com.sun.jna.Structure;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TBitArray extends Structure {

    public int NumberOfBits;

    public byte[] Elements = new byte[1];
    
    @Override
    protected List getFieldOrder() {
        return new ArrayList(Arrays.asList(new String[]{"NumberOfBits", "Elements"}));
    }

    public static class ByReference extends TBitArray implements Structure.ByReference {}

    public static class ByValue extends TBitArray implements Structure.ByValue {}
}
