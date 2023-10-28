package org.heapminds;

import org.heapminds.configuration.WebClientConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppTest {

    @Autowired
    WebClientConfig webClientConfig;

    @Test
    @DisplayName("should return non-empty colleges list")
    public void shouldReturnNonEmptyCollegesList() {
        // Mock WebClientConfig behavior
       
    }


}
