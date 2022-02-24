package com.uto.puai.service;

import com.uto.puai.entity.Formmain0551;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 * @author Richard
 * @since 2022-02-23
 */
public interface Formmain0551Service extends IService<Formmain0551> {

    void updateField(String colSummaryId, List<Map<String, Object>> filesList);
}
