package dev.patika.patika0306.util;

import dev.patika.patika0306.entity.User;
import dev.patika.patika0306.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitializerRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitializerRunner.class);

    @Autowired
    UserRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();

        repository.save(User.builder().username("ali").password("1234").build());
        repository.save(User.builder().username("veli").password("1234").role("USER").build());

        repository.findAll().forEach(user -> logger.info("{}", user));

    }
}
