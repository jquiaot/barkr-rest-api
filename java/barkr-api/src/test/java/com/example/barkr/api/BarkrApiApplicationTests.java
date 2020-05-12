package com.example.barkr.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BarkrApiApplicationTests {

    @Autowired
    private BarksController barksController;

    /*
     * Basic test to ensure context loads properly.
     */
	@Test
	void contextLoads() {
        assertThat(barksController).isNotNull();
	}

}
