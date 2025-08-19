import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class SimpleCache<K, V> {
    private final ConcurrentHashMap<K, Entry<V>> cache;
    private final AtomicInteger size;
    private final int maxSize;
    private final long defaultTtl;

    private record Entry<V>(V value, long expiry) {

        private Entry(V value, long expiry) {
            this.value = value;
            this.expiry = Instant.now().plusSeconds(expiry).toEpochMilli();
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expiry;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    public SimpleCache(int maxSize, long defaultTtl) {
        if (maxSize < 1 || defaultTtl < 1) {
            throw new IllegalArgumentException("Invalid params passed");
        }
        this.cache = new ConcurrentHashMap<>();
        this.size = new AtomicInteger(0);
        this.maxSize = maxSize;
        this.defaultTtl = defaultTtl;
    }

    public void put(K key, V value) {
        this.put(key, value, defaultTtl);
    }

    public void put(K key, V value, long defaultTtl) {
        if (Objects.isNull(key) || defaultTtl < 1) {
            throw new IllegalArgumentException("Invalid values passed");
        }
        if (size.get() >= maxSize) {
            evictOldest();
        }
        if (Objects.isNull(cache.put(key, new Entry<>(value, defaultTtl)))) {
            size.incrementAndGet();
        }
    }

    private void evictOldest() {
        long max = Long.MAX_VALUE;
        K oldestKey = null;

        for (Map.Entry<K, Entry<V>> entry : cache.entrySet()) {
            if (entry.getValue().expiry() < max) {
                oldestKey = entry.getKey();
                max = entry.getValue().expiry();
            }
        }

        if (Objects.nonNull(oldestKey)) {
            cache.remove(oldestKey);
            size.decrementAndGet();
        }
    }

    public V get(K key) {
        Entry<V> value = cache.get(key);
        if (Objects.nonNull(value)) {
            if (!value.isExpired()) {
                return value.value();
            }
            cache.remove(key);
            size.decrementAndGet();
        }
        return null;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return cache.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().value())).entrySet();
    }

    @Override
    public String toString() {
        return "SimpleCache{" +
                "cache=" + cache +
                ", size=" + size +
                ", maxSize=" + maxSize +
                ", defaultTtl=" + defaultTtl +
                '}';
    }
}

public class SimpleCaching {
    public static void main(String[] args) {
        final Random random = new Random();
        final SimpleCache<Integer, Integer> simpleCache = new SimpleCache<>(10, 10);
        random.ints(10, 0, 10).parallel().forEach(intVal -> {
            try {
                Thread.sleep(intVal * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            simpleCache.put(intVal, random.nextInt(), random.nextInt(5, 10));
        });
        for (Map.Entry<Integer, Integer> entry : simpleCache.entrySet()) {
            System.out.println(entry);
        }
    }
}