package net.byteboost.junipy.config;

import net.byteboost.junipy.dto.RoleEnum;
import net.byteboost.junipy.model.User;
import net.byteboost.junipy.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            Dotenv dotenv = Dotenv.load();
            String adminEmail = dotenv.get("ADMIN_EMAIL");
            String adminPass = dotenv.get("ADMIN_PASSWORD");

            if (userRepo.findByEmail(adminEmail) == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail(adminEmail);
                admin.setPassword(encoder.encode(adminPass));
                admin.setRole(RoleEnum.ADMIN);

                userRepo.save(admin);
                System.out.println("Admin user created.");
            } else {
                System.out.println("Admin user already exists, skipping creation.");
            }
        };
    }
}
