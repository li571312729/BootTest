package com.hefu.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author lifenxing
 * @date 2020/9/21 17:48
 */
@SuppressWarnings("serial")
@Data
public class SysFile {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件名
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * fastdfsKEY值
     */
    @TableField(value ="`key`")
    private String key;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 下载次数
     */
    private Integer downloads;

    /**
     * 高
     */
    private Integer height;

    /**
     * 宽
     */
    private Integer width;

    /**
     * 文件类型(1:图片,2:pdf,3:视频)
     */
    private Integer type;

    /**
     * 文件后缀名
     */
    private String suffix;

    /**
     * 创建时间
     */
    private Date createTime;
}
