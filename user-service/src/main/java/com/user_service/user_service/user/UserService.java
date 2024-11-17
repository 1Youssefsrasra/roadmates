package com.user_service.user_service.user;

import com.user_service.user_service.exception.userNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    public String createUser(UserRequest request) {
        var user = repository.save(mapper.toUser(request))  ;
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
    }
}

