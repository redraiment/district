package me.zzp.district;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU缓存
 *
 * @author zhangzepeng
 */
final class Cache extends LinkedHashMap<String, String> {

    private int capacity;

    Cache() {
        this(1024);
    }

    Cache(int capacity) {
        super((capacity >= 0? capacity: 0) + 8, 1.0f);
        this.capacity = capacity;
    }

    int getCapacity() {
        return capacity;
    }

    void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
        return size() > capacity;
    }
}
