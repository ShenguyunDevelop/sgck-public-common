package com.sgck.dubbointerface.pub;

import flex.messaging.io.amf.ASObject;

public interface Sg8kAppService {
	/**
	 *@Description app 登录
	 *@param phone 手机号码
	 *@param token 
	 *@param system
	 *@param deviceid 设备id
	 *@return
	 * @throws Exception 
	 */
	public ASObject appLogin(String phone, String token, String system,String deviceid) throws Exception;
	
	/**
	 *@Description app用户注销，删除用户token信息
	 *@param userId
	 *@param phone
	 *@param deviceid
	 *@param token
	 *@return
	 * @throws Exception 
	 */
	public ASObject removeAppToken(int userId, String phone, String deviceid, String token) throws Exception;
	
	/**
	 *@Description 删除屏蔽规则
	 *@param ruleid
	 * @throws Exception 
	 */
	public ASObject removeFilterRule(int ruleid) throws Exception;
	
	/**
	 *@Description 注册新用户
	 *@param userObject
	 *@return
	 *@throws Exception
	 */
	public ASObject registerAccount(ASObject userObject)throws Exception;
	
	/**
	 *@Description 增加屏蔽规则
	 *@param filterRule
	 *@return
	 *@throws Exception
	 */
	public ASObject addFilterRule(ASObject filterRule) throws Exception;
	
	/**
	 *@Description 根据用户id获取用户推送配置
	 *@param userid
	 *@return
	 *@throws Exception
	 */
	public String getUserPushConfig(Integer userid)throws Exception;
	
	/**
	 *@Description 修改用户推送配置
	 *@param userid
	 *@param userPushConfig
	 *@throws Exception
	 */
	public void updateUserPushConfig(Integer userid, String userPushConfig)throws Exception;
}
