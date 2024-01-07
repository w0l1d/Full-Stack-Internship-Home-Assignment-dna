package ma.dnaengineering.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID AppId;
        @CreatedDate
        private LocalDateTime createdAt;
        @NonNull
        @Min(0)
        private Long id;
        @NotBlank
        private String name;
        @NotBlank
        private String jobTitle;
        @NonNull @Min(0)
        private Double salary;
}
