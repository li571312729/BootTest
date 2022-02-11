package com.hefu.admin.vo.response;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lifenxing
 * @date 2020/9/21 17:50
 */
@Data
public class FileResponseVO {
    @ApiModelProperty("文件id")
    private Long id;

    @TableField(value = "`name`")
    @ApiModelProperty("文件名")
    private String name;

    @ApiModelProperty("文件大小")
    private Integer size;

    @ApiModelProperty("高")
    private Integer height;

    @ApiModelProperty("宽")
    private Integer width;

    @ApiModelProperty("文件类型(1:图片,2:pdf,3:视频)")
    private Integer type;

    @ApiModelProperty("文件后缀名")
    private String suffix;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
