package ie.ait.ria.springrestbootstrap.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Schema(description = "Class representing a film title in the application.")
@Entity
@Validated
@Data
@NoArgsConstructor(access = PROTECTED)
public class Film {

    @Schema(description = "Unique identifier of the film.",
            example = "1",
            required = true)
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Schema(description = "Title of the film.",
            example = "The Call of the Wild",
            required = true)
    @NotBlank
    private String title;

    @Schema(description = "Synopsis of the film.",
            example = "A vibrant story of Buck, a big and kindhearted dog.")
    @Lob
    private String synopsis;
}
