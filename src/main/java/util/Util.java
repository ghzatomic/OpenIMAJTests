package util;

import java.io.File;

public class Util {

    public File getClassloaderFile(String file) {
        ClassLoader classLoader = this.getClass().getClassLoader();

        return new File(classLoader.getResource(file).getFile());
    }
}
