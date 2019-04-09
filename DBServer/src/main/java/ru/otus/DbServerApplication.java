package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.otus.domain.User;
import ru.otus.service.DbService;
import ru.otus.socket.DbSocketExecutor;

@EnableJpaRepositories
@SpringBootApplication
public class DbServerApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(DbServerApplication.class, args);
        DbSocketExecutor starter = context.getBean(DbSocketExecutor.class);
        starter.init(Integer.valueOf(args[0]));
    }
}
