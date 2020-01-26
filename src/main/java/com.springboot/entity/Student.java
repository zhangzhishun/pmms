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
public class Student {
    private Integer stuId;
    private String stuPassword;
    private String stuName;
    private String stuSex;
    private String stuPhoto;
    private String stuOriginPlace;
    private Integer stuClassId;
    private String stuContactInformation;
    private String stuAddress;
    private Integer stuPartyDues;
}
