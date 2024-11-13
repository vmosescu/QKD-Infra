package ro.upb.quantum.kme.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ro.upb.quantum.kme.model.KeyManagementEntity;
import ro.upb.quantum.model.Key;

import java.util.*;

/**
 * Key Management Entity Simulator
 * It is initialized with some hardcoded configuration.
 * In next releases configuration should be loaded from config files.
 */
@Service
public class KmeSimulator {

    private final Map<String, KeyManagementEntity> keyManagementEntities = new HashMap<>();

    @PostConstruct
    void init() {
        addKeyManagementEntity("KME-A", "SAE-A");
        addKeyManagementEntity("KME-B", "SAE-B");
        addKeyManagementEntity("KME-C", "SAE-C");
        generateKeys("KME-A", "SAE-A", "KME-B", "SAE-B");
        generateKeys("KME-B", "SAE-B", "KME-C", "SAE-C");
    }

    public Optional<KeyManagementEntity> getKme(String saeId) {
        return keyManagementEntities.values().stream()
                .filter(kme -> kme.getApps().stream().anyMatch(saeId::equals))
                .findFirst();
    }

    private void addKeyManagementEntity(String kmeId, String... saeIds) {
        KeyManagementEntity kme = new KeyManagementEntity(kmeId);
        Arrays.stream(saeIds).toList().forEach(kme::addSae);
        keyManagementEntities.put(kmeId, kme);
    }

    private void generateKeys(String sourceKmeId, String masterSaeId, String targetKmeId, String slaveSaeId) {
        KeyManagementEntity source = keyManagementEntities.get(sourceKmeId);
        KeyManagementEntity target = keyManagementEntities.get(targetKmeId);
        List<Key> keys = source.getQkdEntity().generateKeys(masterSaeId, slaveSaeId);
        target.getQkdEntity().receiveKeys(masterSaeId, slaveSaeId, keys);
    }
}
