package com.springboot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
public class ApplyInfo {
    private Integer applyId;
    private Integer stuId;
    private String applyTime;
    private Integer levelId;
    private Integer applyState;
    private String fileName;
}
