package com.uto.puai.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Richard
 * @since 2022-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("formmain_0551")
public class Formmain0551 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Double id;

    private Integer state;

    private String startMemberId;

    private Date startDate;

    private String approveMemberId;

    private Date approveDate;

    private Integer finishedflag;

    private Integer ratifyflag;

    private String ratifyMemberId;

    private Date ratifyDate;

    private Integer sort;

    private String modifyMemberId;

    private Date modifyDate;

    private String field0001;

    private String field0010;

    private String field0011;

    private Date field0012;

    private String field0014;

    private String field0015;

    private String field0016;

    private String field0017;

    private String field0018;

    private String field0019;

    private Date field0020;

    private Date field0021;

    private String field0022;

    private String field0023;

    private Date field0024;

    private String field0025;

    private String field0026;

    private Date field0027;

    private String field0032;

    private String field0033;

    private String field0035;

    private Double field0036;

    private String field0037;

    private String field0038;

    private Double field0039;

    private String field0040;

    private Double field0041;

    private String field0042;

    private String field0072;

    private String field0073;

    private String field0074;

    private String field0075;

    private String field0076;

    private String field0077;

    private String field0078;

    private String field0079;

    private String field0081;

    private Double field0028;

    private Double field0034;

    private String field0082;

    private String field0085;

    private String field0086;

    private String field0087;

    private String field0088;

    private String field0029;

    private Double field0013;

    private String field0089;

    private String field0090;

    private Date field0091;

    private String field0092;

    private String field0093;

    private String field0094;

    private String field0095;

    private String field0096;

    private String field0097;


}
