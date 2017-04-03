package ua.com.airport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDatabase {
    private static String url = "jdbc:mysql://localhost/testDB";
    private static String user = "tester";
    private static String password = "tester";

    public static void createTestDB(){
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, user, password);

            statement = connection.createStatement();
            String testSQL = "CREATE DATABASE testDB" +
                    "CREATE TABLE IF NOT EXISTS `testDB`.`Roots` (\n" +
                    "  `idRoots` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `RootName` VARCHAR(45) NOT NULL,\n" +
                    "  `Login` VARCHAR(45) NOT NULL,\n" +
                    "  `Password` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`idRoots`),\n" +
                    "  UNIQUE INDEX `idRoots_UNIQUE` (`idRoots` ASC),\n" +
                    "  UNIQUE INDEX `Login_UNIQUE` (`Login` ASC),\n" +
                    "  UNIQUE INDEX `RootName_UNIQUE` (`RootName` ASC))\n" +
                    "ENGINE = InnoDB;\n" +
                    "CREATE TABLE IF NOT EXISTS `testDB`.`ClassType` (\n" +
                    "  `ClassName` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`ClassName`))\n" +
                    "ENGINE = InnoDB;\n" +
                    "CREATE TABLE IF NOT EXISTS `testDB`.`Passengers Info` (\n" +
                    "  `idPassenger` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `FirstName` VARCHAR(45) NOT NULL,\n" +
                    "  `LastName` VARCHAR(45) NOT NULL,\n" +
                    "  `Nationality` VARCHAR(45) NOT NULL,\n" +
                    "  `Passport` VARCHAR(45) NOT NULL,\n" +
                    "  `Birthday` VARCHAR(45) NOT NULL,\n" +
                    "  `Sex` VARCHAR(45) NOT NULL,\n" +
                    "  `ClassType` VARCHAR(45) NOT NULL,\n" +
                    "  `FlightNum` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`idPassenger`),\n" +
                    "  UNIQUE INDEX `idPassenger_UNIQUE` (`idPassenger` ASC),\n" +
                    "  INDEX `classTypeFK_idx` (`ClassType` ASC),\n" +
                    "  CONSTRAINT `classTypeFK`\n" +
                    "    FOREIGN KEY (`ClassType`)\n" +
                    "    REFERENCES `testDB`.`ClassType` (`ClassName`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;" +
                    "CREATE TABLE IF NOT EXISTS `testDB`.`FlightStatus` (\n" +
                    "  `StatusName` VARCHAR(45) NOT NULL,\n" +
                    "  UNIQUE INDEX `StatusName_UNIQUE` (`StatusName` ASC),\n" +
                    "  PRIMARY KEY (`StatusName`))\n" +
                    "ENGINE = InnoDB;" +
                    "CREATE TABLE IF NOT EXISTS `testDB`.`Flights` (\n" +
                    "  `idFlight` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `ArivalTime` VARCHAR(45) NOT NULL,\n" +
                    "  `DepartureTime` VARCHAR(45) NOT NULL,\n" +
                    "  `FlightNumber` VARCHAR(45) NOT NULL,\n" +
                    "  `FlightStatus` VARCHAR(45) NOT NULL,\n" +
                    "  `Gate` VARCHAR(45) NOT NULL,\n" +
                    "  `Terminal` VARCHAR(45) NOT NULL,\n" +
                    "  `City/Port of Departure` VARCHAR(45) NOT NULL,\n" +
                    "  `City/Port of Arival` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`idFlight`),\n" +
                    "  UNIQUE INDEX `idFlight_UNIQUE` (`idFlight` ASC),\n" +
                    "  INDEX `flightStatusFK_idx` (`FlightStatus` ASC),\n" +
                    "  CONSTRAINT `flightStatusFK`\n" +
                    "    FOREIGN KEY (`FlightStatus`)\n" +
                    "    REFERENCES `testDB`.`FlightStatus` (`StatusName`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;\n" +
                    "CREATE TABLE IF NOT EXISTS `testDB`.`PriceList` (\n" +
                    "  `idPrice` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `ClassType` VARCHAR(45) NOT NULL,\n" +
                    "  `Price` VARCHAR(45) NOT NULL,\n" +
                    "  `FlightNumber` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`idPrice`),\n" +
                    "  UNIQUE INDEX `idPrice_UNIQUE` (`idPrice` ASC),\n" +
                    "  INDEX `classTypeFK_idx` (`ClassType` ASC),\n" +
                    "  CONSTRAINT `classTypeFK`\n" +
                    "    FOREIGN KEY (`ClassType`)\n" +
                    "    REFERENCES `testDB`.`ClassType` (`ClassName`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;";
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

    public static void removeTestDB(){
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, user, password);

            statement = connection.createStatement();
            String testSQL = "DROP DATABASE testDB";
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

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
