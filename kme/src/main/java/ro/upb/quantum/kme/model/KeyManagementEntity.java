package ro.upb.quantum.kme.model;


import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class KeyManagementEntity {

    private final String id;
    private final Set<String> apps = new HashSet<>();
    private final QkdEntity qkdEntity = new QkdEntity();

    public KeyManagementEntity(String id) {
        this.id = id;
    }

    public void addSae(String saeId) {
        apps.add(saeId);
    }
}
