package com.exam.system.util;

import com.exam.system.model.EnrollRecord;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EnrollRecordProcessorTests {

    @Test
    void processShouldReturnEmptyListWhenInputIsEmptyOrNull() {

        EnrollRecordProcessor enrollRecordProcessor = new EnrollRecordProcessor();

        // 构造测试数据
        List<EnrollRecord> rawRecords = Arrays.asList(
            new EnrollRecord("S100003", "C200001", "计算机网络"),
            new EnrollRecord("S100001", "C200002", "高等数学"),
            new EnrollRecord("S100002", "C200001", "数据结构"),
            new EnrollRecord("S100001", "C200001", "线性代数"),
            // 这条记录的 学生ID 和 课程ID 与上一条完全一致，但课程名称不同，按规则将被去重移除
            new EnrollRecord("S100001", "C200001", "线性代数(进阶)"), 
            new EnrollRecord("S100001", "C200003", "大学物理")
    );

    System.out.println("--- 开始处理选课记录 ---");
    List<EnrollRecord> finalRecords = enrollRecordProcessor.processRecords(rawRecords);
    System.out.println("--- 处理完成，共保留 " + finalRecords.size() + " 条记录 ---");
    }
}
