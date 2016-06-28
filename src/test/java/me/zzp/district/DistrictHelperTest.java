package me.zzp.district;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * @author zhangzepeng
 */
public class DistrictHelperTest {

    @Test
    public void testGuess() {
        assertEquals("浙江/杭州", DistrictHelper.guess("杭州"));
        assertEquals("浙江/杭州", DistrictHelper.guess("杭州市"));
        assertEquals("浙江/杭州", DistrictHelper.guess("浙江省杭州市"));
        assertEquals("浙江/杭州", DistrictHelper.guess("浙江杭州"));
        assertEquals("浙江/", DistrictHelper.guess("浙江"));
        assertEquals("浙江/", DistrictHelper.guess("浙江省"));
        assertEquals("", DistrictHelper.guess("浙-杭"));
    }

    @Test
    public void testTelNumber() {
        assertEquals("浙江/杭州", DistrictHelper.ofTelNumber("13656630000"));
        assertEquals("浙江/温州", DistrictHelper.ofTelNumber("13819750000"));
        assertEquals("浙江/杭州", DistrictHelper.ofTelNumber("05711234567"));
        assertEquals("浙江/温州", DistrictHelper.ofTelNumber("05771234567"));
    }

    @Test
    public void testIp() {
        try (Scanner rin = new Scanner(ClassLoader.getSystemResourceAsStream("test.txt"))) {
            while (rin.hasNext()) {
                String ip = rin.next();
                String city = rin.next();
                assertEquals(city, DistrictHelper.ofIp(ip));
            }
        }

        assertEquals("", DistrictHelper.ofIp("0.0.0.0"));
        assertEquals("", DistrictHelper.ofIp("255.255.255.255"));
    }
}
