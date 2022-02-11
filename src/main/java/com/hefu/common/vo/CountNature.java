package com.hefu.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统计字段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountNature implements Serializable {
    private Object natureId;
    private String nature;
    private int count;
    private String imgUrl;

    public CountNature(String nature, int count) {
        this.nature = nature;
        this.count = count;
    }
}
