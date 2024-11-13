package ro.upb.quantum.kme.exception;

public class NoKeysException extends KmeException {

    public NoKeysException(int requestedNo, int poolSize, String masterSaeId, String slaveSaeId) {
        super(String.format(
                "requested %d no of keys, but only %d remains for sae master %s and sae slave %s",
                requestedNo, poolSize, masterSaeId, slaveSaeId));
    }
}
