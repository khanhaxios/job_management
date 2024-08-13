package com.fira.app.seeders;

import com.fira.app.entities.Account;
import com.fira.app.enums.Role;
import com.fira.app.repository.AccountRepository;
import com.fira.app.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class AccountSeeder implements CommandLineRunner, Ordered {

    private final AccountRepository accountRepository;

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("Print admin username :: : ");
//            String adminAccount = scanner.nextLine();
//            Account account = accountRepository.findByUsername(adminAccount).orElse(null);
//            if (account != null) {
//                System.out.println("Admin account exited!");
//                return;
//            }
//            account = new Account();
//            account.setUsername(adminAccount);
//            System.out.println("Print admin password :: : ");
//            String password = scanner.nextLine();
//            if (password.length() < 8 || password.length() > 16) {
//                System.out.println("Password length error!");
//                return;
//            }
//            account.setPassword(new BCryptPasswordEncoder().encode(password));
//            account.setRole(roleRepository.findByName(Role.ADMIN).orElse(null));
//            accountRepository.save(account);
//            System.out.println("Created admin account success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getOrder() {
        return 99;
    }
}
