package libraryservice.libraryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "jwtDemoApplication", url = "http://jwtDemo:8083")
public interface JwtAuthClient {
    @GetMapping("/api/auth/validate")
    Boolean validateToken(@RequestHeader("Authorization") String token);
}