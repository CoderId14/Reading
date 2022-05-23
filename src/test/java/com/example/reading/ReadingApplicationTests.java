package com.example.reading;

import com.example.reading.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ReadingApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    public void allComponentAreNotNull(){
        Assertions.assertNotNull(userRepository);
    }

}
