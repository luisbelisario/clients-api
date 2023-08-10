package com.reservei.clientsapi.config.feign;

import com.reservei.clientsapi.domain.record.UserData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "security-api", url = "${SECURITY_API}")
public interface UserClient {

    @PostMapping ("/users")
    void createUser(@RequestBody UserData data);

}
