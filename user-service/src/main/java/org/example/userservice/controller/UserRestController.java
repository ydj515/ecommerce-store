package org.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.userservice.model.payload.request.MemberRequest;
import org.example.userservice.model.payload.response.SignUpResponse;
import org.example.userservice.service.member.MemberService;
import org.example.userservice.model.vo.Greeting;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserRestController {

    private final Environment environment;
    private final Greeting greeting;

    private final MemberService memberService;

    @GetMapping("/health-check")
    public ResponseEntity<?> healthCheck() {

        return ResponseEntity.ok(String.format("service on PORT %s", environment.getProperty("local.server.port")));
    }

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        return ResponseEntity.ok(greeting.getMessage());
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody MemberRequest memberRequest) {
        SignUpResponse signUpResponse = memberService.createUser(memberRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(memberService.getUsers());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(memberService.getUserByUserId(userId));
    }
}
