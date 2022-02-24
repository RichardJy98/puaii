package com.uto.puai.generators;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Richard
 * @version 1.0
 * @description 代码生成器
 * @date 2021/9/2 10:40
 */
public class MybatisPlusGenerator {

    public static void main(String[] args) {
        //获取项目的路径
        String projectPath = System.getProperty("user.dir");
        //String moduleName = scanner("模块名");
        String moduleName = scanner("模块名");
        String[] tableNames = scanner("表名，多个英文逗号分割").split(",");
        //代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        //初始化全局配置
        autoGenerator.setGlobalConfig(initGlobalConfig(projectPath));
        //初始化数据源
        autoGenerator.setDataSource(initDataSourceConfig());
        //包名配置
        autoGenerator.setPackageInfo(initPackageInfoConfig(moduleName));
        //模版配置
        autoGenerator.setTemplate(initTemplateConfig());
        //初始化自定义配置
        autoGenerator.setCfg(initInjectionConfig(projectPath, moduleName));
        //初始化策略配置
        autoGenerator.setStrategy(initStrategyConfig(tableNames));
        //设置模版引擎为默认的
        autoGenerator.setTemplateEngine(new VelocityTemplateEngine());
        //执行生成代码
        autoGenerator.execute();
    }

    /**
     * 读取键盘录入的数据
     * @param tip 提示信息
     * @return java.lang.String 键盘录入的字符串
     * @author Richard
     * @date 2021/9/2 13:15
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help);
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StrUtil.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 初始化策略配置
     * @param tableNames 表名
     * @return com.baomidou.mybatisplus.generator.config.StrategyConfig 策略配置
     * @author Richard
     * @date 2021/9/2 14:14
     */
    private static StrategyConfig initStrategyConfig(String[] tableNames) {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        //当表名中带*号时可以启用通配符模式
        if (tableNames.length == 1 && tableNames[0].contains("*")) {
            String[] likeStr = tableNames[0].split("_");
            String likePrefix = likeStr[0] + "_";
            //启用模糊匹配表名
            strategyConfig.setLikeTable(new LikeTable(likePrefix));
        } else {
            strategyConfig.setInclude(tableNames);
        }
        return strategyConfig;
    }

    /**
     * 初始化模版配置
     * @return com.baomidou.mybatisplus.generator.config.TemplateConfig 模版配置信息
     * @author Richard
     * @date 2021/9/2 13:46
     */
    private static TemplateConfig initTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        //可以对controller、service、entity模板进行配置
        //mapper.xml模板需单独配置
        templateConfig.setXml(null);
        return templateConfig;
    }

    /**
     * 初始化全局配置
     * @param projectPath 项目路径
     * @return java.lang.Object
     * @author Richard
     * @date 2021/9/2 13:19
     */
    private static GlobalConfig initGlobalConfig(String projectPath) {
        GlobalConfig globalConfig = new GlobalConfig();

        //设置文件输出的根目录
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        //是否覆盖已有文件
        globalConfig.setFileOverride(true);
        //是否打开输出目录
        globalConfig.setOpen(true);
        //开发人员
        globalConfig.setAuthor("Richard");
        //时间配置
        globalConfig.setDateType(DateType.ONLY_DATE);
        //实体类名称
        globalConfig.setEntityName("%s");
        //mapper名称
        globalConfig.setMapperName("%sMapper");
        //ResultMap映射
        globalConfig.setBaseResultMap(true);
        //xml名称
        globalConfig.setXmlName("%sMapper");
        //服务层接口名称
        globalConfig.setServiceName("%sService");
        //服务层接口实现类名称
        globalConfig.setServiceImplName("%sServiceImpl");
        //控制层接口名称
        globalConfig.setControllerName("%sController");
        //开启Swagger2
        globalConfig.setSwagger2(false);
        return globalConfig;
    }

    /**
     * 初始化数据源
     * @return com.baomidou.mybatisplus.generator.config.DataSourceConfig 数据源配置信息
     * @author Richard
     * @date 2021/9/2 13:34
     */
    private static DataSourceConfig initDataSourceConfig() {
        Props props = new Props("generator.properties");
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(props.getStr("dataSource.url"));
        dataSourceConfig.setUsername(props.getStr("dataSource.username"));
        dataSourceConfig.setPassword(props.getStr("dataSource.password"));
        dataSourceConfig.setDriverName(props.getStr("dataSource.driverName"));
        return dataSourceConfig;
    }

    /**
     * 设置包名等信息
     * @param moduleName 模块名称
     * @return com.baomidou.mybatisplus.generator.config.PackageConfig 包名配置
     * @author Richard
     * @date 2021/9/2 13:40
     */
    private static PackageConfig initPackageInfoConfig(String moduleName) {
        Props props = new Props("generator.properties");
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(moduleName);
        packageConfig.setParent("com.uto");
        return packageConfig;
    }

    /**
     * 初始化自定义配置
     * @param projectPath 项目路径
     * @param moduleName  模块名称
     * @return com.baomidou.mybatisplus.generator.InjectionConfig 自定义配置
     * @author Richard
     * @date 2021/9/2 13:48
     */
    private static InjectionConfig initInjectionConfig(String projectPath, String moduleName) {
        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // 可用于自定义属性
            }
        };
        // 模板引擎是Velocity
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + moduleName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        injectionConfig.setFileOutConfigList(focList);
        return injectionConfig;
    }
}
