package com.sgck.common.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.sgck.common.log.DSLogger;

import net.sf.json.JSONObject;

public class OrgTreeItem implements Serializable
{
	private Integer id;	//组织id
	private String guid;	//节点guid
	private String text;	//节点名称
	private Integer parentId;	// 上级组织ID，顶级上级组织为null
	private Integer type;	//组织类型，请参阅组织结构对照表
	private String configInfo;//扩展属性
	private Integer subSystem;	//所属子系统
	private Integer displayOrder;	//显示顺序
	private Integer source;	//是否远程的
	
	private List<Integer> childrenIds;	//子节点id数组   缓存时使用，切面缓存
	private OrgTreeItem[] children;//子对象集合   缓存时该字段失效
	private Boolean leaf;//是否叶节点
	
	private String serverId;	//所属服务器ID  远程中心拖导航树时才用到
	
	private String craftItemNum;//工艺位号
	private List<Integer> overViewList;
	private String zzName;//上级装置名称 type=6
	private String queryResult;	//查询结果字符串
	
	private String dsId;
	private String dsName;
	private Integer tzType;
	private Integer tzCat;
	
	public OrgTreeItem clone()
	{
		 OrgTreeItem orgtree = null;
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 try{
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.close();
 
            ByteArrayInputStream bais = new ByteArrayInputStream(
                    baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            orgtree = (OrgTreeItem) ois.readObject();
            ois.close();
		 }catch(Exception e){
            e.printStackTrace();
		 }
		 return orgtree;
    }
	
	//注： 这两个函数暂时放在这
	//获取装置的类型
	public Integer getDevType(){
		JSONObject jsonObject = JSONObject.fromObject(configInfo);
		Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		if(map != null){
			String devType = (String) map.get("type");
			if(devType != null){
				return Integer.valueOf(devType);
			}
		}

		return 0;
	}
	
	//从configInfo中获取guid  -- 旧cms中guid存在configinfo中
	public String getGuidFromConfigInfo(){
		JSONObject jsonObject = JSONObject.fromObject(configInfo);
		Map<String, Object> configMap = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		return (String)configMap.get("GUID");
	}
	
	public boolean equals(Object obj){
		if(!(obj instanceof OrgTreeItem)){
			return false;
		}
		
		OrgTreeItem other = (OrgTreeItem)obj; 
		return this.id == other.getId();
	}
	
	public Integer getId() {
		return id == null ? 0 : id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(Integer subSystem) {
		this.subSystem = subSystem;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public OrgTreeItem[] getChildren() {
		return children;
	}
	public void setChildren(OrgTreeItem[] children) {
		this.children = children;
	}
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	

	public void setZzname(String zzname){
		this.zzName = zzname;
	}
	
	public String getZzname(){
		return this.zzName;
	}
	
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	
	public String getQueryResult() {
		return queryResult;
	}

	public void setQueryResult(String queryResult) {
		this.queryResult = queryResult;
	}

	public Integer getParentId() {
		return parentId == null ? 0 : parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getConfigInfo() {
		return configInfo;
	}
	public void setConfigInfo(String configInfo) {
		this.configInfo = configInfo;
	}


	public Integer getSource() {
		return source == null ? 0 : source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public List<Integer> getOverViewList() {
		return overViewList;
	}

	public void setOverViewList(List<Integer> overViewList) {
		this.overViewList = overViewList;
	}
	public String getDsId() {
		return dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public Integer getTzType() {
		return tzType == null ? 0 : tzType;
	}

	public void setTzType(Integer tzType) {
		this.tzType = tzType;
	}
	
	public Integer getTzCat() {
		return tzCat == null ? 0 : tzCat;
	}

	public void setTzCat(Integer tzCat) {
		this.tzCat = tzCat;
	}

	public List<Integer> getChildrenIds() {
		return childrenIds;
	}

	public void setChildrenIds(List<Integer> childrenIds) {
		this.childrenIds = childrenIds;
	}

	public String getCraftItemNum() {
		return craftItemNum;
	}

	public void setCraftItemNum(String craftItemNum) {
		this.craftItemNum = craftItemNum;
	}
	
	
}
