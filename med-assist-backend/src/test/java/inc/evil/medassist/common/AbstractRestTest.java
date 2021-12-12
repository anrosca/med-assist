package inc.evil.medassist.common;

import inc.evil.medassist.common.security.TestSecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UrlPathHelper;

@ContextConfiguration(classes = TestSecurityConfiguration.class)
public class AbstractRestTest {
	protected MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.defaultRequest(MockMvcRequestBuilders.get("/")
						.with(new DecodePathInfoPostProcessor()))
				.build();
	}

	private static class DecodePathInfoPostProcessor implements RequestPostProcessor {
		private final UrlPathHelper urlPathHelper = new UrlPathHelper();

		@Override
		@NonNull
		public MockHttpServletRequest postProcessRequest(@NonNull MockHttpServletRequest request) {
			request.setPathInfo(this.urlPathHelper.decodeRequestString(request, request.getPathInfo()));
			request.setServletPath(this.urlPathHelper.decodeRequestString(request, request.getPathInfo()));
			return request;
		}
	}
}
