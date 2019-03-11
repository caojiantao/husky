package cn.caojiantao.husky.common.service;

import cn.caojiantao.husky.common.config.QiniuConfig;
import cn.caojiantao.husky.common.util.UniqueIdUtils;
import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

/**
 * @author caojiantao
 */
@Slf4j
@Service
public class QiniuService {

    private final QiniuConfig config;

    @Autowired
    public QiniuService(QiniuConfig config) {
        this.config = config;
    }

    /**
     * 上传文件
     *
     * @param is 文件流
     * @return 文件地址
     */
    public String uploadFile(InputStream is) throws QiniuException {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        String upToken = auth.uploadToken(config.getBucket());
        String fileKey = UniqueIdUtils.getUUID();
        Response response = uploadManager.put(is, fileKey, upToken, null, null);
        // 解析上传成功的结果
        DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
        return putRet.key;
    }

    public String simplifyUrl(String url) {
        return StringUtils.isEmpty(url) ? url : url.replace(config.getDomain(), "");
    }

    public String decorateUrl(String url) {
        String schema = "http";
        return (StringUtils.isEmpty(url) || url.startsWith(schema)) ? url : (config.getDomain() + url);
    }
}
