package main;

public class CityNotFoundException extends Exception {

    //an exception is made that returns a particular message to the user
    public CityNotFoundException(String message) {
        super(message);
    }
}