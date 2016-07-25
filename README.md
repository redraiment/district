# District

面向中国大陆地区的 手机号码、电话号码、IP地址 归属地查询库。

* IP地址库来源于 [ip2region](http://git.oschina.net/lionsoul/ip2region)。
* 手机号码段和电话号码区号信息来源于 [ip138](http://ip138.com/)。

为了能及时获得最新的IP地址，该项目以 `SNAPSHOT` 形式发布到 Maven 中央库。

# Maven(pom.xml)

```xml
<project>
    ...
   <repositories>
        <repository>
            <id>zzp-mvn-repo</id>
            <url>http://10.0.40.218</url>
        </repository>
    </repositories>
    ...
    <dependencies>
        <dependency>
            <groupId>me.zzp</groupId>
            <artifactId>district</artifactId>
            <version>1.1.0</version>
        </dependency>
    </dependencies>
</project>
```

# 使用

```java
import me.zzp.district.DistrictHelper;

public class Main {
    public static void main(String[] args) {
        // 手机号码归属地
        System.out.println(DistrictHelper.ofTelNumber("13656630000"));
        // 电话号码归属地
        System.out.println(DistrictHelper.ofTelNumber("05711234567"));
        // IP地址归属地
        System.out.println(DistrictHelper.ofIp("192.168.11.1"));
    }
}
```
