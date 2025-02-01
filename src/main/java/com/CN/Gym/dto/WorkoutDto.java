package com.CN.Gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDto {

    String workoutName;
    String description;
    String difficultyLevel;
    int duration;


}
