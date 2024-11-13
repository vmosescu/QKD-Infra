package ro.upb.quantum.kme.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ro.upb.quantum.kme.exception.NoKeysException;
import ro.upb.quantum.model.Key;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Slf4j
public class KeyPool {

    private static final int KEY_SIZE = 56;
    private static final String ALGORITHM = "DES";

    @Getter
    private final String masterSaeId;
    @Getter
    private final String slaveSaeId;
    private final LinkedList<Key> pool = new LinkedList<>();;
    private final KeyGenerator keyGenerator;

    public KeyPool(String masterSaeId, String slaveSaeId) {
        try {
            keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            log.error("simulator cannot generate secret keys!", e);
            throw new RuntimeException(e);
        }
        this.masterSaeId = masterSaeId;
        this.slaveSaeId = slaveSaeId;
    }

    public int getKeySize() {
        return KEY_SIZE;
    }

    public int size() {
        return pool.size();
    }

    public Key getKey() {
        return getKeys(1).getFirst();
    }

    public List<Key> getKeys(int n) {
        if (n > pool.size()) {
            log.warn("requested {} no of keys, but only {} remains for sae master {} and sae slave {}", n, pool.size(), masterSaeId, slaveSaeId);
            throw new NoKeysException(n, pool.size(), masterSaeId, slaveSaeId);
        }
        List<Key> returnedKeys = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            returnedKeys.add(pool.remove());
        }
        return returnedKeys;
    }

    List<Key> generateKeys(int n) {
        List<Key> keys = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            keys.add(generateKey());
        }
        return keys;
    }

    Key generateKey() {
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] rawData = secretKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(rawData);
        Key key = Key.builder()
                .keyId(UUID.randomUUID().toString())
                .key(encodedKey)
                .build();
        pool.add(key);
        return key;
    }

    void receiveKeys(List<Key> keys) {
        pool.addAll(keys);
    }
}
