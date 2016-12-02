package com.base.framework.plugin.mbg;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;


/**
 * @author xiezm
 */
public class DefEntityAnnotation extends PluginAdapter {

	/**
	 * 常量属性，用于取配置的属性(版本字段列名)
	 */
	private static final String VERSION_COLUMN_NAME = "versionColumnName";

	/**
	 * 默认版本字段列名
	 */
	private String versionColumnName;

	public DefEntityAnnotation() {
		versionColumnName = (String) this.getProperties().get(VERSION_COLUMN_NAME);
		versionColumnName = (versionColumnName == null || versionColumnName.length() < 1)
				? "RECORD_VERSION"
				: versionColumnName;
	}

	/**
	 * get方法注解生成
	 */
	@Override
	public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
			IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		// 所有列
		if (introspectedColumn.getActualColumnName() != null && introspectedColumn.getActualColumnName().length() > 0) {
			method.addAnnotation("@Column(name = \"" + introspectedColumn.getActualColumnName() + "\")");
		}
		return super.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable,
				modelClassType);
	}

	/**
	 * 实体类生成
	 */
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addImportedType("javax.persistence.Column");
		topLevelClass.addImportedType("javax.persistence.Entity");
		topLevelClass.addImportedType("javax.persistence.Table");
		// 判定是否有版本列
		boolean isExistsVersionColumn = false;
		for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
			// 判定是否有版本列
			if (introspectedColumn.getActualColumnName().equalsIgnoreCase(versionColumnName)) {
				isExistsVersionColumn = true;
				break;
			}
		}
		// 存在则引用
		if (isExistsVersionColumn) {
			topLevelClass.addImportedType("javax.persistence.Version");
		}
		topLevelClass.addAnnotation("@Entity");
		topLevelClass.addAnnotation("@Table(name = \"" + introspectedTable.getTableConfiguration().getTableName()
				+ "\")");
		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}

	/**
	 * This plugin is always valid - no properties are required
	 */
	public boolean validate(List<String> warnings) {
		return true;
	}
}