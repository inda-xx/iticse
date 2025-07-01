package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LapDataTest {

    @Test
    public void parseValidLineReturnsExpectedValues() throws CorruptLapException {
        String line = "1, soft, 95.5, 4.2, 0";
        LapData lap = LapData.parse(line);
        assertEquals(1, lap.getLapNumber());
        assertEquals("soft", lap.getCompound());
        assertEquals(95.5, lap.getTemperature(), 0.0001);
        assertEquals(4.2, lap.getDegradation(), 0.0001);
        assertEquals(0, lap.getStintId());
    }

    @Test
    public void parseIgnoresExtraWhitespace() throws CorruptLapException {
        String line = "  12 ,  hard  , 100 ,  7.0 ,  1  ";
        LapData lap = LapData.parse(line);
        assertEquals(12, lap.getLapNumber());
        assertEquals("hard", lap.getCompound());
    }

    @Test(expected = CorruptLapException.class)
    public void parseThrowsWhenFieldsMissing() throws CorruptLapException {
        LapData.parse("1,soft,90.0,5.0"); // missing stintId
    }

    @Test(expected = CorruptLapException.class)
    public void parseThrowsWhenNumberFormatInvalid() throws CorruptLapException {
        LapData.parse("NaN,soft,90.0,5.0,0");
    }

    @Test(expected = CorruptLapException.class)
    public void parseThrowsWhenNegativeTemperature() throws CorruptLapException {
        LapData.parse("1,soft,-5,5.0,0");
    }

    @Test(expected = CorruptLapException.class)
    public void parseThrowsWhenDegradationOutOfRange() throws CorruptLapException {
        LapData.parse("1,soft,95,101,0");
    }
}


// TireTest.java
import org.junit.Test;

import static org.junit.Assert.*;

