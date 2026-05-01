package com.exam.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exam.system.model.EnrollRecord;
import com.exam.system.response.ApiResponse;
import com.exam.system.service.EnrollService;

public class EnrollController {
    @Autowired
    private EnrollService enrollService;

    /**
     * 1. 批量导入接口
     * POST /api/enroll/import
     */
    @PostMapping("/import")
    public ApiResponse<String> importRecords(@RequestBody List<EnrollRecord> records) {
        int addedCount = enrollService.batchImport(records);
        return ApiResponse.success("批量导入完成，有效新增记录: " + addedCount + " 条", null);
    }

    /**
     * 2. 综合检索接口
     * GET /api/enroll/search?keyword=xxx&courseType=xxx
     */
    @GetMapping("/search")
    public ApiResponse<List<EnrollRecord>> searchRecords(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String courseType) {
        
        List<EnrollRecord> results = enrollService.search(keyword, courseType);
        
        // 核心：处理找不到数据时的提示信息
        if (results.isEmpty()) {
            return ApiResponse.success("无匹配选课记录", results);
        }
        return ApiResponse.success("查询成功", results);
    }

    /**
     * 3. 手动修改分类接口
     * PUT /api/enroll/type
     */
    @PutMapping("/type")
    public ApiResponse<Void> updateType(
            @RequestParam String studentId,
            @RequestParam String courseId,
            @RequestParam String courseType) {
            
        boolean isSuccess = enrollService.updateCourseType(studentId, courseId, courseType);
        if (isSuccess) {
            return ApiResponse.success("分类更新成功", null);
        } else {
            return ApiResponse.error("未找到对应的选课记录，更新失败");
        }
    }
}
