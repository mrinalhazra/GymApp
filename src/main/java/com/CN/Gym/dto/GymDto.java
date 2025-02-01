package com.CN.Gym.dto;


import com.CN.Gym.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GymDto {

    String name;
    String address;
    Long contactNo;
    String membershipPlans;
    String facilities;
    List<User> members = new ArrayList<>();


}
