package com.exam.system.service;

import java.util.List;

import com.exam.system.model.EnrollRecord;

public interface EnrollService {

    /**
     * 批量导入
     * @param records
     */
    public int batchImport(List<EnrollRecord> records);

    /**
     * 检索
     * @param keyword
     * @return
     */
    public List<EnrollRecord> search(String keyword, String courseType);

    /**
     * 手动更新课程分类
     * @param studentId
     * @param courseId
     * @param newType
     * @return
     */
    public boolean updateCourseType(String studentId, String courseId, String newType);

    
}
