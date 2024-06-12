package io.goji.biz.maner.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.goji.biz.maner.common.BaseResponse;
import io.goji.biz.maner.common.Constants;
import io.goji.biz.maner.model.ProjectInfo;
import io.goji.biz.maner.repo.MetaEntityMapper;
import io.goji.biz.maner.repo.ProjectInfoMapper;
import io.goji.biz.maner.utility.SnowflakeId;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
@Slf4j
@Service
@AllArgsConstructor
public class ProjectService {

    @Resource
    private final ProjectInfoMapper projectInfoMapper;

    @Resource
    private final MetaEntityMapper metaEntityMapper;

    @Resource
    private final SnowflakeId snowflakeId;


    public BaseResponse<List<ProjectInfo>> getProjectList() {
        var queryWrapper = new LambdaQueryWrapper<ProjectInfo>();
        var projectInfoList = projectInfoMapper.selectList(queryWrapper);
        log.info("projectInfoList: {}", projectInfoList);
        return projectInfoList.isEmpty() ? BaseResponse.success(Collections.emptyList()) : BaseResponse.success(projectInfoList);
    }

    public BaseResponse<Boolean> addProject(String projectName) {
        ProjectInfo projectByName = projectInfoMapper.getProjectByName(projectName);
        if(projectByName != null){
            return BaseResponse.fail(HttpStatus.CONFLICT, "项目名称已存在", false);
        }

        ProjectInfo newProject = ProjectInfo
                .builder()
                .projectId(snowflakeId.generateId(Constants.WORKER_ID))
                .name(projectName)
                .build();
        log.info("newProject is {}", newProject);
        projectInfoMapper.addProject(newProject);
        return BaseResponse.success("新增成功", true);
    }


public BaseResponse<Boolean> deleteProject(String projectId){
        metaEntityMapper.deleteMetaEntityByProjectId(projectId);
        Path toBeDeleted = Path.of(Constants.RESOURCE_PATH);
        if(!toBeDeleted.toFile().exists() || !toBeDeleted.toFile().isDirectory()){
           return BaseResponse.fail("资源目录不存在", false);
        }
        try {
            Path projectPath = toBeDeleted.resolve(projectId);
            if(Files.exists(projectPath)){
                Files.delete(projectPath);
            }
        } catch (Exception e){
            return BaseResponse.fail("删除失败", false);
        }
        return BaseResponse.success("删除成功", true);
    }


    public BaseResponse<Boolean> updateProject(String projectId, String projectName) {
        ProjectInfo projectInfo = projectInfoMapper.getProjectById(projectId);
        if(Objects.isNull(projectInfo)){
            return BaseResponse.fail("项目不存在", false);
        }
        ProjectInfo projectByName = projectInfoMapper.getProjectByName(projectName);
        if(projectByName != null){
            return BaseResponse.fail(HttpStatus.CONFLICT, "项目名称已存在", false);
        }
        projectInfoMapper.updateName(
                ProjectInfo
                        .builder()
                        .projectId(projectId)
                        .name(projectName)
                        .build()
        );
        return BaseResponse.success("更新成功", true);
    }



}
