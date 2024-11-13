package ro.upb.quantum.kme.exception;

public class NoSuchApplicationException extends KmeException {

    public NoSuchApplicationException(String applicationId) {
        super("No such application " + applicationId);
    }
}
