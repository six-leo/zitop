package com.zitop.codegenerator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zitop.infrastructure.generate.DaoInterfaceCodeGenerator;
import com.zitop.infrastructure.generate.EntityCodeGenerator;
import com.zitop.infrastructure.generate.HibernateDaoImplCodeGenerator;
import com.zitop.infrastructure.generate.JSPEntityInputCodeGenerator;
import com.zitop.infrastructure.generate.JSPEntityListCodeGenerator;
import com.zitop.infrastructure.generate.ListActionCodeGenerator;
import com.zitop.infrastructure.generate.ManageActionCodeGenerator;
import com.zitop.infrastructure.generate.ServiceCodeGenerator;
import com.zitop.infrastructure.util.NonceUtils;
import com.zitop.infrastructure.util.WordDealUtil;



public class Generator
{

	/*
	 * 参数为Entity类名(注意第一个字符大写)
	 */
	public static void main(String[] args) {
		Generator g = new Generator();
		g.generateStandardLayoutFiles();
	}
	
	/**
	 * 基于标准方式生成各个基础代码
	 * @author william
	 */
	public void generateStandardLayoutFiles()
	{
		/**
		 * 主要变量issue_responsibility
		 */
		String basePackage = "com.zitop.tacking";//基础包路径
		String entityName = "IndexCategory";//实体名称
		String namespace = "admin/index";//struts2命名空间如/example/test
		String namespacePackageStype = "admin.index";//struts2命名空间包路径形式如example.test
		
		
		/**
		 * 以下是各标准包与文件 路径，实际需要才修改
		 */
		String entityPackagePath = basePackage+".entity";//实体包路径
		
		String listActionName = entityName+"ListAction";//列表Action名称
		// String listActionPackagePath = basePackage+".web.actions"+"."+namespacePackageStype;//列表Action包路径
		String listActionPackagePath = "com.zitop.tacking.web.actions"+"."+namespacePackageStype;//列表Action包路径
		
		String manageActionName = entityName+"Action";//管理Action名称
		// String manageActionPackagePath = basePackage+".web.actions"+"."+namespacePackageStype;//管理Action包路径
		String manageActionPackagePath = "com.zitop.tacking.web.actions"+"."+namespacePackageStype;//管理Action包路径
		
		
		String interfaceDaoName = "I"+entityName+"DAO";//接口DAO名称
		String interfaceDaoPackagePath = basePackage+".dao";//接口DAO包路径
		
		String hebernateDaoName = entityName+"DAOImpl";//hibernate实现DAO名称
		String hebernateDaoPackagePath = interfaceDaoPackagePath+".hibernate";//hibernate实现DAO名称
		
		String serviceName = entityName+"Service";//服务类名称
		String servicePackagePath = basePackage+".service";//服务类包路径
		
		
		//产生Entity
		Map<String,String> variableEntity = new HashMap<String,String>();
		variableEntity.put("classPackagePath", entityPackagePath);
		variableEntity.put("className", entityName);
		long serialVersionUID = NonceUtils.randomLong()
		+ NonceUtils.currentMills();
		variableEntity.put("serialVersionUID", serialVersionUID+"");
		EntityCodeGenerator entityCodeGenerator = new EntityCodeGenerator(variableEntity);
		entityCodeGenerator.generateFile();
		
		//产生DAO接口
		Map<String,String> variableIDao = new HashMap<String,String>();
		variableIDao.put("classPackagePath", interfaceDaoPackagePath);
		variableIDao.put("className", interfaceDaoName);
		variableIDao.put("entityName", entityName);
		variableIDao.put("entityPackagePath", entityPackagePath);
		DaoInterfaceCodeGenerator daoInterfaceCodeGenerator = new DaoInterfaceCodeGenerator(variableIDao);
		daoInterfaceCodeGenerator.generateFile();
		
		//产生Hibernate DAO实现
		Map<String,String> variableHibernateDao = new HashMap<String,String>();
		variableHibernateDao.put("classPackagePath", hebernateDaoPackagePath);
		variableHibernateDao.put("className", hebernateDaoName);
		variableHibernateDao.put("entityName", entityName);
		variableHibernateDao.put("entityPackagePath", entityPackagePath);
		variableHibernateDao.put("daoInterfacePackagePath", interfaceDaoPackagePath);
		variableHibernateDao.put("daoInterfaceName", interfaceDaoName);
		HibernateDaoImplCodeGenerator hibernateDaoImplCodeGenerator = new HibernateDaoImplCodeGenerator(variableHibernateDao);
		hibernateDaoImplCodeGenerator.generateFile();
		
		//产生Servic
		Map<String,String> variableService = new HashMap<String,String>();
		variableService.put("classPackagePath", servicePackagePath);
		variableService.put("className", serviceName);
		variableService.put("entityName", entityName);
		variableService.put("entityPackagePath", entityPackagePath);
		variableService.put("daoInterfacePackagePath", interfaceDaoPackagePath);
		variableService.put("daoInterfaceName", interfaceDaoName);
		variableService.put("daoVariableName", StringUtils.uncapitalize(entityName)+"DAO");
		ServiceCodeGenerator srviceCodeGenerator = new ServiceCodeGenerator(variableService);
		srviceCodeGenerator.generateFile();
		
		//产生ListAction
		Map<String,String> variableListAction = new HashMap<String,String>();
		variableListAction.put("classPackagePath", listActionPackagePath);
		variableListAction.put("className", listActionName);
		variableListAction.put("entityName", entityName);
		variableListAction.put("entityPackagePath", entityPackagePath);
		variableListAction.put("servicePackagePath", servicePackagePath);
		variableListAction.put("serviceName", serviceName);
		variableListAction.put("serviceVariableName", StringUtils.uncapitalize(serviceName));
		serialVersionUID = NonceUtils.randomLong()
		+ NonceUtils.currentMills();
		variableListAction.put("serialVersionUID", serialVersionUID+"");
		ListActionCodeGenerator listActionCodeGenerator = new ListActionCodeGenerator(variableListAction);
		listActionCodeGenerator.generateFile();
		
		//产生ManagerAction
		Map<String,String> variableManagement = new HashMap<String,String>();
		variableManagement.put("classPackagePath", manageActionPackagePath);
		variableManagement.put("className", manageActionName);
		variableManagement.put("entityName", entityName);
		variableManagement.put("entityPackagePath", entityPackagePath);
		variableManagement.put("entityVariableName", StringUtils.uncapitalize(entityName));
		variableManagement.put("servicePackagePath", servicePackagePath);
		variableManagement.put("serviceName", serviceName);
		variableManagement.put("serviceVariableName", StringUtils.uncapitalize(serviceName));
		serialVersionUID = NonceUtils.randomLong()
		+ NonceUtils.currentMills();
		variableManagement.put("serialVersionUID", serialVersionUID+"");
		ManageActionCodeGenerator manageActionCodeGenerator = new ManageActionCodeGenerator(variableManagement);
		manageActionCodeGenerator.generateFile();
		
		//生成Jsp-input页面
		Map<String,String> variableEntityJSPInput = new HashMap<String,String>();
		variableEntityJSPInput.put("className", entityName);
		variableEntityJSPInput.put("namespace", namespace);
		variableEntityJSPInput.put("manageActionName", WordDealUtil.wordFormat4JSP(entityName));
		variableEntityJSPInput.put("listActionName", WordDealUtil.wordFormat4JSP(entityName)+"-list");
		JSPEntityInputCodeGenerator jspEntityInputCodeGenerator = new JSPEntityInputCodeGenerator(variableEntityJSPInput);
		jspEntityInputCodeGenerator.generateFile();
		
		//生成Jsp-list页面
		Map<String,String> variableEntityJSPList = new HashMap<String,String>();
		variableEntityJSPList.put("className", entityName);
		variableEntityJSPList.put("namespace", namespace);
		variableEntityJSPList.put("manageActionName", WordDealUtil.wordFormat4JSP(entityName));
		variableEntityJSPList.put("listActionName", WordDealUtil.wordFormat4JSP(entityName)+"-list");
		JSPEntityListCodeGenerator jspEntityListCodeGenerator = new JSPEntityListCodeGenerator(variableEntityJSPList);
		jspEntityListCodeGenerator.generateFile();
	}

}
