package com.ylhl.phlab.consts;

import lombok.Data;

@Data
public class CommonConstant {
    // 题目序号起始值
    public static final Integer NUM_START = 1;
    // 题目序号增加值
    public static final Integer NUM_STEP = 1;
    // 标签业务类型: 0 题目
    public static final Integer QUESTION_TYPE = 0;
    // 标签业务类型: 0 试卷
    public static final Integer PAPER_TYPE = 1;
    // 标签业务类型: 0 课程
    public static final Integer COURSE_TYPE = 2;

}
