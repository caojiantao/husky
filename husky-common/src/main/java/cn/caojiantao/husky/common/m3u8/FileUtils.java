package cn.caojiantao.husky.common.m3u8;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author caojiantao
 */
public class FileUtils {

    private final static Pattern M3U8_PATTERN = Pattern.compile(".*\\.ts.*");

    private static List<String> parseM3u8ByUrl(String url) {
        try (InputStream is = new URL(url).openStream()) {
            return parseM3u8(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> parseM3u8ByFile(File file) {
        try (InputStream is = new FileInputStream(file)) {
            return parseM3u8(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> parseM3u8(InputStream is) throws IOException {
        List<String> result = new ArrayList<>();
        String content = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
        Matcher matcher = M3U8_PATTERN.matcher(content);
        while (matcher.find()) {
            String s = matcher.group();
            result.add(s);
            System.out.println(s);
        }
        return result;
    }

    public static void main(String[] args) {
        String contextPath = "https://video-dev.github.io/streams/x36xhzz/url_8/";
//        String url = contextPath + "193039199_mp4_h264_aac_fhd_7.m3u8";
//        List<String> tsList = parseM3u8ByUrl(url);

        List<String> tsList = parseM3u8ByFile(new File("C:\\Users\\caojiantao\\Downloads\\白蛇.m3u8"));

        if (CollectionUtils.isEmpty(tsList)){
            System.out.println("解析分片为空...");
            return;
        }
        int poolSize = Runtime.getRuntime().availableProcessors() * 2;
        System.out.println("初始化下载线程池大小为" + poolSize + "...");
        ExecutorService service = Executors.newFixedThreadPool(poolSize);
        CountDownLatch latch = new CountDownLatch(tsList.size());
        ConcurrentHashMap<Integer, byte[]> resultMap = new ConcurrentHashMap<>();
        for (int index = 0; index < tsList.size(); index++) {
            service.submit(new DownloadTask(index, latch, tsList.get(index), resultMap, contextPath));
        }
        try {
            latch.await();
            System.out.println("所有下载线程均已完成...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try (FileOutputStream os = new FileOutputStream("D:/test.mp4")) {
            for (int index = 0; index < tsList.size(); index++) {
                byte[] bytes = resultMap.get(index);
                os.write(bytes);
            }
            System.out.println("分片合并已完成...");
        } catch (IOException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}
