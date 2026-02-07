package com.farihim.assessment.demo.service;

import com.farihim.assessment.demo.domain.User;
import com.farihim.assessment.demo.dto.request.UserRequest;
import com.farihim.assessment.demo.dto.response.UserResponse;
import com.farihim.assessment.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsersPaged(int page, int size, String sortBy, String sortDir) {
        Set<String> allowedFields = Set.of("id", "name", "email", "lastAccessed");
        if (!allowedFields.contains(sortBy)) {
            throw new IllegalArgumentException("Cannot sort by field: " + sortBy);
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return userRepository.findAll(pageable)
                .map(this::mapToResponse);
    }


    @Transactional
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));

        user.setLastAccessed(LocalDateTime.now());
        userRepository.save(user);

        return mapToResponse(user);
    }

    @Transactional
    public UserResponse updateUser(UUID id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .lastAccessed(user.getLastAccessed())
                .build();
    }
}
