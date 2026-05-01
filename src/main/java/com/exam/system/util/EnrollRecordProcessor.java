package com.exam.system.util;

import com.exam.system.model.EnrollRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 学生选课基础处理工具。
 */
public class EnrollRecordProcessor {

   /**
     * 处理选课记录：去重、排序、输出
     *
     * @param records 原始选课记录列表
     * @return 处理后的选课记录列表
     */
    public List<EnrollRecord> processRecords(List<EnrollRecord> records) {
        // 1. 边界条件防御
        if (records == null || records.isEmpty()) {
            return new ArrayList<>();
        }

        // 2. 定义排序和去重规则
        // 规则：先按学生ID升序，再按课程ID升序
        Comparator<EnrollRecord> comparator = Comparator
                .comparing(EnrollRecord::getStudentId)
                .thenComparing(EnrollRecord::getCourseId);

        // 3. 使用 TreeSet 自动实现去重和排序
        // TreeSet 发现 comparator 返回 0 时（即学生ID和课程ID完全一致），会自动抛弃后加入的重复记录，从而忽略课程名称的差异
        Set<EnrollRecord> processedSet = new TreeSet<>(comparator);
        processedSet.addAll(records);

        // 4. 将处理后的 Set 转换回 List
        List<EnrollRecord> resultList = new ArrayList<>(processedSet);

        // 5. 遍历输出要求格式的信息（利用实体类中已重写的 toString 方法）
        for (EnrollRecord record : resultList) {
            System.out.println(record.toString());
        }

        return resultList;
    }

/**
 * 测试 Main 方法
 */
// public static void main(String[] args) {
    
// }
}
