package com.example.bankofsamaratest.mappers;

import com.example.bankofsamaratest.dto.UserDto;
import com.example.bankofsamaratest.model.Email;
import com.example.bankofsamaratest.model.PhoneNumber;
import com.example.bankofsamaratest.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "email", expression = "java(emailsToStrings(user.getEmails()))")
    @Mapping(target = "phoneNumber", expression = "java(phonesToStrings(user.getPhones()))")
    @Mapping(target = "balance", source = "account.balance")
    UserDto toUserDto(User user);

    @Mapping(target = "emails", expression = "java(stringsToEmails(userDto.getEmail()))")
    @Mapping(target = "phones", expression = "java(stringsToPhones(userDto.getPhoneNumber()))")
    @Mapping(target = "account.balance", source = "balance")
    User toUser(UserDto userDto);

    default Set<String> emailsToStrings(Set<Email> emails) {
        return emails.stream().map(Email::getEmail).collect(Collectors.toSet());
    }

    default Set<String> phonesToStrings(Set<PhoneNumber> phones) {
        return phones.stream().map(PhoneNumber::getNumber).collect(Collectors.toSet());
    }
    default Set<Email> stringsToEmails(Set<String> emailStrings) {
        return emailStrings.stream()
                .map(emailStr -> {
                    Email email = new Email();
                    email.setEmail(emailStr);
                    return email;
                })
                .collect(Collectors.toSet());
    }
    default Set<PhoneNumber> stringsToPhones(Set<String> phoneStrings) {
        return phoneStrings.stream()
                .map(phoneStr -> {
                    PhoneNumber phone = new PhoneNumber();
                    phone.setNumber(phoneStr);
                    return phone;
                })
                .collect(Collectors.toSet());
    }
}
