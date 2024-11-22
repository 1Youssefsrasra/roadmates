package com.user_service.user_service.user;

import com.user_service.user_service.exception.userNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository repository;
    private final UserMapper mapper;
    public String createUser(UserRequest request) {
        var user = this.repository.save(mapper.toUser(request))  ;
        return user.getId();
    }

    public void updateUser(UserRequest request) {
        var user = this.repository.findById(request.id())
                .orElseThrow(() -> new userNotFoundException(
                        String.format("Cannot update user:: No user found with the provided ID: %s", request.id())
                ));
        mergeuser(user, request);
        this.repository.save(user);
    }

    private void mergeuser(user user, UserRequest request) {
        if (StringUtils.isNotBlank(request.firstname())){
            user.setFirstname(request.firstname());
        }
        if (StringUtils.isNotBlank(request.lastname())){
            user.setLastname(request.lastname());
        }
        if (StringUtils.isNotBlank(request.email())){
            user.setEmail(request.email());
        }
        if (request.phonenumber() != null) {
            user.setPhonenumber(request.phonenumber());
        }
        if (request.birthday() != null){
            user.setBirthday(request.birthday());
        }

        if (request.IdentityCard() != null) {
            user.setIdentitycard(request.IdentityCard());
        }
        if (request.address() != null){
            user.setAddress(request.address());
        }
    }

    public List<UserResponse> findAllUsers() {
        return this.repository.findAll().stream()
                .map(mapper::fromUser)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String userId) {
        return  this.repository.findById(userId).isPresent();
    }

    public UserResponse findById(String userId) {
    return this.repository.findById(userId).map(mapper::fromUser).orElseThrow(() -> new userNotFoundException(String.format("No user found with this ID :: %s ",userId)));}

    public Void deleteUser(String userId) {
        this.repository.deleteById(userId);
        return null;
    }
}

