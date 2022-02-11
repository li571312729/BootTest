package com.hefu.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountDept implements Serializable {
    private Integer webDeptId;
    private String webDeptName;
    private int count;
}
