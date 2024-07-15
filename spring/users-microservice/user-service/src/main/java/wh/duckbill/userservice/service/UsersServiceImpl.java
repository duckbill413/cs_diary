package wh.duckbill.userservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wh.duckbill.userservice.dto.UserDto;
import wh.duckbill.userservice.jpa.UserEntity;
import wh.duckbill.userservice.jpa.UsersRepository;
import wh.duckbill.userservice.vo.ResponseOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        usersRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = usersRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return usersRepository.findAll();
    }
}
