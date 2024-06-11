package io.goji.biz.maner.repo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.goji.biz.maner.model.MetaEntity;
import io.goji.biz.maner.model.ProjectInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MetaEntityMapper extends BaseMapper<MetaEntity> {

    @Insert("""
                        insert into meta_entity(project_id, version, version_code, update_content, update_time)
                        values(#{projectId}, #{version}, #{versionCode}, #{updateContent}, #{updateTime})
            """)
    boolean addMetaEntity(MetaEntity metaEntity);


    @Select("""
                        select * from meta_entity where project_id = #{projectId}
                        order by update_time desc
                        limit 1
            
""")
    MetaEntity getNewestMetaEntity(String projectId);



    @Select("select * from meta_entity where project_id = #{projectId}")
    List<MetaEntity> getMetaEntityList();

    @Delete("delete from meta_entity where project_id = #{projectId}")
    int deleteMetaEntityByProjectId(String projectId);


}
