package ro.upb.quantum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ErrorMessage {
    private String message;
}
