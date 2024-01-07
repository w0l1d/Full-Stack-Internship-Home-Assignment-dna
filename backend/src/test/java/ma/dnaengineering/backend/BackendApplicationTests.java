package ma.dnaengineering.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * This class is used to test the Spring Boot application.
 * It uses the SpringBootTest annotation to indicate that it's a test for Spring Boot application.
 */
@SpringBootTest
class BackendApplicationTests {

	/**
	 * This method is used to add dynamic properties to the Spring Boot application.
	 * It uses the DynamicPropertySource annotation to indicate that it's a dynamic property source.
	 * The properties are added to the DynamicPropertyRegistry.
	 *
	 * @param registry the DynamicPropertyRegistry to which the properties are added
	 */
	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		// in test the parent directory of the project is './backend' instead of './'
		// so we need to change ./backend/compose.yaml to ./compose.yaml
		registry.add("spring.docker.compose.file", () -> "compose.yaml");
		// alter default behavior to not skip docker compose in tests
		registry.add("spring.docker.compose.skip.in-tests", () -> "false");
	}

	/**
	 * This is a test method to check if the Spring Boot application context loads correctly.
	 */
	@Test
	void contextLoads() {
		Assertions.assertTrue(true);
	}

}