
package com.JStormLib.test;

import com.JStormLib.MPQArchive;
import java.io.File;
import java.util.Arrays;

/**
 *
 * @author Timo
 */
public class ArchiveTest {
    public static void main(String[] args){
        try {
            File f = new File("poc.SC2Map");
            File f2 = new File("test.png");
            MPQArchive mpq = MPQArchive.openArchive(f);
            System.out.println(Arrays.toString(mpq.listFiles().toArray()));
//            mpq.renameFile("test-png","test.png");
            System.out.println(Arrays.toString(mpq.listFiles().toArray()));
            mpq.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
