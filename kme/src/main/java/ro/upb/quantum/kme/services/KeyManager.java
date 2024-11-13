package ro.upb.quantum.kme.services;

import org.springframework.stereotype.Service;
import ro.upb.quantum.kme.exception.NoSuchApplicationException;
import ro.upb.quantum.kme.model.KeyManagementEntity;
import ro.upb.quantum.model.Key;
import ro.upb.quantum.model.KeyContainer;
import ro.upb.quantum.model.KmeStatus;

import java.util.List;

@Service
public class KeyManager {

    private final KmeSimulator simulator;

    public KeyManager(KmeSimulator simulator) {
        this.simulator = simulator;
    }


    public KeyContainer getKeys(String masterSaeId, String slaveSaeId, int n) {
        KeyManagementEntity kme = getKme(masterSaeId);
        List<Key> keys = kme.getQkdEntity().getKeys(masterSaeId, slaveSaeId, n);
        return new KeyContainer(keys);
    }

    public KmeStatus getStatus(String masterSaeId, String slaveSaeId) {
        KeyManagementEntity source = getKme(masterSaeId);
        KeyManagementEntity target = getKme(slaveSaeId);
        KmeStatus kmeStatus = source.getQkdEntity().getStatus(masterSaeId, slaveSaeId);
        kmeStatus.setSourceKmeId(source.getId());
        kmeStatus.setTargetKmeId(target.getId());
        return kmeStatus;
    }

    private KeyManagementEntity getKme(String saeId) {
        return simulator.getKme(saeId).orElseThrow(() -> new NoSuchApplicationException(saeId));
    }
}
