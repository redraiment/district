# District

手机、电话、IP归属地 查询服务。

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

    <dependencies>
        <dependency>
            <groupId>me.zzp</groupId>
            <artifactId>district</artifactId>
            <version>1.0.0</version>
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
