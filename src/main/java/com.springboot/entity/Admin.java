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
public class Admin {
    private Integer admId;
    private String admName;
    private String admPassword;
    private Integer admLevel;
}
