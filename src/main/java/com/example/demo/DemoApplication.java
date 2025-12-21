import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner testUser(UserService userService) {
        return args -> {
            User newUser = User.builder()
                    .fullName("Alice Tester")
                    .email("alice@example.com")
                    .password("password123")
                    .role(null) // defaults to LEARNER
                    .build();

            userService.register(newUser);
            System.out.println("✅ User Registered");

            var auth = userService.login("alice@example.com", "password123");
            System.out.println("🔑 JWT Token: " + auth.getAccessToken());
        };
    }
}
