package com.uto.puai.service.impl;

import com.uto.puai.entity.Formmain0551;
import com.uto.puai.enums.ResultCode;
import com.uto.puai.exceptions.DBException;
import com.uto.puai.mapper.Formmain0551Mapper;
import com.uto.puai.service.Formmain0551Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 * @author Richard
 * @since 2022-02-23
 */
@Service
public class Formmain0551ServiceImpl extends ServiceImpl<Formmain0551Mapper, Formmain0551> implements Formmain0551Service {

    @Resource
    private Formmain0551Mapper mapper;

    /**
     * 更改CTP_ATTACHMENT文件保存表中每个文件的两个字段值用于显示
     * @param colSummaryId 协同主表ID
     * @param filesList    所有上传文件的ID和URL
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateField(String colSummaryId, List<Map<String, Object>> filesList) {
        for (Map<String, Object> map : filesList) {
            String id = (String) map.get("id");
            String fileUrl = (String) map.get("fileUrl");
            Integer integer = mapper.updateField(colSummaryId, id, fileUrl);
            System.out.println("本次执行的结果: " + integer);
        }
    }
}
