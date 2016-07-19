package me.zzp.district;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;

/**
 * 归属地辅助类。
 *
 * @author zhangzepeng
 */
public final class DistrictHelper {

    private final static ThreadLocal<Cache> cache;

    private final static Map<String, String> districts;
    private final static Set<String> provinces;
    private final static Set<String> cities;

    private final static Map<String, String> phoneNumbers;
    private final static Map<String, String> diallingCodes;
    private final static IpRange[][] ips;

    static {
        cache = ThreadLocal.withInitial(Cache::new);

        districts = mapOf("province-city");
        provinces = new HashSet<>(districts.values());
        cities = districts.keySet();

        phoneNumbers = mapOf("phone-numbers");
        diallingCodes = mapOf("dialling-code");

        ips = new IpRange[256][];
        for (int index = 0; index < ips.length; index++) {
            ips[index] = new IpRange[0];
        }
        try (Scanner rin = resource("ip")) {
            List<IpRange> ranges = new ArrayList<>();
            while (rin.hasNextLong()) {
                ranges.add(IpRange.of(rin.nextLong(), rin.nextLong(), rin.next()));
            }
            ranges.stream().collect(groupingBy(IpRange::getRoot)).forEach((index, items) -> {
                ips[index] = items.toArray(ips[index]);
            });
        }
    }

    private static Scanner resource(final String name) {
        return new Scanner(DistrictHelper.class.getClassLoader().getResourceAsStream(String.format("me/zzp/district/meta/%s.txt", name)));
    }

    private static Map<String, String> mapOf(final String path) {
        Map<String, String> mapping = new HashMap<>();
        try (Scanner rin = resource(path)) {
            while (rin.hasNext()) {
                mapping.put(rin.next(), rin.next());
            }
        }
        return mapping;
    }

    /**
     * 根据所给的字符串，猜测目标城市。
     *
     * @param district 随机字符串。
     * @return 猜测的目标城市。
     */
    private static String guessWithoutCache(final String district) {
        if (district == null || district.isEmpty()) {
            return "";
        }

        if (district.endsWith("省")) {
            return district.replaceAll("省$", "/");
        }
        String fixed = district;
        if (fixed.endsWith("市")) {
            fixed = fixed.substring(0, district.length() - 1);
        }
        if (districts.containsKey(fixed)) {
            return String.format("%s/%s", districts.get(fixed), fixed);
        }

        Optional<String> optional = cities.stream().filter(district::contains).findFirst();
        if (optional.isPresent()) {
            String city = optional.get();
            return String.format("%s/%s", districts.get(city), city);
        }

        optional = provinces.stream().filter(district::contains).findFirst();
        return optional.isPresent()? optional.get().concat("/"): "";
    }

    public static String guess(final String district) {
        if (district == null || district.isEmpty()) {
            return "";
        }
        if (!cache.get().containsKey(district)) {
            cache.get().put(district, guessWithoutCache(district));
        }
        return cache.get().get(district);
    }

    /**
     * 返回手机/电话号码对应的归属地。
     *
     * @param number 手机/电话号码。
     * @return 归属地。
     */
    public static String ofTelNumber(final String number) {
        if (number == null || districts.isEmpty()) {
            return "";
        }
        if (number.startsWith("0")) {
            if (number.length() >= 3) {
                String prefix = number.substring(0, 3);
                if (diallingCodes.containsKey(prefix)) {
                    return diallingCodes.get(prefix);
                }
            }
            if (number.length() >= 4) {
                String prefix = number.substring(0, 4);
                if (diallingCodes.containsKey(prefix)) {
                    return diallingCodes.get(prefix);
                }
            }
        } else if (number.startsWith("1") && number.length() == 11) {
            String prefix = number.substring(0, 7);
            if (phoneNumbers.containsKey(prefix)) {
                return phoneNumbers.get(prefix);
            }
        }
        return "";
    }

    private static long ipToLong(final String ip) {
        if (ip == null || ip.isEmpty()) {
            return 0;
        }

        String[] values = ip.split("\\.");
        if (values.length != 4) {
            return 0;
        }

        long result = 0;
        for (String value : values) {
            result = result * 256 + Integer.parseInt(value);
        }
        return result;
    }

    /**
     * 返回IP对应的归属地。
     *
     * @param ip IP地址。
     * @return 归属地。
     */
    public static String ofIp(final String ip) {
        IpRange key = IpRange.of(ipToLong(ip));
        int index = Arrays.binarySearch(ips[key.getRoot()], key);
        return index >= 0? ips[key.getRoot()][index].district: "";
    }
}
