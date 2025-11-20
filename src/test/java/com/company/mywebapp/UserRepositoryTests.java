package com.company.mywebapp;

import com.company.mywebapp.user.User;
import com.company.mywebapp.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired private UserRepository repo;

    @Test
    public void addNewUser() {
        User user = new User();
        user.setEmail("email@exmpl");
        user.setFirstName("First");
        user.setLastName("Last");
        user.setPassword("password");
        User savedUser = repo.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
        Assertions.assertThat(savedUser.getEmail()).isEqualTo("email@exmpl");
        Assertions.assertThat(savedUser.getFirstName()).isEqualTo("First");
        Assertions.assertThat(savedUser.getLastName()).isEqualTo("Last");
        Assertions.assertThat(savedUser.getPassword()).isEqualTo("password");

    }

}
