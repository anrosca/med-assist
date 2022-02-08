package inc.evil.medassist.treatment.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record TreatedTeeth(@NotBlank String toothId, @NotNull Boolean isExtracted) {
}
