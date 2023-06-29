package com.ylhl.phlab.constant;

public class LabConstant {
    //样本状态 未领取样本
    public final static String SAMPLE_STATUS_NO = "0";
    //样本状态 已领取样本
    public final static String SAMPLE_STATUS_YES = "1";

    //数据上传状态 否
    public final static String UPLOAD_DATA_STATUS_NO = "0";
    //数据上传状态 是
    public final static String UPLOAD_DATA_STATUS_YES = "1";

    //检测数据当前状态 待提交
    public final static String EVAL_STATUS_WAIT = "0";
    //检测数据当前状态 待评价
    public final static String EVAL_STATUS_NO = "1";
    //检测数据当前状态 已评价
    public final static String EVAL_STATUS_YES = "2";

    //检测数据评价结果 未评价
    public final static String EVAL_RESULT_WAIT = "0";
    //检测数据评价结果 通过
    public final static String EVAL_RESULT_OK = "1";
    //检测数据评价结果 不通过
    public final static String EVAL_RESULT_NOT_OK = "2";

    //下发计划评价跳转状态 需要评价
    public final static String NEED_EVAL_NEED = "0";
    //下发计划评价跳转状态 不需要评价
    public final static String NEED_EVAL_NOT_NEED = "1";

    //下发计划发布状态 待发布
    public final static String PLAN_STATUS_WAIT = "0";
    //下发计划发布状态 已发布
    public final static String PLAN_STATUS_OK = "1";

}
