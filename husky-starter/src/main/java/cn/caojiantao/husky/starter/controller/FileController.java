package cn.caojiantao.husky.starter.controller;

import cn.caojiantao.husky.common.config.QiniuConfig;
import cn.caojiantao.husky.common.service.QiniuService;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author caojiantao
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController extends ApiController {

    private final QiniuConfig qiniuConfig;
    private final QiniuService qiniuService;

    @Autowired
    public FileController(QiniuService qiniuService, QiniuConfig qiniuConfig) {
        this.qiniuService = qiniuService;
        this.qiniuConfig = qiniuConfig;
    }

    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            String result = qiniuService.uploadFile(is);
            return success(qiniuConfig.getDomain() + result);
        } catch (IOException e) {
            log.error("上传文件出现异常：", e);
            return failed("上传文件失败");
        }
    }
}
