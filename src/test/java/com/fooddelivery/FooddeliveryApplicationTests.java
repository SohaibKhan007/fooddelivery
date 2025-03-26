package com.fooddelivery;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FooddeliveryApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> FooddeliveryApplication.main(new String[] {}));
	}
}
