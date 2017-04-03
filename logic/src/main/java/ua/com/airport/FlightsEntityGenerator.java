package ua.com.airport;

import ua.com.airport.entities.FlightsEntity;
import java.util.*;

public class FlightsEntityGenerator {
    Random random = new Random();
    Map<String, String> flightNumberMap = new HashMap<>();
    List<FlightsEntity> flightList = new ArrayList<>();

    /**
     * Map < departure city + arrival city, DAxxx>
     *     where AA - first letters of  departure and arrival city
     *     xxx - random number
     */
    Map<String, String> priceMap = new HashMap<>();
    int count = 100;

    // TODO  flightStatusGenerator, need to add flight statuses tu switch, and create method for dep and arr time

    public List<FlightsEntity> flightsGenerator(int count) {
        FlightsEntityGenerator generator = new FlightsEntityGenerator();
        FlightsEntity generatedFlightsEntity;
        String flightNumber, departureCity, arrivalCity, gate, terminal, flightStatus, classType;
        Double price;

        List<String> classTypeList = new ArrayList<>();
        classTypeList.add("Business");
        classTypeList.add("Econom");
        for(int i = 0; i < count; i++){
            do {
                departureCity = generator.departureCityGenerator();
                arrivalCity = generator.arrivalCityGenerator();
            } while (departureCity == arrivalCity);
            gate = generator.gateGenerator();
            terminal = generator.terminalGenerator();
            flightStatus = generator.flightStatusGenerator();
            classType = generator.classTypeGenerator(classTypeList);
            flightNumber = generator.flightNumberGenerator(departureCity, arrivalCity);
            price = generator.priceGenerator(flightNumber);
            generatedFlightsEntity = new FlightsEntity("arrTime", "depTime", flightNumber, flightStatus, gate, terminal, departureCity, arrivalCity, classType, price);
            flightList.add(generatedFlightsEntity);
        }
        return flightList;
    }

    public Double priceGenerator(String flightNumber){
        double price;
        char[] flightChar = flightNumber.toCharArray();
        String indexNumber = flightChar[2] + "" + flightChar[3] + "" + flightChar[4];
        if(priceMap.containsKey(indexNumber) == false){
            price = (double)(random.nextInt(4001) + 1000);
            priceMap.put(indexNumber, "" + price);
            return price;
        }
        return Double.parseDouble(priceMap.get(indexNumber));
    }


    public String flightStatusGenerator(){
        int value = random.nextInt(8) + 1;
        switch (value){
            case 1:
                return "";
            case 2:
                return "";
            case 3:
                return "";
            case 4:
                return "";
            case 5:
                return "";
            case 6:
                return "";
            case 7:
                return "";
            case 8:
                return "";
            default:
                return "";
        }
    }

    public String departureCityList(int value){
        switch (value){
            case 1:
                return "Kiev";
            case 2:
                return "Moscow";
            case 3:
                return "London";
            case 4:
                return "Lisbon";
            case 5:
                return "Paris";
            case 6:
                return "Madrid";
            case 7:
                return "Santo Domingo";
            case 8:
                return "Helsinki";
            case 9:
                return "Munich";
            case 10:
                return "Berlin";
            case 11:
                return "Athens";
            case 12:
                return "Rome";
            case 13:
                return "Vienna";
            case 14:
                return "Barcelona ";
            case 15:
                return "Milan";
            case 16:
                return "Prague";
            case 17:
                return "Cologne";
            case 18:
                return "Mexico";
            case 19:
                return "Toronto";
            case 20:
                return "Tbilisi";
            default:
                return "Kiev";
        }
    }

    public String departureCityGenerator(){
        int value = random.nextInt(20) + 1;
        switch (value){
            case 1:
                return "Kiev";
            case 2:
                return "Moscow";
            case 3:
                return "London";
            case 4:
                return "Lisbon";
            case 5:
                return "Paris";
            case 6:
                return "Madrid";
            case 7:
                return "Santo Domingo";
            case 8:
                return "Helsinki";
            case 9:
                return "Munich";
            case 10:
                return "Berlin";
            case 11:
                return "Athens";
            case 12:
                return "Rome";
            case 13:
                return "Vienna";
            case 14:
                return "Barcelona ";
            case 15:
                return "Milan";
            case 16:
                return "Prague";
            case 17:
                return "Cologne";
            case 18:
                return "Mexico";
            case 19:
                return "Toronto";
            case 20:
                return "Tbilisi";
            default:
                return "Kiev";
        }
    }

    public String arrivalCityGenerator(){
        int value = random.nextInt(20) + 1;
        switch (value){
            case 1:
                return "Kiev";
            case 2:
                return "Moscow";
            case 3:
                return "London";
            case 4:
                return "Lisbon";
            case 5:
                return "Paris";
            case 6:
                return "Madrid";
            case 7:
                return "Santo Domingo";
            case 8:
                return "Helsinki";
            case 9:
                return "Munich";
            case 10:
                return "Berlin";
            case 11:
                return "Athens";
            case 12:
                return "Rome";
            case 13:
                return "Vienna";
            case 14:
                return "Barcelona ";
            case 15:
                return "Milan";
            case 16:
                return "Prague";
            case 17:
                return "Cologne";
            case 18:
                return "Mexico";
            case 19:
                return "Toronto";
            case 20:
                return "Tbilisi";
            default:
                return "Paris";
        }
    }

    public String classTypeGenerator(List<String> classType){
        int lenght = classType.size();
        int value = random.nextInt(lenght);
        return classType.get(value);
    }

    public String gateGenerator(){
        int value = random.nextInt(20) + 1;
        switch (value){
            case 1:
                return "№1";
            case 2:
                return "№2";
            case 3:
                return "№3";
            case 4:
                return "№4";
            case 5:
                return "№5";
            case 6:
                return "№6";
            case 7:
                return "№7";
            case 8:
                return "№8";
            case 9:
                return "№9";
            case 10:
                return "№10";
            case 11:
                return "№11";
            case 12:
                return "№12";
            case 13:
                return "№13";
            case 14:
                return "№14";
            case 15:
                return "№15";
            case 16:
                return "№16";
            case 17:
                return "№17";
            case 18:
                return "№18";
            case 19:
                return "№19";
            case 20:
                return "№20";
            default:
                return "№1";
        }
    }

    public String terminalGenerator(){
        int value = random.nextInt(2) + 1;
        switch (value){
            case 1:
                return "T1";
            case 2:
                return "T2";
            default:
                return "T1";
        }
    }

    public String flightNumberGenerator(String depCity, String arrCity){
        String index;
        char[] depCityChar = depCity.toCharArray();
        char[] arrCityChar = arrCity.toCharArray();
        index = depCityChar[0] + "" + arrCityChar[0];
        if (flightNumberMap.containsKey(depCity + arrCity) == false) {
            if (flightNumberMap.containsKey(arrCity + depCity)) {
                char[] fNumChar = flightNumberMap.get(arrCity + depCity).toCharArray();
                String existCount = "";
                for (int i = 2; i < fNumChar.length; i++) {
                    existCount += fNumChar[i];
                }
                flightNumberMap.put(depCity + arrCity, index + existCount);
                return index + existCount;
            } else {
                flightNumberMap.put(depCity + arrCity, index + count);
                count++;
                return index + count;
            }
        } else {
            return flightNumberMap.get(depCity + arrCity);
        }
    }
}
