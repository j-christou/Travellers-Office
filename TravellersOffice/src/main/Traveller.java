package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public abstract class Traveller implements Comparable<Traveller>, Serializable {

    private static int numberOfTravellers;
    private int age;
    private String name;
    private String visit;
    private List<City> citiesTravellerSearched = new ArrayList<>();

    public Traveller()  {
        numberOfTravellers++;
        travellerInfo();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public static int getNumberOfTravellers() {
        return numberOfTravellers;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public List<City> getCitiesTravellerSearched() {
        return citiesTravellerSearched;
    }

    public abstract double similarity(City city) throws NullPointerException;

    public abstract void travellerPreferences() throws Exception;

    public abstract void updateTravellerPreferences() throws Exception;

    public abstract void travellerInfo();

    public abstract City compareCities(List<City> list);

    public void searchCities() throws CityNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to make a new search for a city?\n Type yes or no: ");
        if (input.nextLine().toLowerCase().equals("yes")) {
            do {
                System.out.println("Please enter the city's name and country's id: ");
                System.out.println("Enter city's name: ");
                String nameOfCity = input.nextLine();
                //makes the first letter capital and the rest lowercase
                nameOfCity = nameOfCity.substring(0, 1).toUpperCase() + nameOfCity.substring(1).toLowerCase();
                System.out.println("Enter country's ID: ");
                String countryID = input.nextLine();
                //makes all letters lowercase
                countryID = countryID.toLowerCase();
                City city;
                try {
                    city  = new City(nameOfCity, countryID);
                } catch (Exception e) {
                    throw new CityNotFoundException("Wrong input cityName or countryID");
                }
                citiesTravellerSearched.add(city);
                System.out.println("Do you want to add another city?\n Type yes or no: ");
            } while (input.nextLine().toLowerCase().equals("yes"));


            System.out.println("Should we consider cities where it rains? \n Type yes or no");

            if (input.nextLine().toLowerCase().equals("yes")) {
                City city = compareCities(citiesTravellerSearched);
                System.out.println("\nAccording to your criteria the best matching city for you is " + city.getName());
            } else {
                City city = compareCities(citiesTravellerSearched, true);
                System.out.println("\nAccording to your criteria the best matching city for you is " + city.getName());
            }
        } else {
            System.out.println("You didn't add any cities to compare with your preferences");
            System.out.println();
        }

    }

    public City compareCities(List<City> list, boolean weather) {
        ArrayList<City> subList = new ArrayList<>();
        for (City city : list) {
            if(!(city.getWeather().equals("Rain")) == weather) {
                subList.add(city);
            }
        }
        return compareCities(subList);
    }

    public void nameAgeInput() {

        Scanner input = new Scanner(System.in);

        System.out.println("Please give your name: ");
        this.name = input.nextLine();
        while(isInteger(name)) {
            System.out.println("Please enter a valid name in a string format: ");
            this.name = input.nextLine();
        }

        System.out.println("Please give your age: ");
        boolean valid = false;
        while (!valid) {
            try {
                this.age = input.nextInt();
                while ((age < 18) || (age > 90)) {
                    System.out.println("Please enter a valid age: ");
                    this.age = input.nextInt();
                }
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid age format\nAge of traveller must be integer: ");
                input.nextLine();
            }
        }
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(Traveller t) {
        return this.age - t.age;
    }

    @Override
    public String toString() {
        return "Traveller{" +
                " name = " + name +
                ", age = " + age +
                ", visit = " + visit +
                '}';
    }
}