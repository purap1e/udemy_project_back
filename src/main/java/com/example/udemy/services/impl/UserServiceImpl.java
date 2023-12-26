package com.example.udemy.services.impl;

import com.example.udemy.dto.song.SongResponseDto;
import com.example.udemy.dto.user.UserLoginRequestDTO;
import com.example.udemy.entities.Role;
import com.example.udemy.entities.Song;
import com.example.udemy.entities.User;
import com.example.udemy.exceptions.NotFoundException;
import com.example.udemy.exceptions.UsernameExistsException;
import com.example.udemy.mapper.SongResponseMapper;
import com.example.udemy.mapper.UserMapper;
import com.example.udemy.repositories.UserRepository;
import com.example.udemy.services.RoleService;
import com.example.udemy.services.SongService;
import com.example.udemy.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final SongService songService;
    private final SongResponseMapper songResponseMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            log.info("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else {
            log.info("User found in the database {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }
    @Override
    public UUID register(UserLoginRequestDTO userLoginRequestDTO) throws UsernameExistsException {
        if (usernameExist(userLoginRequestDTO.getUsername())) {
            throw new UsernameExistsException(userLoginRequestDTO.getUsername());
        } else {
            User user = userMapper.toEntity(userLoginRequestDTO);
            user.setPassword(passwordEncoder.encode(userLoginRequestDTO.getPassword()));
            Role role = roleService.findByName("USER");
            user.setRoles(Set.of(role));
            return userRepository.save(user).getId();
        }
    }

    @Override
    public UUID addRoleToUser(UUID userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found"));
        Role role = roleService.findByName(roleName);
        user.getRoles().add(role);
        return userRepository.save(user).getId();
    }

    @Override
    public User getUser(UUID id) {
        log.info("Fetching user with id {}", id);
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("user not found"));
    }

    private boolean usernameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public void addSongToUser(UUID userId, UUID songId) {
        User user = getUser(userId);
        Song song = songService.getEntitySong(songId);

        user.getSong().add(song);
        userRepository.save(user);
    }

    @Override
    public void deleteSongFromUser(UUID userId, UUID songId) {
        User user = getUser(userId);
        Song song = songService.getEntitySong(songId);

        user.getSong().remove(song);
        userRepository.save(user);
    }

    @Override
    public List<SongResponseDto> getAllFavourites(UUID userId) {
        User user = getUser(userId);
        return user.getSong().stream().map(songResponseMapper).toList();
    }
}
