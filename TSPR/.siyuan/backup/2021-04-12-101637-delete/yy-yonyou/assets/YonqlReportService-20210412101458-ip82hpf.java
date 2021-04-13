package com.yonyou.ucf.mdf.app.service;

import com.google.gson.JsonArray;
import com.yonyou.ucf.mdd.common.model.model.ReportResult;

import java.util.Map;

public interface YonqlReportService {

    ReportResult getMetadataByYonql(Map<String, Object> param) throws Exception;
}
