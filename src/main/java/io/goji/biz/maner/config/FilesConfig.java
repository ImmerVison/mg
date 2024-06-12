//package io.goji.biz.maner.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//
//import java.io.File;
//
//@Configuration
//public class FilesConfig {
//
//    // 文件存储目录名
//    @Value("${app.files.dir}")
//    private String dir;
//
//    // 文件存储目录
//    @Bean("filesDir")
//    @Lazy
//    public File filesDir() {
//        return new File(dir);
//    }
//}
