package org.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.userservice.model.enums.ResultCode;
import org.example.userservice.model.payload.base.Api;
import org.example.userservice.model.payload.request.MemberRequest;
import org.example.userservice.model.payload.response.MemberResponse;
import org.example.userservice.model.payload.response.SignUpResponse;
import org.example.userservice.service.member.MemberService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserRestController {

    private final Environment environment;

    private final MemberService memberService;

    @GetMapping("/health-check")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok(String.format("service on PORT %s", environment.getProperty("local.server.port")));
    }

    @PostMapping("/users")
    public ResponseEntity<Api<SignUpResponse>> createUser(@RequestBody MemberRequest memberRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Api.<SignUpResponse>builder()
                        .resultCode(ResultCode.JOIN_SUCCESS)
                        .data(memberService.createUser(memberRequest))
                        .build()
        );
    }

    @GetMapping("/users")
    public ResponseEntity<Api<Iterable<MemberResponse>>> getUsers() {
        return ResponseEntity.ok(
                Api.<Iterable<MemberResponse>>builder()
                        .resultCode(ResultCode.JOIN_SUCCESS)
                        .data(memberService.getUsers())
                        .build()
        );
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Api<MemberResponse>> getUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(
                Api.<MemberResponse>builder()
                        .resultCode(ResultCode.JOIN_SUCCESS)
                        .data(memberService.getUserByUserId(userId))
                        .build()
        );
    }
}
