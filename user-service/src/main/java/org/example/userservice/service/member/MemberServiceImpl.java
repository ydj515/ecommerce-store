package org.example.userservice.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.model.entity.Member;
import org.example.userservice.model.mapper.ModelMapper;
import org.example.userservice.model.payload.request.MemberRequest;
import org.example.userservice.model.payload.response.SignUpResponse;
import org.example.userservice.model.payload.response.MemberResponse;
import org.example.userservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final UserRepository userRepository;

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
        Member member = userRepository.findByUserId(userId).orElseThrow(()->
                new UsernameNotFoundException(userId)
        );

        String orderUrl = "http://localhost:8000/order-service/%s/orders";


        return ModelMapper.INSTANCE.toMemberResponse(member);
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
