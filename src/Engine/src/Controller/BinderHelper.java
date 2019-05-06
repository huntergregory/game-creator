package Engine.src.Controller;

import Engine.src.EngineData.Components.Component;
import groovy.lang.Binding;

import java.io.IOException;

/**
 * Uses ClassGrabber and applies extra functionality to bind component classes to a binding, or to get the component import statements to append to a groovy script.
 * @author Hunter Gregory
 * @author David Liu
 */
public class BinderHelper {

    /**
     * Bind each class' simple name to the actual component's class
     * @param binding
     */
    public void bindComponentClasses(Binding binding) {
        try {
            var classGrabber = new ClassGrabber();
            for (Class componentClass : classGrabber.getClassesForPackage(Component.class.getPackageName()))
                binding.setProperty(componentClass.getSimpleName(), componentClass);
        }
        catch (ClassNotFoundException | IOException e) {
            System.out.println("Couldn't bind component classes in Groovy. Groovy exception is likely to occur.");
        }
    }

    /**
     * @return the import statements for all components
     */
    public String getComponentImportStatements() {
        String result = "";
        try {
            var classGrabber = new ClassGrabber();
            for (Class componentClass : classGrabber.getClassesForPackage(Component.class.getPackageName()))
                result += "import " + componentClass.getName() + ";\n";
        }
        catch (ClassNotFoundException | IOException e) {
            System.out.println("Couldn't bind component classes in Groovy. Groovy exception is likely to occur.");
        }
        return result;
    }
}
