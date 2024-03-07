package org.example.userservice.model.mapper;

import org.example.userservice.model.entity.Member;
import org.example.userservice.model.payload.request.MemberRequest;
import org.example.userservice.model.payload.response.MemberResponse;
import org.example.userservice.model.payload.response.OrderResponse;
import org.example.userservice.model.payload.response.SignUpResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Mapper
public interface ModelMapper {
    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);
    BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Mapping(target = "password", source = "password", qualifiedByName = "passwordEncoding")
    Member toMember(MemberRequest memberRequest);

    SignUpResponse toSignUpResponse(Member member);

    @Mapping(target="orders", source = "orderResponses")
    MemberResponse toMemberResponseWithOrders(Member member, List<OrderResponse> orderResponses);

    MemberResponse toMemberResponse(Member member);

    @Named("passwordEncoding")
    default String passwordEncoding(String password) {
        return PASSWORD_ENCODER.encode(password);
    }
}
