
package de.timoh.JStormLib.test;

import de.timoh.JStormLib.MPQArchive;
import java.io.File;

/**
 *
 * @author Timo
 */
public class ArchiveTest {
    public static void main(String[] args){
        try {
            File f = new File("test.mpq");
            MPQArchive mpq = MPQArchive.createArchive(f);
            System.out.println(f.getAbsolutePath());
            
            mpq.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
