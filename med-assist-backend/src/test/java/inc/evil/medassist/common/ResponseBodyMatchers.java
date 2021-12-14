package inc.evil.medassist.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.test.web.servlet.ResultMatcher;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ResponseBodyMatchers {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.registerModule(new JavaTimeModule());
	}

	public <T> ResultMatcher containsObjectAsJson(Object expectedObject, Class<T> targetClass) {
		return mvcResult -> {
			String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
			T actualObject = objectMapper.readValue(json, targetClass);
			assertThat(actualObject).isEqualToComparingFieldByField(expectedObject);
		};
	}

	public <T> ResultMatcher containsListAsJson(Object expectedObject) {
		return mvcResult -> {
			String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
			JsonNode actualObject = objectMapper.readTree(json);
			assertThat(actualObject).isEqualToComparingFieldByField(toJsonNode(expectedObject));
		};
	}

	private JsonNode toJsonNode(Object object) {
		try {
			String json = objectMapper.writeValueAsString(object);
			return objectMapper.readTree(json);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static ResponseBodyMatchers responseBody() {
		return new ResponseBodyMatchers();
	}
}

