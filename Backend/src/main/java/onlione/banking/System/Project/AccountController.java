package onlione.banking.System.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Sign Up Endpoint
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String confirmPassword,
        @RequestParam String name
    ) {
        boolean result = accountService.signUp(username, password, confirmPassword, name);
        return result ? ResponseEntity.ok("Sign-up successful!") : ResponseEntity.badRequest().body("Sign-up failed!");
    }

    // Sign In Endpoint
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(
        @RequestParam String username,
        @RequestParam String password
    ) {
        String name = accountService.signIn(username, password);
        return name != null ? ResponseEntity.ok("Welcome, " + name + "!") : ResponseEntity.badRequest().body("Invalid credentials.");
    }

    // Change Password Endpoint
    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(
        @RequestParam String username,
        @RequestParam String oldPassword,
        @RequestParam String newPassword
    ) {
        boolean result = accountService.changePassword(username, oldPassword, newPassword);
        return result ? ResponseEntity.ok("Password changed successfully!") : ResponseEntity.badRequest().body("Password change failed.");
    }
}

