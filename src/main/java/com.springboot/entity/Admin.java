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
public class Admin {
    private Integer admId;
    private String admName;
    private String admPassword;
    private Integer admLevel;
}
