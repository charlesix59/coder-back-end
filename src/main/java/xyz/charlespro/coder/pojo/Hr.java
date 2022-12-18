package xyz.charlespro.coder.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hr {

    Integer id;
    String name;
    String email;
    String phone;
    String password;
    String avatar;
    Boolean gender;
    Date birthday;
    Integer companyId;
}
