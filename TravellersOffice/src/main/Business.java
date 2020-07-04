package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Business extends Traveller {

    private String nameOfCompany;
    private String employeePosition;
    private double currentLatitude;
    private double currentLongitude;

    //constructor of traveller is called because of inheritance and then the user inserts some criteria about his job
    public Business() {
        super();
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    //calculates the similarity of the city for a business with coordination
    public double similarity(City city) throws NullPointerException {
        return 1 - ((DistanceCalculator.distance(getCurrentLatitude(), getCurrentLongitude(), city.getLatitude(),
                city.getLongitude()))/20036);
    }

    //inserts user's job and his position to the
    public void travellerInfo() {
        nameAgeInput();
        Scanner input = new Scanner(System.in);
        System.out.println("Give name of your company: ");
        this.nameOfCompany = input.nextLine();
        System.out.println("State your position at the " + getNameOfCompany() + ": ");
        this.employeePosition = input.nextLine();
        while(isInteger(employeePosition)) {
            System.out.println("Please enter a valid name for your position in a string format: ");
            this.employeePosition = input.nextLine();
        }
    }

    //method from traveller which is being modified for the coordination of the user
    public void travellerPreferences() throws CityNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("In which city are you currently are?");
        String nameOfCity = input.nextLine();
        nameOfCity = nameOfCity.toLowerCase();
        nameOfCity = nameOfCity.substring(0, 1).toUpperCase() + nameOfCity.substring(1);

        System.out.println("Give the countryID of the country you are now: ");
        System.out.println("Example countryID for Greece:gr");
        String countryID = input.nextLine();
        countryID = countryID.toLowerCase();

        City city;

        try {
            city  = new City(nameOfCity, countryID);
        } catch (Exception e) {
            throw new CityNotFoundException("Wrong input cityName or countryID");
        }


        this.currentLatitude = city.getLatitude();
        this.currentLongitude = city.getLongitude();
        System.out.println(city.getName() + "'s latitude " + getCurrentLatitude() + ", longitude " + getCurrentLongitude());
    }

    public void updateTravellerPreferences() throws Exception {
        this.currentLatitude = 0.0;
        this.currentLongitude = 0.0;
        travellerPreferences();
    }

    //returns the city which suits best for the business traveller
    @Override
    public City compareCities(List<City> list) throws NullPointerException {
        List<Double> listOfValues = new ArrayList<>();
        for (City city : list) {
            listOfValues.add(similarity(city));
            System.out.println(similarity(city));
        }

        Double distance = Collections.max(listOfValues);
        return list.get(listOfValues.indexOf(distance));
    }

    @Override
    public String toString() {
        return "Business{" +
                "name = " + getName() +
                ", age = " + getAge() +
                ", nameOfCompany = " + nameOfCompany +
                ", employeePosition = " + employeePosition +
                '}';
    }
}