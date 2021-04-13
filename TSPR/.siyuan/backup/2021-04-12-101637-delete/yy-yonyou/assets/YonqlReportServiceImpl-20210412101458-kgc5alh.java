package com.yonyou.ucf.mdf.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.yonyou.cloud.hpapaas.yonql.SearchDao;
import com.yonyou.cloud.hpapaas.yonql.domain.DbType;
import com.yonyou.cloud.hpapaas.yonql.query.YonQLResult;
import com.yonyou.diwork.service.auth.IServiceIsolateService;
import com.yonyou.ucf.mdd.common.context.MddBaseContext;
import com.yonyou.ucf.mdd.common.model.Pager;
import com.yonyou.ucf.mdd.common.model.model.ReportResult;
import com.yonyou.ucf.mdd.common.model.uimeta.UIMetaBaseInfo;
import com.yonyou.ucf.mdd.common.utils.Toolkit;
import com.yonyou.ucf.mdd.core.meta.MddMetaDaoHelper;
import com.yonyou.ucf.mdf.app.service.YonqlReportService;
import com.yonyou.ucf.mdf.app.util.AliasStruct;
import com.yonyou.ucf.mdf.app.util.MddReportDataPermissions;
import com.yonyou.ucf.mdf.app.util.ReportSqlUtils;
import com.yonyou.ucf.mdf.domain.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.imeta.biz.base.BizContext;
import org.imeta.core.base.ConditionOperator;
import org.imeta.core.model.Entity;
import org.imeta.core.model.Property;
import org.imeta.orm.base.Json;
import org.imeta.orm.query.parser.QuerySchemaBuilder;
import org.imeta.orm.schema.ConditionExpression;
import org.imeta.orm.schema.QueryCondition;
import org.imeta.orm.schema.QueryField;
import org.imeta.orm.schema.QuerySchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: st
 * @date: 2021/4/9 10:44
 * @version: 1.0
 * @description:
 */
@Service("yonqlReportServiceImpl")
@Slf4j
public class YonqlReportServiceImpl implements YonqlReportService {

    @Resource
    private IServiceIsolateService serviceIsolateService;

    @Resource
    private MddReportDataPermissions mddReportDataPermissions;

    @Value("${metadata.service.api}")
    private String serviceUri;



