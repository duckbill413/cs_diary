package wh.duckbill.netflix.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wh.duckbill.netflix.exception.UserException;
import wh.duckbill.netflix.user.command.UserResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements FetchUserUsecase {
    private final FetchUserPort fetchUserPort;

    @Override
    public UserResponse findUserByEmail(String email) {
        Optional<UserPortResponse> byEmail = fetchUserPort.findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new UserException.UserDoesNotExistException();
        }

        UserPortResponse userPortResponse = byEmail.get();
        return UserResponse.builder()
                .userId(userPortResponse.getUserId())
                .email(userPortResponse.getEmail())
                .phone(userPortResponse.getPhone())
                .password(userPortResponse.getPassword())
                .provider(userPortResponse.getProvider())
                .providerId(userPortResponse.getProviderId())
                .role(userPortResponse.getRole())
                .username(userPortResponse.getUsername())
                .build();
    }
}
