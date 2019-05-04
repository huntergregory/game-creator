package Engine.src.Controller;

import Engine.src.EngineData.Components.Component;
import groovy.lang.Binding;

import java.io.IOException;

public class BinderHelper {

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
