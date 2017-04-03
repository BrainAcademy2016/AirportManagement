package ua.com.airport.utils;

import ua.com.airport.utils.DateUtils;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.List;

public class DateUtilsTest {

    @Test
    public void testGenerateDayList() throws Exception {
        final int monthCount = 12;
        final int yearCount = 4;
        List<String> testList;
        for(int i = 1; i <= yearCount; i++) {
            for(int j = 1; j <= monthCount; j++) {
                testList = DateUtils.generateDayList(String.valueOf(j), String.valueOf(i));
                if (j == 1 || j == 3 || j == 5 || j == 7 ||
                        j == 8 || j == 10 || j == 12) {
                    assertEquals(31, testList.size());
                    testList.clear();
                } else if (j == 2){
                    if(i % 4 == 0) {
                        assertEquals(29, testList.size());
                        testList.clear();
                    } else {
                        assertEquals(28, testList.size());
                        testList.clear();
                    }
                } else {
                    assertEquals(30, testList.size());
                    testList.clear();
                }
            }
        }
        System.out.println("Test dayList - successful");
    }

    @Test
    public void testGenerateMonthList() throws Exception {
        final int monthCount = 12;
        List<String> testList = DateUtils.generateMonthList();
        for(int count = 1; count <= monthCount; count++){
            if (count < 10) {
                assertEquals("0" + count, testList.get(count - 1));
            } else {
                assertEquals("" + count, testList.get(count - 1));
            }
        }
        System.out.println("Test monthList - successful");
    }

}
