package xyz.charlespro.coder.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    Integer id;
    int creatorId;
    String name;
    Company company;
    String description;
    Integer recruitNumber;
    String experienceRequirement;
    String educationRequirement;
    String firstCategories;
    String secondCategories;
    String tag;
}
