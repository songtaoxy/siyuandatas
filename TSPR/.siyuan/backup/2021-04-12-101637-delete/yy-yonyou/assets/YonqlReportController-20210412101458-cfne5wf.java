package com.yonyou.ucf.mdf.app.controller.reportform;

import com.google.gson.JsonArray;
import com.yonyou.metadata.base.StructuredClassifier;
import com.yonyou.metadata.utils.BaseType2Json;
import com.yonyou.ucf.mdd.common.model.model.ReportResult;
import com.yonyou.ucf.mdf.app.controller.BaseController;
import com.yonyou.ucf.mdf.app.service.YonqlReportService;
import com.yonyou.ucf.mdf.app.util.ExceptionUtil;
import com.yonyou.ucf.mdf.app.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: st
 * @date: 2021/4/7 13:58
 * @version: 1.0
 * @description:
 */

@RestController
@RequestMapping("/v1")
@Slf4j
public class YonqlReportController extends BaseController {

    @Qualifier("yonqlReportServiceImpl")
    @Autowired
    private YonqlReportService yonqlReportService;

    @RequestMapping("/yonql")
    public ReportResult query(@RequestBody Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            ReportResult reportResult = yonqlReportService.getMetadataByYonql(param);
            return reportResult;
        } catch (Exception e) {
            log.error("ReportQuery", e);
            ReportResult reportResult = new ReportResult();
            reportResult.setMessage(ExceptionUtil.getRealMessage(e));
            reportResult.setSuccess(false);

            if (null != param) {
                String msgError = (String) ((Map) (((Map) param.get("params")).get("msgError"))).get("serviceCode");
                if (StringUtils.isNotEmpty(msgError)) {
                    response.sendError(Integer.parseInt(ResultCode.EXCEPTION_CODE), ExceptionUtil.getRealMessage(e));
                }
            }
            return reportResult;
        }
    }

}