package community.community.controller;

import community.community.dto.FileDTO;
import community.community.provider.UCloudProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Coder on 2023/10/30 & 21:59.
 **/
@Controller
@Slf4j
public class FileController {
    @Autowired
    private UCloudProvider uCloudProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        try {
            String fileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            log.info("Image Upload filename: {}", fileName);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setUrl(fileName);
            fileDTO.setSuccess(1);
            return fileDTO;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
