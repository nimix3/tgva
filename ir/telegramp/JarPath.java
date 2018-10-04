
package ir.telegramp;

import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class JarPath {
    public static String GetPath() {
        String absolutePath = JarPath.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
        absolutePath = absolutePath.substring(1, absolutePath.length());
        absolutePath = absolutePath.replace("%20", " ");
        absolutePath = absolutePath.replace("/", "\\");
        return absolutePath;
    }
}

