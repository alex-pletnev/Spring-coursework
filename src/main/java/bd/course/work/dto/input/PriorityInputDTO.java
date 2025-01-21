package bd.course.work.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PriorityInputDTO {
    /**
     * Identifier of the type.
     * Usually null when creating a new record.
     */
    private Long id;

    @NotNull(message = "Priority value is mandatory")
    @Positive(message = "Priority value must be positive")
    private Double priority;

    @NotBlank(message = "Priority name is mandatory")
    @Size(max = 100, message = "Priority name cannot exceed 100 characters")
    private String name;
}