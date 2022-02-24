package com.uto.puai.mapper;

import com.uto.puai.entity.Formmain0551;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author Richard
 * @version 1.0
 * @description: 员工登记表Mapper
 * @date 2022/2/24 13:16
 */
@Mapper
public interface Formmain0551Mapper extends BaseMapper<Formmain0551> {

    /**
     * 根据FILE_URL查询CTP_ATTACHMENT中的唯一记录,将其中的ATT_REFERENCE改为colSummaryId,SUB_REFERENCE改为id
     * @param colSummaryId 协同主表主键
     * @param id           附件的主键
     * @param fileUrl      附件的url
     * @author Richard
     * @date 2022/2/24 13:15
     */
    @Update("update CTP_ATTACHMENT set ATT_REFERENCE = #{colSummaryId},SUB_REFERENCE = #{id} where FILE_URL = #{fileUrl}")
    Integer updateField(@Param("colSummaryId") String colSummaryId, @Param("id") String id, @Param("fileUrl") String fileUrl);
}
