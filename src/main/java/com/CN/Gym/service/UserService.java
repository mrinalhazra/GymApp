package com.CN.Gym.service;

import com.CN.Gym.dto.UserRequest;
import com.CN.Gym.dto.WorkoutDto;
import com.CN.Gym.exception.UserNotFoundException;
import com.CN.Gym.model.Role;
import com.CN.Gym.model.User;
import com.CN.Gym.model.Workout;
import com.CN.Gym.repository.UserRepository;
import com.CN.Gym.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final WorkoutRepository workoutRepository;

    public UserService(UserRepository userRepository, WorkoutRepository workoutRepository) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
    }

    public List<User> getAllUsers() {
        return  userRepository.findAll();
    }

    public void createUser(UserRequest userRequest) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userRequest.getPassword());
        User user = new User();
                user.setEmail(userRequest.getEmail());
                user.setAge(userRequest.getAge());
                user.setGender(userRequest.getGender());
                user.setGender(userRequest.getGender());
                user.setPassword(encodedPassword);
        Role role = new Role();
        Set<Role> roles = new HashSet<>();
        if(userRequest.getUserType() != null) {
            if (userRequest.getUserType().equalsIgnoreCase("TRAINER")) {
                role.setRoleName("ROLE_TRAINER");
                roles.add(role);
                user.setRoles(roles);
            } else if (userRequest.getUserType().equalsIgnoreCase("ADMIN")) {
                role.setRoleName("ROLE_ADMIN");
                roles.add(role);
                user.setRoles(roles);
            } else {
                role.setRoleName("ROLE_CUSTOMER");
                roles.add(role);
                user.setRoles(roles);
            }
        }
        else {
            role.setRoleName("ROLE_CUSTOMER");
            roles.add(role);
            user.setRoles(roles);
        }
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        try {
            return userRepository.findById(id).get();
        } catch (Exception e) {
            throw new UserNotFoundException("No User found");
        }
    }

    public void updateUser(UserRequest userRequest, Long id) {
        User user = null;
        try {
            user = this.getUserById(id);
        } catch (Exception e) {
            throw new UserNotFoundException("No User found");
        }
        user.setAge(userRequest.getAge());
        user.setEmail(userRequest.getEmail());
        user.setGender(userRequest.getGender());
        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        Role role = new Role();
        Set<Role> roles = new HashSet<>();
        if(userRequest.getUserType() != null) {
            if (userRequest.getUserType().equalsIgnoreCase("TRAINER")) {
                role.setRoleName("ROLE_TRAINER");
                roles.add(role);
                user.setRoles(roles);
            } else if (userRequest.getUserType().equalsIgnoreCase("ADMIN")) {
                role.setRoleName("ROLE_ADMIN");
                roles.add(role);
                user.setRoles(roles);
            } else {
                role.setRoleName("ROLE_CUSTOMER");
                roles.add(role);
                user.setRoles(roles);
            }
        }
        else {
            role.setRoleName("ROLE_CUSTOMER");
            roles.add(role);
            user.setRoles(roles);
        }

        userRepository.save(user);

    }

    public void deleteUser(Long id){
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new UserNotFoundException("No User found");
        }

    }

    public void addWorkout(WorkoutDto workoutDto, Long userId) {
        User user = null;
        try {
            user = this.getUserById(userId);
        } catch (Exception e) {
            throw new UserNotFoundException("No User found");
        }
        Workout workout = new Workout();
        workout.setWorkoutName(workoutDto.getWorkoutName());
        workout.setDescription(workoutDto.getDescription());
        workout.setDuration(workoutDto.getDuration());
        workout.setDifficultyLevel(workoutDto.getDifficultyLevel());

        List<Workout> workoutList = user.getWorkouts();
        workoutList.add(workout);
        user.setWorkouts(workoutList);
        workout.setUser(user);

        userRepository.save(user);
        workoutRepository.save(workout);

    }
}
