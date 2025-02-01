package com.CN.Gym.service;


import com.CN.Gym.dto.GymDto;
import com.CN.Gym.exception.GymNotFoundException;
import com.CN.Gym.exception.UserNotFoundException;
import com.CN.Gym.model.Gym;
import com.CN.Gym.model.User;
import com.CN.Gym.repository.GymRepository;
import com.CN.Gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class GymService {

    @Autowired
    private final GymRepository gymRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private UserService userService;

    public GymService(GymRepository gymRepository, UserRepository userRepository) {
        this.gymRepository = gymRepository;
        this.userRepository = userRepository;
    }
    /*
        This is the service class for Gym, you need to complete the class by doing the following:
        a. Use appropriate annotations.
        b. Complete the methods given below.
        c. Autowire the necessary dependencies.
     */


    public List<Gym> getAllGyms() {   
        return gymRepository.findAll();
    }

     public Gym getGymById(Long id) {
         try {
             return gymRepository.findById(id).get();
         } catch (Exception e) {
             throw new GymNotFoundException("No gym found with id: "+id);
         }
     }

    public void deleteGymById(Long id) {
        try {
            gymRepository.deleteById(id);
        } catch (Exception e) {
            throw new GymNotFoundException("No gym found with id: "+id);
        }
    }

    public void updateGym(GymDto gymDto, Long id) {
        Gym gym = null;
        try {
            gym = this.getGymById(id);
        } catch (Exception e) {
            throw new GymNotFoundException("No gym found with id: "+id);
        }
        gym.setAddress(gymDto.getAddress());
        gym.setName(gymDto.getName());
        gym.setFacilities(gymDto.getFacilities());
        gym.setContactNo(gymDto.getContactNo());
        gym.setMembershipPlans(gymDto.getMembershipPlans());
        gym.setMembers(gymDto.getMembers());
        gymRepository.save(gym);
    }

    public void createGym(GymDto gymDto) {
        Gym gym = new Gym();
        gym.setAddress(gymDto.getAddress());
        gym.setName(gymDto.getName());
        gym.setFacilities(gymDto.getFacilities());
        gym.setContactNo(gymDto.getContactNo());
        gym.setMembershipPlans(gymDto.getMembershipPlans());
        gym.setMembers(gymDto.getMembers());
        gymRepository.save(gym);



    }


    public void addMember(Long userId, Long gymId) {
        User user = null;
        try {
            user = this.userService.getUserById(userId);
        } catch (Exception e) {
            throw new UserNotFoundException("No user found");
        }
        Gym gym = null;
        try {
            gym = getGymById(gymId);
        } catch (Exception e) {
            throw new GymNotFoundException("No gym found");
        }
        List<User> members = gym.getMembers();
       members.add(user);
       gym.setMembers(members);
       user.setGym(gym);
       gymRepository.save(gym);
       userRepository.save(user);
    }


    public void deleteMember(Long userId, Long gymId) {

        User user = null;
        try {
            user = this.userRepository.findById(userId).get();
        } catch (Exception e) {
            throw new UserNotFoundException("N user found with the id:"+userId);
        }
        Gym gym = null;
        try {
            gym = this.getGymById(gymId);
        } catch (Exception e) {
            throw new GymNotFoundException("No gym found with id: "+gymId);
        }
       if(gym.getMembers().contains(user)){
           user.setGym(null);
           gym.getMembers().remove(user);
           gymRepository.save(gym);
           userRepository.save(user);
       }
    }
}
