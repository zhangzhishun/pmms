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
public class File {
    private Integer fileType;
    private Integer stuId;
    private String filePath;
    private String fileName;
    private String fileTime;
}