    @Override
    public ReportResult getMetadataByYonql(Map<String, Object> param) throws Exception {

        try {
            long l = System.currentTimeMillis();

            QuerySchema schema;
            String fullName;

            Object sqlStatment = param.get("sqlStatment");
            if (sqlStatment != null && StringUtils.isNotEmpty(sqlStatment.toString())){
                SearchDao searchDao = new SearchDao();
                YonQLResult result = searchDao.explain(sqlStatment.toString(), DbType.MYSQL);
                schema = result.getQuerySchema();
                fullName = schema.fullname();
            }else {
                String json = JSON.toJSONString(param);
                log.info("queryForReport json:{}", json);
                schema = QuerySchemaBuilder.fromJson(new Json(json));
                if (null == param.get("entity")) {
                    throw new IllegalArgumentException("entity must not be null");
                }
                fullName =param.get("entity").toString();
                Entity entity = null;
                if(isUnpublished(param, fullName)){
                    entity = UndefineBaseCache.UnpublishedEntity(serviceUri, fullName);
                }else {
                    entity = BizContext.getMetaRepository().entity(fullName);
                }

                if (null == entity) {
                    throw new IllegalArgumentException("entity is null");
                }
                if ("Report".equals(entity.get("modelType")) && null == entity.get("tableName")) {
                    schema.setInnerView(ReportSqlUtils.getReportSql(serviceUri, entity, param, new AliasStruct()).sql().toString());
                }
                //自定义组织权限 condition填充
                String serviceCode = null;
                if(null != param.get("params") && null != ((Map)param.get("params")).get("ctx") &&
                        null != ((Map)((Map)param.get("params")).get("ctx")).get("serviceCode")){

                    serviceCode= (String)((Map)((Map)param.get("params")).get("ctx")).get("serviceCode");
                }else if(null != param.get("params") && null != ((Map)param.get("params")).get("bz") &&
                        null != ((Map)((Map)param.get("params")).get("bz")).get("serviceCode")){

                    serviceCode= (String)((Map)((Map)param.get("params")).get("bz")).get("serviceCode");
                }
                if(StringUtils.isNotEmpty(serviceCode)){
                    joinOrgId(entity, schema, serviceCode);
                    String sysCode = null;
                    if ("Report".equals(entity.get("modelType"))) {
                        sysCode = "custome-report";
                    }
                    mddReportDataPermissions.handleQuerySchema(param.get("entity").toString(), serviceCode, sysCode, schema);
                }
            }

            UIMetaBaseInfo billContext = new UIMetaBaseInfo();
            billContext.setFullname(fullName);
            billContext.setbRowAuthControl(true);


            Pager pager = null;
            Object[] result = null;
            List<Map<String, Object>> results = null;
            MddMetaDaoHelper helper = MddBaseContext.getBean(MddMetaDaoHelper.class);
            Long startTime = System.currentTimeMillis();
            pager = helper.queryByPage(billContext, schema);
            log.info("========query接口耗时: {} ========", (System.currentTimeMillis() - startTime));

            if (null == pager) {
                return new ReportResult();
            }
            results = pager.getRecordList();


            if (null != results && results.size() > 0) {
                List<Object> values = new ArrayList<>();
                for (Map<String, Object> ret : results) {
                    List<Object> value = new ArrayList<>();
                    for (QueryField field : schema.selectFields()) {
                        String fieldname = "";
                        if (!Toolkit.isEmpty(field.alias())) {
                            fieldname = field.alias();
                        } else {
                            fieldname = field.name().replaceAll(".", "_");
                        }
                        //兼容翻译器和pg湖里对返回结果集 大小写的转换
                        Object fieldValue = null == ret.get(fieldname) ? ret.get(fieldname.toLowerCase()): ret.get(fieldname);
                        value.add(fieldValue);
                    }
                    values.add(value);
                }
                result = values.toArray();
            }
            log.info("queryForReportTime:{}", System.currentTimeMillis() - l);

            if (sqlStatment != null && StringUtils.isNotEmpty(sqlStatment.toString())){
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("fullName", fullName);
                resultMap.put("fieldNames", schema.selectFields().stream().map(QueryField::name).collect(Collectors.toList()));
                resultMap.put("fieldAlias", schema.selectFields().stream().map(QueryField::alias).collect(Collectors.toList()));
                resultMap.put("values", results);
                return new ReportResult(resultMap, pager);
            }
            return new ReportResult(result, pager);
        } catch (Exception e) {
            log.error("queryForReport", e);
            throw new Exception(e);
        }
    }

    /**
     * 实体组织id字段存在时  拼接组织权限过滤
     * */
    public void joinOrgId(Entity entity, QuerySchema schema, String serviceCode){
        List<Property> propertyList = entity.attributes();
        propertyList.forEach(property->{
            Boolean bol = (Boolean) property.get("isMasterOrg");
            String isMasterOrg = String.valueOf(bol);
            String isMasterOrgContant = "true";
            if(StringUtils.isNoneEmpty(isMasterOrg) && isMasterOrgContant.equals(isMasterOrg)){
                List<String> orgIds = getOrgIdsByCondition(serviceCode);
                ConditionExpression customeCondition = new QueryCondition(property.name(), ConditionOperator.in, orgIds);
                schema.appendQueryCondition(customeCondition);
            }
        });
    }

    /**
     * 获取组织id集合
     * */
    public List<String> getOrgIdsByCondition( String serviceCode){
        String userId = ApplicationContextUtil.getThreadContext("userId");
        String tenantId = ApplicationContextUtil.getThreadContext("tenantId");

        Long startTime = System.currentTimeMillis();
        log.info("======远程接口 获取组织id start======");

        List<String> orgIds = serviceIsolateService.findMainOrgPermission(userId, serviceCode, tenantId);
        if(CollectionUtils.isEmpty(orgIds)){
            return new ArrayList<>();
        }
        log.info("======远程接口 获取组织id end 耗时：{}======", (System.currentTimeMillis() - startTime));
        return orgIds;
    }

    /***
     * 虚拟实体未发布分析
     * */
    public boolean isUnpublished(Map<String,Object> param, String entityUri){
        if(null != param.get("params") && null != ((Map)param.get("params")).get("metaClassFlag")){
            String metaClassFlag= (String) ((Map)param.get("params")).get("metaClassFlag");
            if("local_metaclass".equals(metaClassFlag)){
                UndefineBaseCache.removeCache(serviceUri, entityUri);
                return true;
            }
        }
        return false;
    }

}
