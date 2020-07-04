package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ticket {

    private List<Traveller> listOfTravellers = new ArrayList<>();

    //returns a free ticket to the traveller who's choices are best matching to the city of Athens
    public Traveller freeTicket(City city) {
        ArrayList<Double> bestSimilarity = new ArrayList<>();

        for (Traveller traveller : listOfTravellers) {
            bestSimilarity.add(traveller.similarity(city));
        }

        Double percentage = Collections.max(bestSimilarity);
        return listOfTravellers.get(bestSimilarity.indexOf(percentage));
    }

    public void setListOfTravellers(List<Traveller> listOfTravellers) {
        this.listOfTravellers = listOfTravellers;
    }
}