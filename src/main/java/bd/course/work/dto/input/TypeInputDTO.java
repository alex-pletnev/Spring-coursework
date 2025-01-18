package bd.course.work.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TypeInputDTO {
    @NotBlank(message = "Type description is mandatory")
    @Size(max = 255, message = "Type description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Difficulty factor is mandatory")
    @Positive(message = "Difficulty factor must be positive")
    private Double difficultyFactor;
}