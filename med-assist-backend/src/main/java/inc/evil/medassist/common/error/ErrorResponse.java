package inc.evil.medassist.common.error;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ErrorResponse {
	private String path;
	private Set<String> messages;
}
