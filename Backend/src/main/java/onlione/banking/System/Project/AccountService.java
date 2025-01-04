package onlione.banking.System.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Sign Up method
    public boolean signUp(String username, String password, String confirmPassword, String name) {
        if (!password.equals(confirmPassword)) {
            return false;
        }

        // Check if username already exists
        if (accountRepository.findByUsername(username) != null) {
            return false;
        }

        // Create account and save to database
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setName(name);

        accountRepository.save(account);
        return true;
    }

    // Sign In method
    public String signIn(String username, String password) {
        Account account = accountRepository.findByUsername(username);
        if (account != null && passwordEncoder.matches(password, account.getPassword())) {
            return account.getName();
        }
        return null;
    }

    // Change Password method
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        Account account = accountRepository.findByUsername(username);
        if (account != null && passwordEncoder.matches(oldPassword, account.getPassword())) {
            account.setPassword(passwordEncoder.encode(newPassword));
            accountRepository.save(account);
            return true;
        }
        return false;
    }
}
