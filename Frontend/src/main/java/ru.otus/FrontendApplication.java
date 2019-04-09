package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.service.FrontendService;
import ru.otus.socket.FrontendSocketExecutor;

@SpringBootApplication
public class FrontendApplication {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(FrontendApplication.class, args);
        FrontendSocketExecutor starter = context.getBean(FrontendSocketExecutor.class);
        FrontendService frontendService = context.getBean(FrontendService.class);
        starter.init(Integer.valueOf(args[0]), frontendService);
    }
}
