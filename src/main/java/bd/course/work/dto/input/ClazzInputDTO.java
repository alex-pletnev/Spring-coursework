package bd.course.work.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClazzInputDTO {
    @NotBlank(message = "Class name is mandatory")
    @Size(max = 100, message = "Class name cannot exceed 100 characters")
    private String className;

    @NotBlank(message = "Ability is mandatory")
    @Size(max = 255, message = "Ability description cannot exceed 255 characters")
    private String ability;
}