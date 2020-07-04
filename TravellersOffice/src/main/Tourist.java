package main;

import java.io.IOException;
import java.util.*;

public class Tourist extends Traveller {

    private String countryOfOrigin;
    private final List<String> questionsList = new ArrayList<>();
    private final Map<String, String> preferences = new LinkedHashMap<>();

    //constructor for tourist that because of inheritance, the tourist inherits all the methods and attributes that belong to the superclass
    public Tourist() {
        super();
        buildQuestionsList();
    }

    //returns the similarity of the user's answers for a city
    public double similarity(City city) throws NullPointerException {
        int totalFindings = 0;
        int countOfWordExistInWikiArticle= 0;

        if (this.preferences.size() == city.getAttributes().size()) {
            Iterator<Map.Entry<String, String>> preferencesIterator = this.preferences.entrySet().iterator();
            Iterator<Map.Entry<String, Integer>> attributesIterator = city.getAttributes().entrySet().iterator();
            while (preferencesIterator.hasNext() && attributesIterator.hasNext()) {
                Map.Entry<String, String> currentPreference = preferencesIterator.next();
                if (currentPreference.getValue().toLowerCase().equals("yes")) {
                    Map.Entry<String, Integer> currentEntry = attributesIterator.next();
                    if (currentEntry.getValue() > 0) {
                        countOfWordExistInWikiArticle++;
                        totalFindings += currentEntry.getValue();
                    }
                } else {
                    attributesIterator.next();
                }
            }
        }
        //returns 0 if the user answers no to all the recommendations
        if(countOfWordExistInWikiArticle == 0) {
            return 0.0;
        }
        return ((double) (countOfWordExistInWikiArticle * totalFindings)/city.getCountTotalWords());
    }

    public City compareCities(List<City> list) {
        for (City city : list) {
            System.out.println();
            System.out.println(city.getName() + " ");
            System.out.printf("%.3f", similarity(city));
            System.out.print("\u0025");
            System.out.println();
        }

        List<Double> listOfValues = new ArrayList<>();
        for (City city : list) {
            listOfValues.add(similarity(city));
        }
        Double percentage = Collections.max(listOfValues);
        int index = listOfValues.indexOf(percentage);
        setVisit(list.get(index).getName());
        return list.get(index);
    }

    @Override
    public void travellerInfo() {
        nameAgeInput();
        System.out.println("Give country of origin: ");
        Scanner input = new Scanner(System.in);
        this.countryOfOrigin = input.nextLine();
        countryOfOrigin = countryOfOrigin.substring(0, 1).toUpperCase() + countryOfOrigin.substring(1).toLowerCase();
    }

    //asks from the user to input his country of origin and then constructor from traveller is called
    @Override
    public void travellerPreferences() {
        System.out.println("Answer by typing yes or no, to the following questions.");
        Scanner input = new Scanner(System.in);
        String answer;
        Iterator<String> questionIterator = this.questionsList.iterator();
        while (questionIterator.hasNext()) {
            String question = questionIterator.next();
            System.out.println(question);
            answer = input.nextLine();
            this.preferences.put(question, answer);
        }
    }

    //updates user choices via traveller's method
    @Override
    public void updateTravellerPreferences() throws IOException {
        this.preferences.clear();
        travellerPreferences();
    }

    private void buildQuestionsList() {
        this.questionsList.add("Are you interested in museums and sightseeing?");
        this.questionsList.add("Are you interested in nightlife?");
        this.questionsList.add("Are you interested in cafes and bars?");
        this.questionsList.add("Are you interested in traditional restaurants?");
        this.questionsList.add("Are you interested in seaside places?");
        this.questionsList.add("Would you like it to be a capital city?");
        this.questionsList.add("Would you prefer easier transportation throughout the city, like metro?");
        this.questionsList.add("Are you interested in music concerts and traditional music?");
        this.questionsList.add("Would you like to be a sports active city?");
    }

    @Override
    public String toString() {
        return "Tourist{" +
                " name = " + getName() +
                ", age = " + getAge() +
                ", countryOfOrigin = " + countryOfOrigin +
                '}';
    }
}