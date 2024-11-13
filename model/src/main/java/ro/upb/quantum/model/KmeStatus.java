package ro.upb.quantum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@ToString
public class KmeStatus {
    @JsonProperty("source_KME_ID")
    private String sourceKmeId;
    @JsonProperty("target_KME_ID")
    private String targetKmeId;
    @JsonProperty("master_SAE_ID")
    private String masterSaeId;
    @JsonProperty("slave_SAE_ID")
    private String slaveSaeId;
    @JsonProperty("key_size")
    private int keySize;
    @JsonProperty("stored_key_count")
    private int storedKeyCount;
    @JsonProperty("max_key_count")
    private int maxKeyCount;
    @JsonProperty("max_key_per_request")
    private int maxKeyPerRequest;
    @JsonProperty("max_key_size")
    private int maxKeySize;
    @JsonProperty("min_key_size")
    private int minKeySize;
    @JsonProperty("max_SAE_ID_count")
    private int maxSaeIdCount;
}
