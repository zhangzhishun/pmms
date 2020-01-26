package com.springboot.entity;

import lombok.*;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor                 //无参构造
@AllArgsConstructor                //有参构造
public class ApplyInfo {
    private Integer applyId;
    private Integer stuId;
    private String applyTime;
    private Integer levelId;
    private Integer applyState;
    private String fileName;
}
