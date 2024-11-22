package ro.upb.quantum.kme.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ro.upb.quantum.kme.exception.KmeException;
import ro.upb.quantum.kme.services.KeyManager;
import ro.upb.quantum.model.ErrorMessage;
import ro.upb.quantum.model.KeyContainer;
import ro.upb.quantum.model.KmeStatus;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/keys")
@Slf4j
public class KeyManagement {

    private final KeyManager keyManager;

    public KeyManagement(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    @GetMapping("/{slaveSaeId}/status")
    public KmeStatus getStatus(@PathVariable String slaveSaeId, Principal principal) {
        String saeId = ((UserDetails) ((Authentication) principal).getPrincipal()).getUsername();
        log.info("status request from {} for slave {}", saeId, slaveSaeId);
        return keyManager.getStatus(saeId, slaveSaeId);
    }

    @GetMapping("/{slaveSaeId}/enc_keys")
    public KeyContainer getEncKeys(@PathVariable String slaveSaeId, Principal principal) {
        String saeId = ((UserDetails) ((Authentication) principal).getPrincipal()).getUsername();
        log.info("get key request from {} for slave {}", saeId, slaveSaeId);
        return keyManager.getKeys(saeId, slaveSaeId, 20);
    }

    @GetMapping("/{masterSaeId}/dec_keys")
    public KeyContainer getDecKeys(@PathVariable String masterSaeId, Principal principal) {
        String saeId = ((UserDetails) ((Authentication) principal).getPrincipal()).getUsername();
        log.info("get key request from {} for master {}", saeId, masterSaeId);
        return keyManager.getKeys(masterSaeId, saeId, 20);
    }

    @ExceptionHandler(KmeException.class)
    public ResponseEntity<ErrorMessage> kmeExceptionHandler(KmeException ex) {
        log.error("errors in simulator", ex);
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
