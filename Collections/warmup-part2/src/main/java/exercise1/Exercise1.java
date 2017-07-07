package exercise1;

import java.util.*;

/**
 * Created by Radu.Hoaghe on 4/20/2015.
 *
 * Exercise 1: You have a Map<String, String> that holds in each element a country with its capital (countries Map)
 *              a) Find all the countries that start with 'R' character, iterating through the keys of the Map using
 *              keySet() method
 *              b) Find all the countries that that contain 'Q' or 'q' letter, iterating through the keys of the Map using
 *              Map.Entry, and convert all the characters of the countries found to lowercase
 *              c) Find the capital city with the longest name, iterating through the values of the Map (values() method)
 *
 */
public class Exercise1 {

    // A map that holds some key-value pairs, the key represents the country and its value represents
    // the capital city of the country
    private Map<String, String> countries;

    public Exercise1(Map<String, String> countries){
        this.countries = countries;
    }

    public List<String> iteratingOverKeys(){

        // The list of countries that start with the 'R' character
        List<String> seekingCountries = new ArrayList<String>();

        //  Exercise #1 a) You need to iterate over the map keys using a foreach loop (see Map.keySet())

        Set<String> keys = countries.keySet();
        for (String key : keys) {
            if(key.startsWith("R"))
                seekingCountries.add(key);

        }
        //  Exercise #1 a) and add the countries that start with 'R' character into the seekingCountries list
        //  Exercise #1 a) hint: see String documentation

        return seekingCountries;
    }

    public List<String> iteratingOverEntries(){

        // The list of countries that contain the 'Q' character
        List<String> seekingCountries = new ArrayList<String>();

        // TODO Exercise #1 b) You need to iterate over the map entries using a foreach loop (see Map.Entry)
        Set<Map.Entry<String, String>> entry = countries.entrySet();
        for (Map.Entry<String, String> stringStringEntry : entry) {
            if(stringStringEntry.getKey().contains("q")||stringStringEntry.getKey().contains("Q")){
                seekingCountries.add(stringStringEntry.getKey().toLowerCase());
            }
            else seekingCountries.add(stringStringEntry.getKey());

        }



        //  Exercise #1 b) and convert to lowercase (hint: String documentation) all the countries that contain 'Q'
        //  Exercise #1 b) or 'q' letter into the seekingCountries list

        return seekingCountries;
    }

    public String iteratingOverValues(){

        // The capital city with the longest name
        String seekingCapital = "";

        //  Exercise #1 c) You need to iterate over the map values using a foreach loop (see Map.values())
        //  Exercise #1 c) and find the capital city with the longest name
        Collection<String> capitals = countries.values();
        int max=0;
        for (String s : capitals) {
            if (s.length() > max) {
                seekingCapital = s;
                max=s.length();
            }
        }


        return seekingCapital;
    }
}
