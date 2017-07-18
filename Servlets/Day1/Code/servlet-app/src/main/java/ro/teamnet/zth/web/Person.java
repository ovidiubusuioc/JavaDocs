package ro.teamnet.zth.web;

/**
 * Created by Ovidiu.Busuioc on 7/18/2017.
 */
public class Person {
    String firstName, lastName;
    Long age;
    Boolean married;


    public Person(String firstName, String lastName, Long age, Boolean married) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.married = married;

    }
}
