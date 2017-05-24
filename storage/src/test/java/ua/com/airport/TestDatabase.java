package ua.com.airport;

import java.sql.*;

public class TestDatabase {
//    private static String url = "jdbc:mysql://localhost/";
//    private static String user = "root";
//    private static String password = "illidanf14";

    public static void createTestDB(){
        String URL = "jdbc:mysql://localhost/";
        String Driver = "com.mysql.jdbc.Driver";
        String User = "root";
        String Password = "illidanf14";
//        PreparedStatement ps = null;
//        Connection con = null;
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            String url = "jdbc:mysql://localhost/mysql";
            connection = DriverManager.getConnection(url, "root", "");

            statement = connection.createStatement();
            String testdb = "CREATE DATABASE TestDb";
            statement.executeUpdate(testdb);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                } // nothing we can do
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                } // nothing we can do
            }
        }
//        try {
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            con = DriverManager.getConnection("jdbc:mysql://localhost/testDB", User, Password);
//
//            statement = con.createStatement();
//            String testSQL = "CREATE SCHEMA IF NOT EXISTS TestDB" +
//                    "CREATE TABLE IF NOT EXISTS `testDB`.`Roots` (" +
//                    "  `idRoots` INT NOT NULL AUTO_INCREMENT," +
//                    "  `RootName` VARCHAR(45) NOT NULL," +
//                    "  `Login` VARCHAR(45) NOT NULL," +
//                    "  `Password` VARCHAR(45) NOT NULL," +
//                    "  PRIMARY KEY (`idRoots`)," +
//                    "  UNIQUE INDEX `idRoots_UNIQUE` (`idRoots` ASC)," +
//                    "  UNIQUE INDEX `Login_UNIQUE` (`Login` ASC)," +
//                    "  UNIQUE INDEX `RootName_UNIQUE` (`RootName` ASC))" +
//                    "ENGINE = InnoDB;" +
//                    "CREATE TABLE IF NOT EXISTS `testDB`.`ClassType` (" +
//                    "  `ClassName` VARCHAR(45) NOT NULL," +
//                    "  PRIMARY KEY (`ClassName`))" +
//                    "ENGINE = InnoDB" +
//                    "CREATE TABLE IF NOT EXISTS `testDB`.`Passengers Info` (" +
//                    "  `idPassenger` INT NOT NULL AUTO_INCREMENT," +
//                    "  `FirstName` VARCHAR(45) NOT NULL," +
//                    "  `LastName` VARCHAR(45) NOT NULL," +
//                    "  `Nationality` VARCHAR(45) NOT NULL," +
//                    "  `Passport` VARCHAR(45) NOT NULL," +
//                    "  `Birthday` VARCHAR(45) NOT NULL," +
//                    "  `Sex` VARCHAR(45) NOT NULL," +
//                    "  `ClassType` VARCHAR(45) NOT NULL," +
//                    "  `FlightNum` VARCHAR(45) NOT NULL," +
//                    "  PRIMARY KEY (`idPassenger`)," +
//                    "  UNIQUE INDEX `idPassenger_UNIQUE` (`idPassenger` ASC)," +
//                    "  INDEX `classTypeFK_idx` (`ClassType` ASC)," +
//                    "  CONSTRAINT `classTypeFK`" +
//                    "    FOREIGN KEY (`ClassType`)" +
//                    "    REFERENCES `testDB`.`ClassType` (`ClassName`)" +
//                    "    ON DELETE NO ACTION" +
//                    "    ON UPDATE NO ACTION)" +
//                    "ENGINE = InnoDB" +
//                    "CREATE TABLE IF NOT EXISTS `testDB`.`FlightStatus` (\n" +
//                    "  `StatusName` VARCHAR(45) NOT NULL,\n" +
//                    "  UNIQUE INDEX `StatusName_UNIQUE` (`StatusName` ASC),\n" +
//                    "  PRIMARY KEY (`StatusName`))\n" +
//                    "ENGINE = InnoDB" +
//                    "CREATE TABLE IF NOT EXISTS `testDB`.`Flights` (" +
//                    "  `idFlight` INT NOT NULL AUTO_INCREMENT," +
//                    "  `ArivalTime` VARCHAR(45) NOT NULL," +
//                    "  `DepartureTime` VARCHAR(45) NOT NULL," +
//                    "  `FlightNumber` VARCHAR(45) NOT NULL," +
//                    "  `FlightStatus` VARCHAR(45) NOT NULL," +
//                    "  `Gate` VARCHAR(45) NOT NULL," +
//                    "  `Terminal` VARCHAR(45) NOT NULL," +
//                    "  `City/Port of Departure` VARCHAR(45) NOT NULL," +
//                    "  `City/Port of Arival` VARCHAR(45) NOT NULL," +
//                    "  PRIMARY KEY (`idFlight`)," +
//                    "  UNIQUE INDEX `idFlight_UNIQUE` (`idFlight` ASC)," +
//                    "  INDEX `flightStatusFK_idx` (`FlightStatus` ASC)," +
//                    "  CONSTRAINT `flightStatusFK`" +
//                    "    FOREIGN KEY (`FlightStatus`)" +
//                    "    REFERENCES `testDB`.`FlightStatus` (`StatusName`)" +
//                    "    ON DELETE NO ACTION" +
//                    "    ON UPDATE NO ACTION)" +
//                    "ENGINE = InnoDB" +
//                    "CREATE TABLE IF NOT EXISTS `testDB`.`PriceList` (" +
//                    "  `idPrice` INT NOT NULL AUTO_INCREMENT," +
//                    "  `ClassType` VARCHAR(45) NOT NULL," +
//                    "  `Price` VARCHAR(45) NOT NULL," +
//                    "  `FlightNumber` VARCHAR(45) NOT NULL," +
//                    "  PRIMARY KEY (`idPrice`)," +
//                    "  UNIQUE INDEX `idPrice_UNIQUE` (`idPrice` ASC)," +
//                    "  INDEX `classTypeFK_idx` (`ClassType` ASC)," +
//                    "  CONSTRAINT `classTypeFK`" +
//                    "    FOREIGN KEY (`ClassType`)" +
//                    "    REFERENCES `testDB`.`ClassType` (`ClassName`)" +
//                    "    ON DELETE NO ACTION" +
//                    "    ON UPDATE NO ACTION)" +
//                    "ENGINE = InnoDB";
//            statement.executeUpdate(testSQL);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (statement != null) {
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                } // nothing we can do
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                } // nothing we can do
//            }
//        }
    }


    public static void removeTestDB(){
        Connection connection = null;
        Statement statement = null;
        String User = "root";
        String Password = "illidanf14";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/testDB", User, Password);

            statement = connection.createStatement();
            String testSQL = "DROP SCHEMA TestDB";
            statement.executeUpdate(testSQL);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                } // nothing we can do
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                } // nothing we can do
            }
        }
    }

//    public String getUrl() {
//        return url;
//    }
//
//    public String getUser() {
//        return user;
//    }
//
//    public String getPassword() {
//        return password;
//    }
}
