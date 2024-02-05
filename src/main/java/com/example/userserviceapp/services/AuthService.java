package com.example.userserviceapp.services;

import com.example.userserviceapp.dtos.UserDto;
import com.example.userserviceapp.exceptions.*;
import com.example.userserviceapp.models.Session;
import com.example.userserviceapp.models.SessionStatus;
import com.example.userserviceapp.models.User;
import com.example.userserviceapp.repositories.SessionRepository;
import com.example.userserviceapp.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public ResponseEntity<UserDto> login(String email, String password) throws UserDoesntExistException, InvalidPasswordException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserDoesntExistException("User with email "+ email +" doesn't exists");
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new InvalidPasswordException("Password is invalid");
        }
        UserDto userDto = UserDto.from(user);
        Map<String,Object> claimsMap = new HashMap<>();
        claimsMap.put("userId",user.getId());
        claimsMap.put("email",user.getEmail());
        claimsMap.put("roles",user.getRoles());

        SecretKey key = Jwts.SIG.HS256.key().build();

        String token = Jwts.builder().claims(claimsMap).signWith(key).compact();
        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);
        session.setToken(token);
        sessionRepository.save(session);
        MultiValueMapAdapter<String,String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH-TOKEN",token);
        return new ResponseEntity<>(userDto,headers, HttpStatus.OK);
    }
    public void logout(String token,Long userId){
        Optional<Session> optionalSession = sessionRepository.findByTokenAndUser_Id(token,userId);
        if(optionalSession.isEmpty())
        {
            return ;
        }
        Session session = optionalSession.get();
        session.setSessionStatus(SessionStatus.LOGGED_OUT);
        sessionRepository.save(session);
    }
    public User signUp(String email,String password) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            throw new UserAlreadyExistsException("User with email "+ email +" already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return user;
    }
    public User validate(String token, Long userId) {
        Optional<Session> optionalSession = sessionRepository.findByTokenAndUser_Id(token,userId);
        if(optionalSession.isEmpty()){
            return null;
        }
        Session session = optionalSession.get();
        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE))
            return null;
        return userRepository.findById(userId).get();
    }
}
