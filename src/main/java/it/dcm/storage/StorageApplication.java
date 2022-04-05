package it.dcm.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@Slf4j
@SpringBootApplication
public class StorageApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
        log.info("  \n______  __       __ _______                      ______  ________ \n" +
                " /      \\|  \\     /  \\       \\                    /      \\|        \\\n" +
                "|  ▓▓▓▓▓▓\\ ▓▓\\   /  ▓▓ ▓▓▓▓▓▓▓\\ ______  __     __|  ▓▓▓▓▓▓\\\\▓▓▓▓▓▓▓▓\n" +
                "| ▓▓   \\▓▓ ▓▓▓\\ /  ▓▓▓ ▓▓  | ▓▓/      \\|  \\   /  \\ ▓▓__/ ▓▓   /  ▓▓ \n" +
                "| ▓▓     | ▓▓▓▓\\  ▓▓▓▓ ▓▓  | ▓▓  ▓▓▓▓▓▓\\\\▓▓\\ /  ▓▓\\▓▓    ▓▓  /  ▓▓  \n" +
                "| ▓▓   __| ▓▓\\▓▓ ▓▓ ▓▓ ▓▓  | ▓▓ ▓▓    ▓▓ \\▓▓\\  ▓▓ _\\▓▓▓▓▓▓▓ /  ▓▓   \n" +
                "| ▓▓__/  \\ ▓▓ \\▓▓▓| ▓▓ ▓▓__/ ▓▓ ▓▓▓▓▓▓▓▓  \\▓▓ ▓▓ |  \\__/ ▓▓/  ▓▓    \n" +
                " \\▓▓    ▓▓ ▓▓  \\▓ | ▓▓ ▓▓    ▓▓\\▓▓     \\   \\▓▓▓   \\▓▓    ▓▓  ▓▓     \n" +
                "  \\▓▓▓▓▓▓ \\▓▓      \\▓▓\\▓▓▓▓▓▓▓  \\▓▓▓▓▓▓▓    \\▓     \\▓▓▓▓▓▓ \\▓▓      \n" +
                "                                                                   ");
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // TODO Auto-generated method stub
        log.info("  \n______  __       __ _______                      ______  ________ \n" +
                " /      \\|  \\     /  \\       \\                    /      \\|        \\\n" +
                "|  ▓▓▓▓▓▓\\ ▓▓\\   /  ▓▓ ▓▓▓▓▓▓▓\\ ______  __     __|  ▓▓▓▓▓▓\\\\▓▓▓▓▓▓▓▓\n" +
                "| ▓▓   \\▓▓ ▓▓▓\\ /  ▓▓▓ ▓▓  | ▓▓/      \\|  \\   /  \\ ▓▓__/ ▓▓   /  ▓▓ \n" +
                "| ▓▓     | ▓▓▓▓\\  ▓▓▓▓ ▓▓  | ▓▓  ▓▓▓▓▓▓\\\\▓▓\\ /  ▓▓\\▓▓    ▓▓  /  ▓▓  \n" +
                "| ▓▓   __| ▓▓\\▓▓ ▓▓ ▓▓ ▓▓  | ▓▓ ▓▓    ▓▓ \\▓▓\\  ▓▓ _\\▓▓▓▓▓▓▓ /  ▓▓   \n" +
                "| ▓▓__/  \\ ▓▓ \\▓▓▓| ▓▓ ▓▓__/ ▓▓ ▓▓▓▓▓▓▓▓  \\▓▓ ▓▓ |  \\__/ ▓▓/  ▓▓    \n" +
                " \\▓▓    ▓▓ ▓▓  \\▓ | ▓▓ ▓▓    ▓▓\\▓▓     \\   \\▓▓▓   \\▓▓    ▓▓  ▓▓     \n" +
                "  \\▓▓▓▓▓▓ \\▓▓      \\▓▓\\▓▓▓▓▓▓▓  \\▓▓▓▓▓▓▓    \\▓     \\▓▓▓▓▓▓ \\▓▓      \n" +
                "                                                                   ");
        return builder.sources(StorageApplication.class);
    }


}
