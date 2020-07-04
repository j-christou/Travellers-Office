package main;

import java.util.*;


public class InputHandler {

    private LoadData data = new LoadData();

    public void menu() throws Exception {
        data.loadTravellersFromFile();
        data.loadCitiesFromDatabase();
        getUserInput();
        sortTravellers(data.getListOfTravellers());
        giveFreeTicket();
        data.saveTravellersToFile();
        data.saveCitiesToDatabase();
    }

    private void getUserInput() throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter what type of traveller you are: (type business or tourist)");
        Traveller traveller;

        //read user's speciality and add it to the list with travellers
        String typeOfTraveller = input.nextLine();
        if (typeOfTraveller.toLowerCase().equals("business")) {
            traveller = new Business();
        } else {
            traveller = new Tourist();
        }
        handleTraveller(data.getListOfTravellers(), traveller);

        Iterator<City> citiesIterator = traveller.getCitiesTravellerSearched().iterator();
        while (citiesIterator.hasNext()) {
            checkListOfCities(data.getDataBaseStoredCities(), data.getNewCitiesList(), citiesIterator.next());
        }
    }

    private void giveFreeTicket() {
        List<City> allCities = new ArrayList<>();

        allCities.addAll(data.getDataBaseStoredCities());
        allCities.addAll(data.getNewCitiesList());

        Ticket ticket = new Ticket();
        City randomCityOne = allCities.get(randomIndex(allCities));
        System.out.println("\nWe have a free ticket for " + randomCityOne.getName() + "!");
        ticket.setListOfTravellers(data.getListOfTravellers());
        System.out.println("Free ticket goes to " + ticket.freeTicket(randomCityOne));
    }

    //checks if a traveller already exists
    private boolean checkForSameTraveller(List<Traveller> list, Traveller traveller) {
        Iterator<Traveller> iterator = list.iterator();
        while (iterator.hasNext()) {
            Traveller tr = iterator.next();
            if ((tr.getName().equals(traveller.getName())) && (tr.getAge() == traveller.getAge())) {
                System.out.println("Same traveller is making another search\n");
                return true;
            }
        }
        return false;
    }

    private void sortTravellers(List<Traveller> list) {
        Collections.sort(list);
        Iterator<Traveller> iterator = list.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
    }

    //random number from 0 to the size of the list with cities
    private int randomIndex(List<City> list) {
        Random random = new Random();
        return random.nextInt(list.size());
    }

    private void handleTraveller(List<Traveller> list, Traveller traveller) throws Exception {
        if (checkForSameTraveller(list, traveller)) {
            traveller.updateTravellerPreferences();
            traveller.searchCities();
        } else {
            traveller.travellerPreferences();
            traveller.searchCities();
            list.add(traveller);
        }
    }

    //checks if a city exists in the list with cities
    private void checkListOfCities(List<City> oldList, List<City> newList, City city) {
        boolean exists = false;
        for (City value : oldList) {
            if (value.equals(city)) {
                System.out.println(city.getName() + " city already exists");
                exists = true;
                break;
            }
        }
        if (!exists) {
            newList.add(city);
            System.out.println(city.getName() + " city was added");
        }
    }
}