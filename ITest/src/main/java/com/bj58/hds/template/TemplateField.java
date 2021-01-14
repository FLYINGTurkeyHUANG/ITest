package com.bj58.hds.template;

/**
 * 简历模板域字段：
 * 1、字段名
 * 2、必填与否
 * 3、填写项对应的组件类型(填写、单选、多选、滑动选择等)
 * 4、简历实体中字段的映射关系
 * */
public class TemplateField {

    /** 字段名 */
    private String fieldName;

    /** 该字段是否必填 */
    private int necessary;

    /** 对应的组件类型 */
    private int type;

    /** 简历实体中字段的映射关系 */
    private String[] resumeField;


}
