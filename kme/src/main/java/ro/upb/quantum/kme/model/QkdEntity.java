package ro.upb.quantum.kme.model;

import ro.upb.quantum.kme.exception.NoKeysException;
import ro.upb.quantum.model.Key;
import ro.upb.quantum.model.KmeStatus;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class QkdEntity {

    private final static int DEFAULT_NO_OF_KEYS = 100;

    private final List<KeyPool> keyPools = new LinkedList<>();

    public List<Key> generateKeys(String masterSaeId, String slaveSaeId) {
        KeyPool keyPool = new KeyPool(masterSaeId, slaveSaeId);
        List<Key> keys = keyPool.generateKeys(DEFAULT_NO_OF_KEYS);
        keyPools.add(keyPool);
        return keys;
    }

    public void receiveKeys(String masterSaeId, String slaveSaeId, List<Key> keys) {
        KeyPool keyPool = getKeyPool(masterSaeId, slaveSaeId)
                .orElse(new KeyPool(masterSaeId, slaveSaeId));
        keyPool.receiveKeys(keys);
    }

    public List<Key> getKeys(String masterSaeId, String slaveSaeId, int n) {
        KeyPool keyPool = getKeyPool(masterSaeId, slaveSaeId)
                .orElseThrow(() -> new NoKeysException(n, 0, masterSaeId, slaveSaeId));
        return keyPool.getKeys(n);
    }

    public KmeStatus getStatus(String masterSaeId, String slaveSaeId) {
        KeyPool keyPool = getKeyPool(masterSaeId, slaveSaeId)
                .orElse(new KeyPool(masterSaeId, slaveSaeId));
        return KmeStatus.builder()
                .masterSaeId(masterSaeId)
                .slaveSaeId(slaveSaeId)
                .keySize(keyPool.getKeySize())
                .storedKeyCount(keyPool.size())
                .maxKeyCount(1000)
                .maxKeyPerRequest(24)
                .maxKeySize(keyPool.getKeySize())
                .minKeySize(keyPool.getKeySize())
                .build();
    }

    private Optional<KeyPool> getKeyPool(String masterSaeId, String slaveSaeId) {
        return keyPools.stream()
                .filter(pool -> masterSaeId.equals(pool.getMasterSaeId()) && slaveSaeId.equals(pool.getSlaveSaeId()))
                .findFirst();
    }
}
