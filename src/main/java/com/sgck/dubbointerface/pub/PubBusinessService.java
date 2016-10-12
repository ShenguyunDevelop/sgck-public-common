package com.sgck.dubbointerface.pub;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sgck.common.domain.User;
import com.sgck.dubbointerface.exception.BaseServiceException;

import flex.messaging.io.amf.ASObject;

public interface PubBusinessService
{

	// 默认缓存地址
	public static final String DEFAULT_CACHE_ADDRESS = "default.redis.sysmon";
	// 默认数据库地址
	public static final String DEFAULT_DB_ADDRESS = "default.db.sysmon";
	// 8k数据库地址
	public static final String SG8K_DB_ADDRESS = "default.db.sg8k";
	// 8k缓存地址
	public static final String SG8K_CACHE_ADDRESS = "default.redis.sg8k";
	// 9k数据库地址
	public static final String SG9K_DB_ADDRESS = "default.db.sg9k";
	// 9k缓存地址
	public static final String SG9K_CACHE_ADDRESS = "default.redis.sg9k";
	// 8kmq地址
	public static final String SG8K_MQ_ADDRESS = "default.mq.sg8k";
	// 默认组织结构树缓存标识
	public static final String ALL_ORG_CACHE = "ALL_ORG_CACHE::";

	public static final String ORG_SYSMON_CACHE = "OrgCache";
	// 默认用户缓存标识
	public static final String USER_CACHE = "USER_CACHE::";
	// 订阅机组集合
	public static final String SUB_MACHINE = "SUB_MACHINE::";
	// 机组订阅
	public static final String RT_MACHINE_DATA = "RT_MACHINE_DATA::";
	// 机组缓存
	public static final String MACHINE = "MACHINE::";
	// 默认缓存时间 单位秒
	public static final int DEFAULT_CACHE_TIME = 60 * 60;
	// 默认存储上传状态标识
	public static final String DEFAULT_UPLOAD_STATUS = "UPLOAD_STATUS::";
	// adminex
	public static final String SYSMON_SUB_REDIS_NAME_DOMAIN = "SubSystem::";

	public static final String ORG_TO_OVERVIEW_LIST = "orgToOverViewList::";

	public final static String REDIS_KEY_MACHINE = "machine";

	public final static String RT_MACHINE_ID_CACHE = "RtMachineIdCache";// 订阅机组

	public static final String STATE_CACHE = "StateCache";// 机组状态

	public static final String EXT_DATA_SOURCE_CACHE = "DataSourceCache::";// 数据源

	public List<ASObject> test();

	// 机组新增 删除 修改
	public int addMachine(String dbKey, String redisKey, ASObject machine, Map<String, String> gpInfoMap, Integer userId) throws BaseServiceException;

	// 机组的批量混合操作
	public void updateMachine(List<String> delMachines, List<ASObject> addMachines, List<ASObject> modifyMachines, Integer userId) throws BaseServiceException;

	public void modifyMachine(String dbKey, String redisKey, ASObject machine, Map<String, String> gpInfoMap) throws BaseServiceException;

	public void deleteMachine(String dbKey, String redisKey, String id) throws BaseServiceException;

	// 获取用户过滤后的导航树 需要结合缓存
	public <T> T getCheckOrgListTree(Integer userId, String rootId, int subSysType);

	// 获取超级用户过滤后的导航树 需要结合缓存
	public <T> T getCheckOrgListTree(String rootId, final int subSystemType);

	// 根据类型所有导航树信息
	public <T> T getOrgListTree(int subSysType);

	// 根据类型所有导航树信息
	public <T> T getOrgList(int subSysType);

	// 上传实时状态
	public <T> void uploadRtStatus(int subSysType, Map<String, T> map) throws BaseServiceException;

	/**
	 * @Description 获取实时状态
	 * @param subSysType
	 *            2 8k;3,9k 0 取所有
	 * @return
	 */
	public List<ASObject> getRtStatus(Integer subSysType);

	/**
	 * @Description 需要订阅机组
	 * @param machine8k
	 *            需要订阅的8k机组
	 * @param machine9k
	 *            需要订阅的9k机组
	 */
	public <T> void subRtMachine(Map<String, ASObject> machine8k, Map<String, ASObject> machine9k);

	/**
	 * 
	 * 2016年4月22日
	 * 
	 * @Description 获取需要订阅机组
	 * @param subSysType
	 *            子系统id
	 * @return
	 */
	public Map<String, ASObject> getSubMachine(int subSysType);

	/**
	 * @Description 上传机组实时特征值
	 * @param subSysType
	 * @param map
	 */
	public <T> void upRTMachineData(int subSysType, Map<String, T> map);

	/**
	 * @Description 获取机组实时值
	 * @param subSysType
	 * @return
	 */
	public List<ASObject> getRTMachineData(int subSysType);

	/**
	 * 缓存机组
	 */
	public ASObject getMachine(int subSysType, String machineId) throws Exception;

	// 获取子系统访问信息集合
	public List<ASObject> getSubsystemInfoList();

	// 上传子系统信息
	public void uploadSubsystemInfo(ASObject subsystem) throws BaseServiceException;

	/**
	 * @Description 删除缓存
	 * @param redisKye
	 * @param machineId
	 */
	public void removeMachine(int subSysType, String machineId);

	/**
	 * @Description 初始化对应的节点对应总貌图
	 * @throws Exception
	 */
	public void initOrgOverList(int subSysType) throws Exception;

	/**
	 * @Description 新增节点和总貌图关系
	 * @param subSysType
	 * @param orgOvers
	 * @throws Exception
	 */
	public void refreshOrgOver(int subSysType, List<String> orgList) throws Exception;

	/**
	 * @Description 从缓存里获取外部数据源
	 * @return
	 * @throws Exception
	 */
	public ASObject getExtDataSourceDataByIds(Set<String> sourceIds) throws Exception;

	public <T> Map<String, T> getHgetAllForTest(String key);

	// 根据父节点ID获取下级子节点列表
	public List<ASObject> getOrgListByParentId(Integer root);

	// 根据类型获取所有过滤节点树形结构
	public List<ASObject> getOrgListTreeByFilterType(Integer type);

	// 指定类型orgtype 获取用户过滤后的导航树 需要结合缓存
	public <T> T getCheckOrgListTreeForType(Integer userId, String rootId, final Integer orgtype);

	// 获取超级用户过滤后的导航树 需要结合缓存
	public <T> T getCheckOrgListTreeForType(String rootId, final Integer orgtype);

	// 获取用户缓存
	public User getUserById(Integer userId);

	public void reloadUserCache(User user) throws BaseServiceException;

	public void deleteUserCache(Integer userId) throws BaseServiceException;
}
