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
public class StuClass {
    private Integer stuClassId;
    private String stuClassName;
    private Integer majorId;
    private Integer teaId;
}
