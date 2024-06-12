package io.goji.biz.maner.controller;

import io.goji.biz.maner.common.BaseResponse;
import io.goji.biz.maner.model.MetaEntity;
import io.goji.biz.maner.service.MetaEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/meta")
@Tag(name = "元数据管理", description = "元数据管理接口")
public class MetaInfoController {

    @Resource
    private final MetaEntityService metaInfoService;

    @Operation(summary = "新增元数据", description = "新增元数据")
    @PostMapping
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "新增元数据")
    public ResponseEntity<BaseResponse<Boolean>>  addMetaInfo(@RequestBody MetaEntity newInfo) {
        return ResponseEntity.ok(metaInfoService.addMetaEntity(newInfo));
    }


    @Operation(summary = "上传元数据", description = "上传元数据")
    @PostMapping("upload/{projectId}")
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "上传元数据")
    @Synchronized
    public ResponseEntity<BaseResponse<Boolean>> uploadInfo(
            @PathVariable("projectId") String projectId,
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(metaInfoService.uploadMetaEntity(projectId, file));

    }




    @Operation(summary = "获取最新元数据", description = "获取最新元数据")
    @GetMapping("newest/{projectId}")
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "获取最新元数据")
    public ResponseEntity<BaseResponse<MetaEntity>> getNewestMetaInfo(@PathVariable("projectId") String projectId) {
        return ResponseEntity.ok(metaInfoService.getNewestMetaEntity(projectId));
    }

    @Operation(summary = "获取项目所有元数据", description = "获取项目所有元数据")
    @GetMapping("all/{projectId}")
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "获取项目所有元数据")
    public ResponseEntity<BaseResponse<List<MetaEntity>> > getAllMetaInfo(@PathVariable("projectId") String projectId) {
        return ResponseEntity.ok(metaInfoService.getAllMetaEntity(projectId));
    }


    @Operation(summary = "下载元数据", description = "下载元数据")
    @GetMapping("download/{projectId}")
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "下载元数据")
    public ResponseEntity<BaseResponse<Void>> metaInfoDownload(@PathVariable("projectId") String projectId, HttpServletResponse response) {
        return ResponseEntity.ok(metaInfoService.metaInfoDownLoad(projectId, response));
    }



}
