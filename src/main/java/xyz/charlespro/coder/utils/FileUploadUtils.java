package xyz.charlespro.coder.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileUploadUtils {
    @Value("${file-save-path}")
    private String fileSavePath;
//    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
    public String fileHandler(MultipartFile uploadFile, HttpServletRequest req){
        String filePath = "";
//        String format = sdf.format(new Date());
//        File folder = new File(fileSavePath + format);
        File folder = new File(fileSavePath);
//        if (!folder.isDirectory()) {
//            folder.mkdirs();
//            assert uploadFile != null;
//            String oldName = uploadFile.getOriginalFilename();
//            String newName = UUID.randomUUID().toString() +
//                    Objects.requireNonNull(oldName).substring(oldName.lastIndexOf("."), oldName.length());
//            try {
//                uploadFile.transferTo(new File(folder, newName));
//                filePath = req.getScheme() + "://" + req.getServerName() + ":" +
//                        req.getServerPort() + "/uploadFile/" + format + newName;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "上传失败! ";
//            }
//        }
        assert folder.isDirectory();
        String oldName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString() +
                Objects.requireNonNull(oldName).substring(oldName.lastIndexOf("."), oldName.length());
        try {
            uploadFile.transferTo(new File(folder, newName));
//            filePath = req.getScheme() + "://" + req.getServerName() + ":" +
//                    req.getServerPort() + "/uploadFile/" + format + newName;
            filePath = "/uploadFile/"  + newName;
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败! ";
        }
        return filePath;
    }
}
