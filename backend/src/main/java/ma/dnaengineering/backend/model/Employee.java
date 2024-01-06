package ma.dnaengineering.backend.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record Employee(
        @Min(0)
        Long id,
        @NotBlank
        String name,
        @NotBlank
        String jobTitle,
        @NonNull @Min(0)
        Double salary) {
}
