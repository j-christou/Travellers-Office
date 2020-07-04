package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import weather.OpenWeatherMap;
import wikipedia.MediaWiki;
import java.io.IOException;
import java.net.URL;

public class OpenData {
    private static String appId = "251184c3bcde6e4aba1bf9965192ac9d";
    private static OpenWeatherMap weather_obj;
    private static MediaWiki mediaWiki_obj;

    public static OpenWeatherMap RetrieveWeatherData(String city, String country) throws NullPointerException, InterruptedException {
        Thread threadOne = new Thread() {
            @Override
            public void run() {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    weather_obj = mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + appId), OpenWeatherMap.class);
                    weather_obj = new OpenWeatherMap(weather_obj.getCoord(), weather_obj.getWeather(), weather_obj.getBase(),
                            weather_obj.getMain(), weather_obj.getVisibility(), weather_obj.getWind(), weather_obj.getClouds(),
                            weather_obj.getDt(), weather_obj.getSys(), weather_obj.getTimezone(), weather_obj.getId(),
                            weather_obj.getName(), weather_obj.getCod());
                } catch (IOException | NullPointerException ex) {
                    System.out.println("Failed to retrieve weather data");
                }
            }
        };
        threadOne.start();
        threadOne.join();
        return weather_obj;

    }

    public static String RetrieveWikiData(String city) throws NullPointerException, InterruptedException {
        Thread threadTwo = new Thread() {
            @Override
            public void run() {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mediaWiki_obj = mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + city +
                            "&format=json&formatversion=2"), MediaWiki.class);
                } catch (IOException ex) {
                    System.out.println("Failed to retrieve wikipedia data");
                }
            }
        };
        threadTwo.start();
        threadTwo.join();
        return html2text((mediaWiki_obj.getQuery().getPages().get(0)).getExtract());
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}