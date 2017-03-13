package ua.com.airport;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import ua.com.airport.daoimpl.PassengersDaoImpl;
import ua.com.airport.entities.PassengersEntity;

public class PassengersImplTest {

    @BeforeClass
    public static void createDB(){
        TestDatabase.createTestDB();
    }

    @AfterClass
    public static void deleteDB(){
        TestDatabase.removeTestDB();
    }

    @Test
    public void addPassengerTest(){
        PassengersDaoImpl passengersDao = new PassengersDaoImpl();
        PassengersEntity testPassenger = new PassengersEntity("Mariia", "Petrova", "Ukranian" , "28.06.1991", "ME821324",
                                                            "Woman", "Business", "LK350");
        passengersDao.addPassenger(testPassenger);
        assertEquals(testPassenger, passengersDao.getPassenger(1));
    }

}
