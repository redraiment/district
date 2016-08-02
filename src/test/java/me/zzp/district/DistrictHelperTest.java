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

    /*
     * 极少数的名字有包含关系，例如“鞍山”与“马鞍山”
     */
    @Test
    public void testAbbreviation() {
        assertEquals("青海/海南藏族", DistrictHelper.guess("海南藏族"));
        assertEquals("青海/海南藏族", DistrictHelper.guess("青海海南藏族"));
        assertEquals("辽宁/鞍山", DistrictHelper.guess("鞍山"));
        assertEquals("辽宁/鞍山", DistrictHelper.guess("辽宁鞍山"));
        assertEquals("安徽/马鞍山", DistrictHelper.guess("马鞍山"));
        assertEquals("安徽/马鞍山", DistrictHelper.guess("安徽马鞍山"));
        assertEquals("内蒙古/兴安", DistrictHelper.guess("兴安"));
        assertEquals("内蒙古/兴安", DistrictHelper.guess("内蒙兴安"));
        assertEquals("内蒙古/兴安", DistrictHelper.guess("内蒙古兴安"));
        assertEquals("黑龙江/大兴安岭", DistrictHelper.guess("大兴安岭"));
        assertEquals("黑龙江/大兴安岭", DistrictHelper.guess("黑龙江大兴安岭"));
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
        Scanner rin = new Scanner(ClassLoader.getSystemResourceAsStream("test.txt"));
        while (rin.hasNext()) {
            String ip = rin.next();
            String city = rin.next();
            assertEquals(city, DistrictHelper.ofIp(ip));
        }
        rin.close();

        assertEquals("", DistrictHelper.ofIp("0.0.0.0"));
        assertEquals("", DistrictHelper.ofIp("255.255.255.255"));
    }
}
