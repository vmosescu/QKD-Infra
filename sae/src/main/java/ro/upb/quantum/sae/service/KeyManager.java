package ro.upb.quantum.sae.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ro.upb.quantum.model.KeyContainer;
import ro.upb.quantum.model.KmeStatus;

@Service
@Slf4j
public class KeyManager {
    private final RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:8080/api/v1/keys/")
            .defaultHeader("SAE-ID", "SAE-B")
            .build();

    public KmeStatus getStatus() {
        KmeStatus kmeStatus = restClient.get()
                .uri("{slaveSaeId}/status", "SAE-C")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(KmeStatus.class);
        log.info("KME Status: {}", kmeStatus);
        return kmeStatus;
    }

    public KeyContainer getEncKeys() {
        KeyContainer keyContainer = restClient.get()
                .uri("{slaveSaeId}/enc_keys", "SAE-C")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(KeyContainer.class);
        log.info("enc keys for SAE-C: {}", keyContainer);
        return keyContainer;
    }

    public KeyContainer getDecKeys() {
        KeyContainer keyContainer = restClient.get()
                .uri("{masterSaeId}/dec_keys", "SAE-A")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(KeyContainer.class);
        log.info("dec keys for SAE-A: {}", keyContainer);
        return keyContainer;
    }
}
