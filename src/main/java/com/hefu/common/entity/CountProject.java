package com.hefu.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountProject implements Serializable {
    private Integer projectId;
    private String projectName;
    private int count;
}