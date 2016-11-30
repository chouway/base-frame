package com.base.framwork.plugin.mbg;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.ClassloaderUtility;

/**
 * 对生成的mapper.java的方法,mapper.xml生成的默认声明,进行控制
 *
 * @author xiezm
 */
public class DefMapperAndSqlMapGenerator extends PluginAdapter {

	@Override
	public boolean clientCountByExampleMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		//改名
		method.setName("countByCondition");
		replaceMethodParamExample2Condition(method);
		return super.clientCountByExampleMethodGenerated(method, interfaze, introspectedTable);
	}

	//sqlMap count by example 2 condition  zyw
	@Override
	public boolean sqlMapCountByExampleElementGenerated(XmlElement element,
														IntrospectedTable introspectedTable) {

		replaceExample2Condition(element);
		return true;
	}

	//sqlMap select by example 2 condition  zyw
	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		replaceExample2Condition(element);
		return true;
	}

	//条件 sqlMap  Example 2 Condition  zyw
	@Override
	public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element,
															IntrospectedTable introspectedTable) {
		replaceExample2Condition(element);
		return true;
	}


	@Override
	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
//		replaceExample2Condition(element);
		return true;
	}



	@Override
	public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		//改名
		method.setName("selectByCondition");
		replaceMethodParamExample2Condition(method);
		return super.clientSelectByExampleWithoutBLOBsMethodGenerated(method, interfaze, introspectedTable);
	}

	@Override
	public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		//改名
		method.setName("selectByConditionWithBLOBs");
		return super.clientSelectByExampleWithoutBLOBsMethodGenerated(method, interfaze, introspectedTable);
	}


	//Modified by Yinghui, add support for cache
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		String cacheType = introspectedTable.getTableConfiguration().getProperty("cache_type");
		if (cacheType != null) {
			interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.CacheNamespaceRef"));
			interfaze.addAnnotation("@CacheNamespaceRef(value = " + interfaze.getType().getShortName() + ".class)");
		}
		return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
	}

	@Override
	public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		return true;
	}

	@Override
	public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		return true;
	}

	@Override
	public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		return true;
	}

	@Override
	public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		return true;
	}

	@Override
	public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
		return true;
	}

	@Override
	public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		return true;
	}

	@Override
	public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		return true;
	}

	@Override
	public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		return true;
	}

	@Override
	public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		return false;
	}

	@Override
	public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		return false;
	}

	@Override
	public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		return false;
	}

	@Override
	public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		return false;
	}

	@Override
	public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		return false;
	}

	@Override
	public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		return false;
	}

	@Override
	public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		//改名
		method.setName("deleteByCondition");
		replaceMethodParamExample2Condition(method);
		return super.clientDeleteByExampleMethodGenerated(method, interfaze, introspectedTable);
	}

	@Override
	public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		//改名
		method.setName("updateByCondition");
		replaceMethodParamExample2Condition(method);
		return super.clientUpdateByExampleSelectiveMethodGenerated(method, interfaze, introspectedTable);
	}


	@Override
	public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		return true;
	}

	@Override
	public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		return true;
	}

	@Override
	public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		return true;
	}

	@Override
	public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		return true;
	}

	public boolean validate(List<String> warnings) {
		return true;
	}

	//add by zyw
	protected void replaceExample2Condition(XmlElement element) {
		String name = element.getName();
		if(!"sql".equals(name)){
			List<Attribute> attributes = element.getAttributes();
			for (Attribute attribute : attributes) {
				String attrKey = "id";
				if(replaceAttrValueExample2Condition(attribute, attrKey)){
					break;
				}
			}

		}
		/*List<Element> elements = element.getElements();
		for (Element element_sub : elements) {
			if(element_sub instanceof XmlElement){
				XmlElement xmlElement = (XmlElement) element_sub;
				List<Element> elements_sub = xmlElement.getElements();
				for (Element element_sub_sub : elements_sub) {
					if(element_sub_sub instanceof  XmlElement){
						XmlElement xmlElement_sub = (XmlElement) element_sub_sub;
						if ("include".equals(xmlElement_sub.getName())) {
							List<Attribute> attributes_sub = xmlElement_sub.getAttributes();
							for (Attribute attribute : attributes_sub) {
								String attrKey = "refid";
								if(replaceAttrValueExample2Condition(attribute, attrKey)){
									break;
								};
							}
						}
					}
				}
			}
		}*/

	}

	private boolean replaceAttrValueExample2Condition(Attribute attribute, String attrKey) {
		boolean result = false;
		Class<Attribute> clazz = Attribute.class;
		try {

            if(attrKey.equals(attribute.getName())){
                Field valueField = clazz.getDeclaredField("value");
                valueField.setAccessible(true);
                String valueField_v = (String) valueField.get(attribute);
                if(valueField_v.indexOf("Example")!=-1){
                    valueField.set(attribute,valueField_v.replaceAll("Example","Condition"));
					result = true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Field reflect error",e);
        }
        return result;
	}

	private boolean replaceMethodParamExample2Condition(Method method) {
		boolean result = false;
		List<Parameter> parameters = method.getParameters();
		if(parameters == null || parameters.size()== 0){
			return result;
		}
		try {
			for (Parameter parameter : parameters) {
				String name = parameter.getName();
				if("example".equals(name)){
					Class<Parameter> clazz = Parameter.class;
					Field valueField = clazz.getDeclaredField("name");
					valueField.setAccessible(true);
					valueField.set(parameter,"condition");
					result = true;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Field reflect error",e);
		}
		return result;
	}

	@Override
	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap,
								   IntrospectedTable introspectedTable) {
		return true;
	}
}