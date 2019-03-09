package cn.caojiantao.husky.common.m3u8;

import lombok.AllArgsConstructor;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author caojiantao
 */
@AllArgsConstructor
public class DownloadTask implements Runnable {

    private int index;
    private CountDownLatch latch;
    private String tsUrl;
    private ConcurrentHashMap<Integer, byte[]> tsMap;
    private String contextPath;

    @Override
    public void run() {
        String schemaPrefix = "http";
        if (!tsUrl.startsWith(schemaPrefix)) {
            tsUrl = contextPath + tsUrl;
        }
        System.out.println("[" + index + "]线程开始下载...");
        try (InputStream is = new URL(tsUrl).openStream()) {
            byte[] bytes = StreamUtils.copyToByteArray(is);
            tsMap.put(index, bytes);
            System.out.println("[" + index + "]下载线程已完成...");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
            System.out.println("还剩" + latch.getCount());
        }
    }
}
