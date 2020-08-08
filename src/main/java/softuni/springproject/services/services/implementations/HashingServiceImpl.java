package softuni.springproject.services.services.implementations;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import softuni.springproject.services.services.HashingService;

@Service
public class HashingServiceImpl implements HashingService {
    @Override
    public String hash(String str) {
        return DigestUtils.sha256Hex(str);
    }
}
