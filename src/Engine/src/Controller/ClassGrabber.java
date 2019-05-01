package Engine.src.Controller;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Heavily modified from <a href="https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection">Stack Overflow</a>
 * Gets all classes from the package (and subpackages if the package name doesn't refer to a jar file).
 * @author Hunter Gregory
 */
public class ClassGrabber {
    private static final String CLASS_EXTENSION = ".class";

    private ArrayList<Class> myClasses;

    /**
     * Returns classes inside a package and its subpackages.
     *
     * @param packageName
     * @return array of classes
     * @throws ClassNotFoundException, IOException
     */
    public Class[] getClassesForPackage(String packageName) throws ClassNotFoundException, IOException {
        myClasses = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();

        Enumeration<URL> resources = classLoader.getResources(packageName.replace('.', '/'));
        URLConnection connection;

        while (resources.hasMoreElements()) {
            var url = resources.nextElement();
            connection = url.openConnection();

            if (connection instanceof JarURLConnection)
                addClassesFromJar((JarURLConnection) connection, packageName);
            else
                addClassesFromDirectory(new File(URLDecoder.decode(url.getPath(), "UTF-8")), packageName);
        }

        return myClasses.toArray(new Class[0]);
    }

    private void addClassesFromDirectory(File directory, String packageName) throws ClassNotFoundException {
        if (!directory.exists())
            return;

        String[] files = directory.list();

        for (String file : files) {
            if (file.endsWith(CLASS_EXTENSION)) {
                String className = packageName + '.' + file.substring(0, file.length() - CLASS_EXTENSION.length());
                myClasses.add(Class.forName(className));
            }
            else {
                addClassesFromDirectory(new File(directory, file), packageName + "." + file);
            }
        }
    }

    private void addClassesFromJar(JarURLConnection connection, String packageName) throws ClassNotFoundException, IOException {
        final JarFile jarFile = connection.getJarFile();
        final Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String fileName = jarEntry.getName();
            if (fileName.contains(CLASS_EXTENSION)) {
                fileName = fileName.substring(0, fileName.length() - CLASS_EXTENSION.length()).replace('/', '.');

                if (fileName.contains(packageName)) {
                    myClasses.add(Class.forName(fileName));
                }
            }
        }
    }
}
