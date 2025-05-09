package tn.esprit.examen.controllers;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.examen.entities.UserDTO;

@FeignClient(name = "user-service" , url = "http://localhost:8082")
public interface UserClient {
    @GetMapping("/api/users/{id}/exists")
    Boolean userExists(@PathVariable("id") Long id);

    @GetMapping("/api/users/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
}
