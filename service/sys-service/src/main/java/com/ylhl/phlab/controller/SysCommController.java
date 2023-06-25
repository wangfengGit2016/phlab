package com.ylhl.phlab.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.CacheConstants;
import com.ylhl.phlab.domain.SysLogInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.CoreMapper;
import com.ylhl.phlab.utils.ClassUtils;
import com.ylhl.phlab.utils.DocUtils;
import com.ylhl.phlab.utils.SmsMobileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags = "SysCommController",description = "系统-公共")
@Slf4j
@RestController
@RequestMapping("/sys/comm")
public class SysCommController {

    @Resource
    CoreMapper coreMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @ApiOperation("图片验证码")
    @PostMapping("/captcha")
    public JSONObject captcha() {
        JSONObject res = new JSONObject();
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(128, 40, 4, 4);
        String code = shearCaptcha.getCode();
        String uuid = IdUtil.fastSimpleUUID();
        stringRedisTemplate.opsForValue().set(CacheConstants.CAPTCHA_PREFIX + uuid,code,5, TimeUnit.MINUTES);
        res.put("codeKey",uuid);
        res.put("codeValue",code);
        res.put("codePic",shearCaptcha.getImageBase64Data());
        return res;
    }

    @ApiOperation("短信验证码")
    @PostMapping("/sms")
    public JSONObject sms(@RequestBody JSONObject param) {
        JSONObject res = new JSONObject();
        SmsMobileUtils.runService(param);
        return res;
    }

    @ApiOperation("短信验证码")
    @PostMapping("/phone")
    public JSONObject phone(@RequestBody JSONObject param) {
        return SmsMobileUtils.doPhone(param);
    }

    @ApiOperation("批量生产数据")
    @PostMapping("/cs1")
    public JSONObject cs1(@RequestBody JSONObject param) {
        JSONObject res = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        for(int i =0;i<10;i++){
            JSONObject dto = new JSONObject();
            dto.put("id",IdUtil.fastSimpleUUID());
            dto.put("status",1);
            list.add(dto);
        }
        CoreBuilder.insert().saveBatch(list, SysLogInfo.class);
        return res;
    }

    @ApiOperation(value = "类生成",hidden = true)
    @PostMapping(value = "/class")
    public JSONObject newClass(@RequestBody JSONObject param) {
        JSONObject res = new JSONObject();
        String basePackage=  param.getString("basePackage");
        if(StringUtils.isNotBlank(param.getString("tableName") )){
            String module=  param.getString("module");
            if(StringUtils.isNotBlank(module)){
                module="."+module;
            }
            String className=  param.getString("className");
            String tableName=  param.getString("tableName");
            List<JSONObject> columns = coreMapper.showColumns(tableName);
            ClassUtils.build(module,basePackage,className,tableName,columns);
//            ClassUtils.buildService(module,basePackage);
//            ClassUtils.buildServiceImpl(module,basePackage,className,columns);
        }else {
            List<String> tables=coreMapper.showTables();
            System.out.println(tables);
            tables.forEach(item->{
                if(StringUtils.isBlank(param.getString("prefix"))||item.startsWith(param.getString("prefix"))){
                    String module=  param.getString("module");
                    if(StringUtils.isNotBlank(module)){
                        module="."+module;
                    }
                    String className=  ClassUtils.humpToLine(item);
                    List<JSONObject> columns = coreMapper.showColumns(item);
                    ClassUtils.build(module,basePackage,className,item,columns);
//                ClassUtils.buildService(module,basePackage);
                ClassUtils.buildServiceImpl(module,basePackage,className,columns);
                }
            });
        }



        return res;
    }



    @ApiOperation(value = "文档生成",hidden = true)
    @GetMapping(value="/doc")
    public void doc(HttpServletRequest request, HttpServletResponse response) throws IOException {
        XWPFDocument document = new XWPFDocument(new FileInputStream(System.getProperty("user.dir")+"\\doc\\tmp.docx"));
        List<JSONObject> tables=coreMapper.showTableInfo("phlab");
        DocUtils.buildObjectApi(document);
        DocUtils.buildDataDesign(document,tables,coreMapper);
        DocUtils.writeDoc(response,document,"tmp.docx");
    }

    @ApiOperation(value = "文档生成",hidden = true)
    @GetMapping(value="/doc.html")
    public String docHtml(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<!DOCTYPE>\n");
        stringBuffer.append("<html lang=\"zh\">\n");
        stringBuffer.append("<head>\n");
        stringBuffer.append("<title>接口文档</title>\n");
        stringBuffer.append("<style>\n");
        stringBuffer.append("h2{\n");

        stringBuffer.append("}\n");

        stringBuffer.append("</style>\n");
        stringBuffer.append("</head>\n");
        stringBuffer.append("<body>\n");
        stringBuffer.append("<h2>临平民政接口文档</h2>\n");
        StringBuffer leftDiv = new StringBuffer();
        StringBuffer rightDiv = new StringBuffer();
        Map<String,Object> map= SpringUtil.getApplicationContext().getBeansWithAnnotation(Api.class);
        map.forEach((k,v)->{
            Class<?> clazz = v.getClass();
            Api api = AnnotationUtils.findAnnotation(clazz,Api.class);
            stringBuffer.append("<a>模块名："+api.description()+"</a><br>\n");
            RequestMapping requestMapping= AnnotationUtils.findAnnotation(clazz,RequestMapping.class);

            Method[] methods = clazz.getDeclaredMethods();
            for(Method method:methods){
                PostMapping postMapping =AnnotationUtils.findAnnotation(method,PostMapping.class);
                GetMapping getMapping =AnnotationUtils.findAnnotation(method,GetMapping.class);
                if(postMapping!=null){
                    stringBuffer.append("<span>接口地址："+requestMapping.value()[0]+postMapping.value()[0]+"</span><br>");
                    stringBuffer.append("<span>接口类型：POST</span><br>\n");
                }else if(getMapping!=null){
                    stringBuffer.append("<span>接口地址："+requestMapping.value()[0]+getMapping.value()[0]+"</span><br>");
                    stringBuffer.append("<span>接口类型：GET</span><br>\n");
                }
                ApiOperation apiOperation = AnnotationUtils.findAnnotation(method,ApiOperation.class);
                if(apiOperation!=null){
                    stringBuffer.append("<span>接口描述："+apiOperation.value()+"</span><br>");
                }
            }
            stringBuffer.append("<hr/>");
        });

        stringBuffer.append("</body>\n");
        stringBuffer.append("</html>\n");
        return stringBuffer.toString();
    }

}
