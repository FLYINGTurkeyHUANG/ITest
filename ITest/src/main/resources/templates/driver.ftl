<#--建议模板文件命名：类别id.ftl-->
<#list driverTemplateFields as templateField>
    fieldName=${templateField.fieldName}
    necessary=${templateField.necessary}
    type=${templateField.type}
    resumeField=<#list templateField.resumeField as field>${field} </#list>

</#list>