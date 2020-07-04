package main;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoadData {

    protected List<City> newCitiesList;//create a list that contains new city objects
    protected List<City> dataBaseStoredCities;//create a list that contains old city objects, loaded from database
    protected List<Traveller> listOfTravellers;//create a list that contains traveller objects
    private final String url = "jdbc:mysql://127.0.0.1:3306/test?characterEncoding=latin1&useTimezone=true&serverTimezone=UTC";
    private final String username = "root";
    private final String password = "root";

    public LoadData() {
        this.newCitiesList = new ArrayList<>();
        this.dataBaseStoredCities = new ArrayList<>();
        this.listOfTravellers = new ArrayList<>();
    }

    public List<City> getNewCitiesList() {
        return newCitiesList;
    }

    public List<City> getDataBaseStoredCities() {
        return dataBaseStoredCities;
    }

    public List<Traveller> getListOfTravellers() {
        return listOfTravellers;
    }

    public void loadTravellersFromFile() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("travellers.txt")))) {
            boolean EOF = false;
            while(!EOF) {
                try {
                    this.listOfTravellers = (List<Traveller>) objectInputStream.readObject();
                } catch (EOFException e) {
                    EOF = true;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous travellers");
        }

        for (Traveller traveller : listOfTravellers) {
            System.out.println(traveller.toString());
        }
    }

    public void saveTravellersToFile() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("travellers.txt")))) {
            objectOutputStream.writeObject(this.listOfTravellers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCitiesFromDatabase() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(url, username, password);
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from city");
            while(rs.next()) {
                this.dataBaseStoredCities.add(new City(rs.getString("cityName"),
                        rs.getString("countryID"),
                        rs.getString("weatherDesc"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")));
            }
            con.close();
        }catch(Exception e){
            System.out.println("Could not load cities. Database is empty or failed to load cities");
        }

        for (City city : dataBaseStoredCities) {
            System.out.println(city.toString());
        }
    }

    public void saveCitiesToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO city VALUES (?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            Iterator<City> cityIterator = newCitiesList.iterator();

            while(cityIterator.hasNext()) {
                City city = cityIterator.next();
                statement.setString(1, city.getName());
                statement.setString(2, city.getCountryID());
                statement.setString(3, city.getWeather());
                statement.setDouble(4, city.getLatitude());
                statement.setDouble(5, city.getLongitude());
                statement.executeUpdate();
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("Could not save cities to database");
        }
    }
}