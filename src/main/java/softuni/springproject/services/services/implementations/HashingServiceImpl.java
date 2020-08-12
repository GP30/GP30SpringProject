package softuni.springproject.services.services.implementations;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.springproject.services.services.HashingService;

@Service
public class HashingServiceImpl implements HashingService {
    private final PasswordEncoder passwordEncoder;

    public HashingServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String str) {
        return passwordEncoder.encode(str);
    }
}
