package com.movie.MovieFullStack.Controllers;

import com.movie.MovieFullStack.Dto.MailBody;
import com.movie.MovieFullStack.auth.entities.ForgotPassword;
import com.movie.MovieFullStack.auth.entities.User;
import com.movie.MovieFullStack.auth.repositories.ForgotPasswordRepository;
import com.movie.MovieFullStack.auth.repositories.UserRepository;
import com.movie.MovieFullStack.auth.utils.ChangePassword;
import com.movie.MovieFullStack.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/forgotPassword")
@RequiredArgsConstructor
public class ForgotPasswordController {
    // send mail for email verification
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyEmail (@PathVariable String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user not found by this mail "+ email));
        int otp = OtpGenerator();
        MailBody mailBody = MailBody
                .builder()
                .to(email)
                .text("This is the OTP for your Forgot Password Request " + otp )
                .subject("OTP for Forgot Passwword")
                .build();
        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70*1000))
                .user(user)
                .build();
        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fp);
        return ResponseEntity.ok("Email sent for verification");
    }
    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp (@PathVariable Integer otp, @PathVariable String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user not found by this mail "+ email));
        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user).orElseThrow(()-> new RuntimeException("Invalid otp for email "+ email));
        if(fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP verified");
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePasswordHandler (@RequestBody ChangePassword changePassword, @PathVariable String email){
        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
            return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
        }
        String encodedPassword = passwordEncoder.encode(changePassword.password());
        userRepository.updatePassword(email, encodedPassword);

        return ResponseEntity.ok("Password has been changed!");
    }
    private Integer OtpGenerator(){
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}
