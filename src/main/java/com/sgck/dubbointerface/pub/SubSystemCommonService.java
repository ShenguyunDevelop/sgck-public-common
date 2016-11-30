package com.sgck.dubbointerface.pub;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sgck.common.domain.User;

import flex.messaging.io.amf.ASObject;

@Component
public interface SubSystemCommonService {
	
	/**
	 *@Description 初始化获取所有屏蔽规则包括全局的和所有用户的
	 *@param platform 0是短信;1是app
	 *@return
	 *@throws Exception 
	 */
	public ASObject getAllFilter(int platform) throws Exception;
	
	/**
	 *@Description 获取所有的用户信息
	 *@return
	 *@throws Exception
	 */
	public List<User> getAllUserInfo(int platform) throws Exception;
	
	/**
	 *@Description 获取所有的导航树节点
	 *@return
	 *@throws Exception
	 */
	public List<ASObject> getAllNaviTree()throws Exception;
}
