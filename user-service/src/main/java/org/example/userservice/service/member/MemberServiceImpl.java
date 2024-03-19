package org.example.userservice.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.client.OrderServiceClient;
import org.example.userservice.model.entity.Member;
import org.example.userservice.model.enums.ResultCode;
import org.example.userservice.model.mapper.ModelMapper;
import org.example.userservice.model.payload.base.Api;
import org.example.userservice.model.payload.request.MemberRequest;
import org.example.userservice.model.payload.response.MemberResponse;
import org.example.userservice.model.payload.response.OrderResponse;
import org.example.userservice.model.payload.response.SignUpResponse;
import org.example.userservice.repository.UserRepository;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final UserRepository userRepository;
    private final OrderServiceClient orderServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Override
    @Transactional

    public SignUpResponse createUser(MemberRequest memberRequest) {
        Optional<Member> findMember = userRepository.findByUserId(memberRequest.getEmail());
        if (findMember.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        Member member = userRepository.save(ModelMapper.INSTANCE.toMember(memberRequest));

        return ModelMapper.INSTANCE.toSignUpResponse(member);
    }


    @Override
    public MemberResponse getUserByUserId(String userId) {
        Member member = userRepository.findByUserId(userId).orElseThrow(() ->
                new UsernameNotFoundException(userId)
        );

        log.info("before call orders");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
        Api<List<OrderResponse>> orderResponse = circuitBreaker.run(() -> orderServiceClient.getOrders(userId),
                throwable -> Api.<List<OrderResponse>>builder()
                        .resultCode(ResultCode.FAIL)
                        .data(Collections.EMPTY_LIST)
                        .build()
        );
        log.info("after call orders");

        List<OrderResponse> orders = orderResponse.getData();

        return ModelMapper.INSTANCE.toMemberResponseWithOrders(member, orders);
    }

    @Override
    public Iterable<MemberResponse> getUsers() {
        List<Member> members = userRepository.findAll();
        return members.stream()
                .map(ModelMapper.INSTANCE::toMemberResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MemberResponse getUserDetailsByEmail(String userName) {
        return null;
    }
}
