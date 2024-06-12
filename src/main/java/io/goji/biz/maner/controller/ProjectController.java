package io.goji.biz.maner.controller;

import io.goji.biz.maner.common.BaseResponse;
import io.goji.biz.maner.model.ProjectInfo;
import io.goji.biz.maner.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Tag(name = "项目管理", description = "项目管理接口")
@RestController
@RequestMapping("projects")
public class ProjectController {

    @Resource
    private final ProjectService projectService;



    @GetMapping
    @Operation(summary = "获取项目列表", description = "获取项目列表")
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "获取项目列表")
    public ResponseEntity<BaseResponse<List<ProjectInfo>>> getProjectList() {
        return ResponseEntity.ok(projectService.getProjectList());
    }


    @Operation(
            summary = "新增项目",
            parameters = {
                @Parameter(name = "projectName", description = "项目名称", required = true, example = "项目1")
            }
    )
    @PostMapping("{projectName}")
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "新增项目")
    public ResponseEntity<BaseResponse<Boolean>> addProject(@PathVariable("projectName") String projectName) {
        return ResponseEntity.ok(projectService.addProject(projectName));
    }


    @Operation(
            summary = "删除项目",
            parameters = {
                    @Parameter(name = "projectId", description = "项目ID", required = true, example = "1")
            }
    )
    @DeleteMapping("{projectId}")
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "删除项目")
    public ResponseEntity<BaseResponse<Boolean>> deleteProject(@PathVariable("projectId") String projectId) {
        log.info("删除项目...");
        System.out.println("删除项目...ddd");
        return ResponseEntity.ok(projectService.deleteProject(projectId));
    }




    @Operation(
            summary = "更新项目名称",
            parameters = {
                    @Parameter(name = "projectId", description = "项目ID", required = true, example = "1"),
                    @Parameter(name = "projectName", description = "项目名称", required = true, example = "项目1")
            }
    )
    @PutMapping("{projectId}/{projectName}")
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "更新项目名称")
    public ResponseEntity<BaseResponse<Boolean>> updateName(
            @PathVariable("projectId") String projectId,
            @PathVariable("projectName")String projectName) {
        return ResponseEntity.ok(projectService.updateProject(projectId, projectName));
    }



}
