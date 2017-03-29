package ua.com.airport;

import ua.com.airport.entities.PassengersEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PassengerEntityGenerator {
    Random  random = new Random();

    public List<PassengersEntity>  passengersGenerator() {
        PassengerEntityGenerator generator = new PassengerEntityGenerator();
        PassengersEntity generatedPassenger;
        List<PassengersEntity> passengersList = new ArrayList<>();
        String sex, firstName, lastName, nationality, passport, birthday, classType, flightNum;
        List<String> classTypeList = new ArrayList<>();
        List<String> flightNumberList = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            sex = generator.sexGenerator();
            firstName = generator.firstNameGenerator(sex);
            lastName = generator.lastNameGenerator(sex);
            nationality = generator.nationalityGenerator();
            passport = generator.passportGenerator();
            birthday = generator.birthdayGenerator();
            classType = generator.classTypeGenerator(classTypeList);
            flightNum = generator.flightNumberGenerator(flightNumberList);

            generatedPassenger = new PassengersEntity(firstName, lastName, nationality, passport, birthday, sex, classType, flightNum);
            passengersList.add(generatedPassenger);
        }

        return passengersList;
    }

    public String firstNameGenerator(String sex) {
        int value = random.nextInt(13) + 1;
        if (sex == "Woman") {
            switch (value) {
                case 1:
                    return  "Mariia";
                case 2:
                    return "Kate";
                case 3:
                    return  "Irina";
                case 4:
                    return "Marina";
                case 5:
                    return  "Olga";
                case 6:
                    return  "Anna";
                case 7:
                    return "Tatiana";
                case 8:
                    return  "Elena";
                case 9:
                    return  "Larisa";
                case 10:
                    return  "Diana";
                case 11:
                    return  "Julia";
                case 12:
                    return  "Alisa";
                case 13:
                    return  "Viktoriia";
                default:
                    return  "Mariia";
            }
        }else if (sex == "Man") {
            switch (value) {
                case 1:
                    return "Vitalii";
                case 2:
                    return  "Oleksii";
                case 3:
                    return  "Andrew";
                case 4:
                    return  "Oleksandr";
                case 5:
                    return  "Vadim";
                case 6:
                    return  "David";
                case 7:
                    return  "Viktor";
                case 8:
                    return  "Roman";
                case 9:
                    return  "Egor";
                case 10:
                    return  "Ivan";
                case 11:
                    return "John";
                case 12:
                    return "Nikolaii";
                case 13:
                    return  "Igor";
                default:
                    return  "Vitalii";
            }
        } else
            System.out.println("Error!");
            return "";
    }

    public String lastNameGenerator(String sex){
        int value = random.nextInt(13) + 1;
        if(sex == "Woman") {
            switch (value){
                case 1:
                    return  "Petrova";
                case 2:
                    return  "Gutnichenko";
                case 3:
                    return  "Karpenko";
                case 4:
                    return  "Dytyna";
                case 5:
                    return  "Sidorska";
                case 6:
                    return  "Ivanchenko";
                case 7:
                    return  "Trofimenko";
                case 8:
                    return  "Semenko";
                case 9:
                    return  "Badzim";
                case 10:
                    return  "Yeroshenko";
                case 11:
                    return  "Bondarenko";
                case 12:
                    return  "Deshko";
                case 13:
                    return  "Kravchenko";
                default:
                    return  "Petrova";
            }
        } else if(sex == "Man") {
            switch (value){
                case 1:
                    return  "Diravka";
                case 2:
                    return  "Chernov";
                case 3:
                    return  "Pastushenko";
                case 4:
                    return  "Slizov";
                case 5:
                    return  "Sheppard";
                case 6:
                    return  "Klimenko";
                case 7:
                    return  "Valiev";
                case 8:
                    return  "Morozov";
                case 9:
                    return  "Trubetskoii";
                case 10:
                    return "Sidorenko";
                case 11:
                    return  "Chernenko";
                case 12:
                    return  "Zaslavskii";
                case 13:
                    return  "Pilipenko";
                default:
                    return  "Klimenko";
            }
        } else {
            System.out.println("Error!");
            return "";
        }
    }

    public String nationalityGenerator(){
        int value = random.nextInt(10) + 1;
        switch (value){
            case 1:
                return "Ukrainian";
            case 2:
                return "Russian";
            case 3:
                return "Spaniard";
            case 4:
                return "Britisher";
            case 5:
                return "Frenchman";
            case 6:
                return "German";
            case 7:
                return "Dane";
            case 8:
                return "Scotsman";
            case 9:
                return  "Swiss";
            case 10:
                return  "Portuguese";
            default:
                return  "Ukrainian";
        }
    }

    /**
     * C1 - char №1, C2 - char №2, N - numbers 0-9
     * @return passport: C1C2NNNNNN
     */
    public String passportGenerator(){
        // 65 - 90
        char c1, c2;
        int n;
        String passport;
        c1 = (char)(random.nextInt(26) + 65);
        c2 = (char)(random.nextInt(26) + 65);
        passport = c1 + "" + c2;
        for(int i = 0; i < 6; i++){
            passport += random.nextInt(10);
        }
        return passport;
    }

    /**
     * dd - day, mm - month, yyyy - year
     *@return String birthday date, format: dd.mm.yyyy
     */
    public String birthdayGenerator(){
        int dd, mm, yyyy;
        String day, month;
        yyyy = random.nextInt(17) + 1980;
        mm = random.nextInt(12) + 1;
        if(mm == 2){
            if(yyyy / 4 == 0){
                dd = random.nextInt(29) + 1;
            } else {
                dd = random.nextInt(28) + 1;
            }
        } else if(mm == 1 || mm == 3 || mm == 5 || mm == 7 || mm == 8 || mm == 10 || mm == 12) {
            dd = random.nextInt(31) + 1;
        } else {
            dd = random.nextInt(30) + 1;
        }
        if(mm < 10){
            dd = random.nextInt(31) + 1;
        } else {
            dd = random.nextInt(30) + 1;
        }
        if (mm < 10) {
            month = "0" + mm;
        } else {
            month = "" + mm;
        }
        if (dd < 10){
            day = "0" + dd;
        } else {
            day = "" + dd;
        }
        return day + "." + month + "." + yyyy;
    }

    public String sexGenerator(){
        int value = random.nextInt(2) + 1;
        switch (value){
            case 1:
                return "Woman";
            case 2:
                return "Man";
            default:
                return "Woman";
        }
    }

    public String classTypeGenerator(List<String> classType){
        int lenght = classType.size();
        int value = random.nextInt(lenght);
        return classType.get(value);
    }

    public String flightNumberGenerator(List<String> flightNumber){
        int lenght = flightNumber.size();
        int value = random.nextInt(lenght);
        return flightNumber.get(value);
    }
}
