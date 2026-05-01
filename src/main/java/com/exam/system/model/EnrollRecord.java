package com.exam.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 选课记录实体类。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollRecord {

    /**
     * 学生ID，格式：S+6位数字。
     */
    private String studentId;

    /**
     * 课程ID，格式：C+6位数字。
     */
    private String courseId;

    /**
     * 课程名称。
     */
    private String courseName;


    /**
     * 课程类型。
     */
    private String courseType;

    public EnrollRecord(String studentId, String courseId, String courseName) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return String.format("学生ID：%s，课程ID：%s，课程名称：%s", studentId, courseId, courseName);
    }
}
