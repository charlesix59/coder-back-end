package xyz.charlespro.coder.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    Integer id;
    String name;
    String scale;
    String type;
    String legalPerson;
}
