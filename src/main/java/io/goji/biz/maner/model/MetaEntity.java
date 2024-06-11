package io.goji.biz.maner.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableCharset;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableEngine;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlCharsetConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Schema(name = "MetaEntity", description = "元数据实体")
@Data
@AllArgsConstructor
@Builder
@TableName("meta_entity")
@Table(name = "meta_entity")
@TableCharset(MySqlCharsetConstant.UTF8)
@TableEngine(MySqlEngineConstant.InnoDB)
public class MetaEntity {

    @Schema(description = "项目ID")
    @Column(name = "project_id")
    String projectId;

    @Schema(description = "版本号")
    @Column
    String version;

    @Schema(description = "版本代码")
    @Column
    int versionCode;


    @Schema(description = "更新内容")
    @Column(name = "update_content")
    String updateContent;

    @Schema(description = "更新时间")
    @Column(name = "update_time")
    String updateTime;

}
