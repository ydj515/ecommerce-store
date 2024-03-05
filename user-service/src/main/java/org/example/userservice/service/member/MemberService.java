package org.example.userservice.service.member;

import org.example.userservice.model.payload.request.MemberRequest;
import org.example.userservice.model.payload.response.SignUpResponse;
import org.example.userservice.model.payload.response.MemberResponse;

public interface MemberService {
    SignUpResponse createUser(MemberRequest memberRequest);

    MemberResponse getUserByUserId(String userId);

    Iterable<MemberResponse> getUsers();

    MemberResponse getUserDetailsByEmail(String userName);
}
