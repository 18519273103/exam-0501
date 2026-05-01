package com.exam.system.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.exam.system.model.EnrollRecord;
import com.exam.system.service.EnrollService;



@Service
public class EnrollServiceImpl implements EnrollService {

    private final Set<EnrollRecord> dataStore = new TreeSet<>(Comparator
            .comparing(EnrollRecord::getStudentId)
            .thenComparing(EnrollRecord::getCourseId));

    @Override
    public int batchImport(List<EnrollRecord> records) {
        if (records == null || records.isEmpty()) return 0;
        
        int initialSize = dataStore.size();
        for (EnrollRecord record : records) {
            // 如果前端没有传类型（手动标注为空），则触发自动识别
            if (!StringUtils.hasText(record.getCourseType())) {
                record.setCourseType(autoClassify(record.getCourseName()));
            }
            dataStore.add(record);
        }
        // 返回实际成功插入（去重后）的条数
        return dataStore.size() - initialSize;
    }
    
    @Override
    public boolean updateCourseType(String studentId, String courseId, String newType) {
        for (EnrollRecord record : dataStore) {
            if (record.getStudentId().equals(studentId) && record.getCourseId().equals(courseId)) {
                record.setCourseType(newType);
                return true;
            }
        }
        return false;
    }

    /**
     * 辅助功能：简单的自动分类算法（基于关键字命中）
     */
    private String autoClassify(String courseName) {
        if (!StringUtils.hasText(courseName)) return "未知类型";
        
        String name = courseName.toLowerCase();
        if (name.contains("数学") || name.contains("英语") || name.contains("政治")) {
            return "公共课";
        } else if (name.contains("体育") || name.contains("音乐") || name.contains("艺术")) {
            return "选修课";
        } else {
            // 默认归类为专业课
            return "专业课";
        }
    }


    @Override
    public List<EnrollRecord> search(String keyword, String courseType) {
        // 如果关键字为空，返回全部排序好的列表
        if (!StringUtils.hasText(keyword)) {
            return new ArrayList<>(dataStore);
        }

        // 使用 Java 8 Stream API 进行全量检索，1000条数据过滤仅需几毫秒
        String lowerKeyword = keyword.toLowerCase();
        return dataStore.stream()
                .filter(record -> 
                        record.getStudentId().toLowerCase().contains(lowerKeyword) ||
                        record.getCourseId().toLowerCase().contains(lowerKeyword) ||
                        record.getCourseName().toLowerCase().contains(lowerKeyword) ||
                        (record.getCourseType() != null && record.getCourseType().contains(lowerKeyword))
                )
                .collect(Collectors.toList());
    }
}
