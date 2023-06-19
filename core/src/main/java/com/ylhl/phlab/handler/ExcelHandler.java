package com.ylhl.phlab.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelHandler {
    /**
     *
     * @param file 文件
     * @param type 文件类型（业务类型）
     * @param id 业务id（可不传）
     */
    JSONObject doService(MultipartFile file, String type, String id);
}
