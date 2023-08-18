package com.reservei.clientsapi.config.feign;

import com.reservei.clientsapi.domain.record.TokenData;
import com.reservei.clientsapi.domain.record.UserData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "security-api", url = "${SECURITY_API}")
public interface UserClient {

    @PostMapping("/users")
    void createUser(@RequestBody UserData data);

    @PutMapping("/users/{publicId}")
    void updateUser(@PathVariable String publicId,
                    @RequestBody UserData data,
                    @RequestHeader("Authorization") String token);

    @PutMapping("/users/reactivate/{publicId}")
    void reactivateUser(@PathVariable String publicId,
                        @RequestHeader("Authorization") String token);

    @DeleteMapping("/users/{publicId}")
    void deleteUser(@PathVariable String publicId,
                    @RequestHeader("Authorization") String token);

    @PostMapping("/users/validate")
    String validateToken(@RequestBody TokenData data);
}
