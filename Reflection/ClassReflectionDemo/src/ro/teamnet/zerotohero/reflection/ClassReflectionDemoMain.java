package ro.teamnet.zerotohero.reflection;

import javafx.scene.shape.QuadCurve;
import ro.teamnet.zerotohero.reflection.demoobjects.Dogs;
import ro.teamnet.zerotohero.reflection.demoobjects.Pets;
import ro.teamnet.zerotohero.reflection.demoobjects.obj;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * TODO
 * in order to resolve the exercises below, you will have to create
 * all the needed clasees, with their members and methods
 * (in ro.teamnet.zerotohero.reflection.demoobjects package)
 */
public class ClassReflectionDemoMain {

    private static Integer a;
    public Field field;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //TODO get the class for a String object, and print it
        System.out.println("Ovidiu".getClass());

        //TODO get the class of an Enum, and print it
        //obj object = new obj();
        //System.out.println(object.getClass());

        //TODO get the class of a collection, and print it
        ArrayList<String> lista = new ArrayList();
        System.out.println(lista.getClass());

        //TODO get the class of a primitive type, and print it
        int a = 4;
        System.out.println(((Object) a).getClass());

        //TODO get and print the class for a field of primitive type
        System.out.println(((Object) a).getClass().getField("SIZE"));

        //TODO get and print the class for a primitive type, using the wrapper class
        System.out.println(((Object) a).getClass().getTypeName());

        //TODO get the class for a specified class name
        Pets pet = new Pets(5);
        System.out.println(pet.getClass());

        //TODO get the superclass of a class, and print it
        Dogs dog = new Dogs(12, "black");
        System.out.println(dog.getClass().getSuperclass());
        //TODO get the superclass of the superclass above, and print it
        System.out.println(dog.getClass().getSuperclass().getSuperclass());
        

        //TODO get and print the declared classes within some other class
        System.out.println(dog.getClass().getDeclaredClasses());
        

        //TODO print the number of constructors of a class
        System.out.println(dog.getClass().getConstructors().length);

        //TODO get and invoke a public constructor of a class
        Constructor con = dog.getClass().getConstructors()[0];
        con.newInstance();

        //TODO get and print the class of one private field 

        System.out.println(dog.getClass().getFields().toString());

        //TODO set and print the value of one private field for an object
        System.out.println();

        //TODO get and print only the public fields class
        

        //TODO get and invoke one public method of a class
        

        //TODO get and invoke one inherited method of a class
       
		
		//TODO invoke a method of a class the classic way for ten times, and print the timestamp (System.currentTimeMillis())
		//TODO invoke a method of a class by Reflection for 100 times, and print the timestamp
		//what do you observe?
		
    }
}
