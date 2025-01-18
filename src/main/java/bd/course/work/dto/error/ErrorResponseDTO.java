package bd.course.work.dto.error;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDTO {
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
    private String details;

    public ErrorResponseDTO(String message, String errorCode, String details) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }
}