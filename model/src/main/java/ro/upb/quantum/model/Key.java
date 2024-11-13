package ro.upb.quantum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
public class Key {
    @JsonProperty("key_ID")
    private String keyId;
    private String key;
}
