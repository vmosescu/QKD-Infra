package ro.upb.quantum.sae.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.upb.quantum.model.KeyContainer;
import ro.upb.quantum.model.KmeStatus;
import ro.upb.quantum.sae.service.KeyManager;

@RestController
public class KeyManagement {

    private final KeyManager keyManager;

    public KeyManagement(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    @GetMapping("/status")
    public KmeStatus getStatus() {
        return keyManager.getStatus();
    }

    @GetMapping("/enc")
    public KeyContainer getEncKeys() {
        return keyManager.getEncKeys();
    }

    @GetMapping("/dec")
    public KeyContainer getDecKeys() {
        return keyManager.getDecKeys();
    }
}
