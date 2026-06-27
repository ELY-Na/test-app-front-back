package com.openclassrooms.starterjwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringBootSecurityJwtApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	void shouldCallMain() {

		try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {

			SpringBootSecurityJwtApplication.main(new String[] {});

			mocked.verify(() -> SpringApplication.run(
					SpringBootSecurityJwtApplication.class,
					new String[] {}));
		}
	}
}
