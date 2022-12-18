package xyz.charlespro.coder.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJob {
    Integer userId;
    Integer jobId;
    String state;
    boolean isCancel;
    String reason;
    Date lastModifyDate;
}
