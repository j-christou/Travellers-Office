package main;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class City implements Serializable {
    private final String name;
    private final String countryID;
    private Map<String, Integer> attributes = new LinkedHashMap();
    private String weather;
    private int countTotalWords;
    private final double latitude;
    private final double longitude;
    private static List<String> list = new ArrayList();

    public City(String name, String countryID) throws NullPointerException, IOException, InterruptedException {
        this.name = name;
        this.countryID = countryID;
        this.weather = Objects.requireNonNull(OpenData.RetrieveWeatherData(name, countryID)).getWeather().get(0).getMain();
        this.latitude = Objects.requireNonNull(OpenData.RetrieveWeatherData(name, countryID)).getCoord().getLat();
        this.longitude = Objects.requireNonNull(OpenData.RetrieveWeatherData(name, countryID)).getCoord().getLon();
        setFields();
    }

    public City(String name, String countryID, String weather, double latitude, double longitude) {
        this.name = name;
        this.countryID = countryID;
        this.weather = weather;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Map<String, Integer> getAttributes() {
        return attributes;
    }

    public String getWeather() {
        return weather;
    }

    public String getCountryID() {
        return countryID;
    }

    public int getCountTotalWords() {
        return countTotalWords;
    }

    public void setFields() throws InterruptedException {
        this.createdWordLibrary();
        String cityArticleInfo = OpenData.RetrieveWikiData(this.name);
        Iterator iterator = list.iterator();

        while(iterator.hasNext()) {
            int timesFound = 0;
            String word = (String)iterator.next();
            Pattern p = Pattern.compile(word);

            for(Matcher m = p.matcher(cityArticleInfo); m.find(); ++timesFound) {
            }

            this.attributes.put(word, timesFound);
        }

        this.countTotalWords = countTotalWords(cityArticleInfo);
    }

    public static int countTotalWords(String str) {
        String[] s =str.split(" ");
        return 	s.length;
    }

    private void createdWordLibrary() {
        list.add("museums");
        list.add("nightlife");
        list.add("bars");
        list.add("traditional food");
        list.add("seaside");
        list.add("capital");
        list.add("transport");
        list.add("concerts");
        list.add("sports");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof City)) {
            return false;
        } else {
            City cityObject = (City)obj;
            return cityObject.getName().equals(this.name);
        }
    }


    public String toString() {
        return "City{name='" + this.name + "', countryID='" + this.countryID  + "', latitude=" + this.latitude +
                ", longitude=" + this.longitude + ", weather='" + this.weather + "'}";
    }
}