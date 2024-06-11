package io.goji.biz.maner.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.goji.biz.maner.common.BaseResponse;
import io.goji.biz.maner.common.Constants;
import io.goji.biz.maner.model.MetaEntity;
import io.goji.biz.maner.repo.MetaEntityMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@Service
public class MetaEntityService {

    @Resource
    private final MetaEntityMapper metaEntityMapper;


    public BaseResponse<Boolean> addMetaEntity(MetaEntity metaEntity) {
        metaEntityMapper.addMetaEntity(metaEntity);
        return BaseResponse.success("MetaEntity added successfully", true);
    }


    public BaseResponse<MetaEntity> getNewestMetaEntity(String projectId) {
        MetaEntity newestMetaEntity = metaEntityMapper.getNewestMetaEntity(projectId);
        return newestMetaEntity == null ? BaseResponse.fail("No MetaEntity found", null) : BaseResponse.success(newestMetaEntity);
    }


    public BaseResponse<List<MetaEntity>> getAllMetaEntity(String project) {
        List<MetaEntity> metaEntityList = metaEntityMapper.getMetaEntityList();
        return metaEntityList.isEmpty() ? BaseResponse.fail("No MetaEntity found", null) : BaseResponse.success(metaEntityList);
    }

    public BaseResponse<Boolean> uploadMetaEntity(String projectId, MultipartFile file) {
        Path uploadPath = Path.of(Constants.RESOURCE_PATH);
        if (!uploadPath.toFile().exists()) {
            try {
                Files.createDirectory(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
                log.info("Failed to create the directory: {}", uploadPath);
                return BaseResponse.fail("Failed to create the directory", false);

            }
        }
        Path originFile = uploadPath.resolve(projectId);
        try (Stream<Path> folders = Files.list(uploadPath)) {
            folders.filter(path -> path.getFileName().toString().equals(projectId))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            log.info("Failed to delete the file {}", path);
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Failed to delete the file");
            return BaseResponse.fail("Failed to delete the file", false);
        }

        try {
            file.transferTo(originFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BaseResponse.success("MetaEntity uploaded successfully", true);

    }


    public BaseResponse<Void> metaInfoDownLoad(String projectId, HttpServletResponse response) {
        Path downloadPath = Path.of(Constants.RESOURCE_PATH);
        Path file = downloadPath.resolve(projectId);
        if (!file.toFile().exists()) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            try {
                response.getWriter().write(new ObjectMapper()
                        .writeValueAsString(BaseResponse.fail("The file does not exist", null)));
            } catch (IOException e) {
                e.printStackTrace();
//                throw new RuntimeException(e);
            }
        }
        try {
            response.setContentType("application/force-download");
            response.addHeader("Content-Length", "" + Files.size(file));
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getFileName().toString(), StandardCharsets.UTF_8));
            // the file may be too large to fit into memory, so we need to use the following code
            Files.copy(file, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


}
