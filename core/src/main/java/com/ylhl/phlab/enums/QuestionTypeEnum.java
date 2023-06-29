package com.ylhl.phlab.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionTypeEnum {
    ONE_CHOOSE (0, "单选题"),
    MORE_CHOOSE (1, "多选题"),

    TRUE_OR_FALSE (2,"判断题"),
    FILL_BLANK (3, "填空题"),
    SUBJECTIVE (4, "主观题");

    private final Integer code;
    private final String type;
}
