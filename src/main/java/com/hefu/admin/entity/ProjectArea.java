package com.hefu.admin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class ProjectArea implements Serializable {
    private static final long serialVersionUID = 5926438336964067340L;

    private String area;

    private Integer count;
}
