package xyz.charlespro.coder.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    Integer id;
    Integer userId;
    String fileUrl;
    Boolean isPublic;
    Date date;
}
