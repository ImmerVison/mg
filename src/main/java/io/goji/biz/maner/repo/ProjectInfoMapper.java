package io.goji.biz.maner.repo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.goji.biz.maner.model.ProjectInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProjectInfoMapper extends BaseMapper<ProjectInfo> {

    @Select("select * from project_info")
    List<ProjectInfo> getProjectList();

    @Delete("delete from project_info where project_id = #{projectId}")
    int deleteProjectById(@Param("projectId") String projectId);

    @Insert("""
                        insert into project_info(project_id, name)
                         values(#{projectId}, #{name})
            """)
    boolean addProject(ProjectInfo projectInfo);


    @Update("""
                        update project_info
                        set name = #{name}
                        where project_id = #{projectId}
            """)
    void updateName(ProjectInfo newInfo);


    @Select("select * from project_info where project_id = #{projectId}")
    ProjectInfo getProjectById(String projectId);


    @Select("select * from project_info where name = #{projectName}")
    ProjectInfo getProjectByName(String projectName);


}
