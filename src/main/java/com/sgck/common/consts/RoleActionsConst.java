package com.sgck.common.consts;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import flex.messaging.io.amf.ASObject;

public class RoleActionsConst {
	public static ASObject roleActionsTree = null;

	public final static ASObject getRoleActionsTree() {
		if (roleActionsTree == null) {

			roleActionsTree = new ASObject();
			roleActionsTree.put("id", 1);
			roleActionsTree.put("name", "所有");
			roleActionsTree.put("parentId", 0);

			ASObject actionDetail4 = new ASObject();
			actionDetail4.put("id", 4000000);
			actionDetail4.put("name", "公共");
			actionDetail4.put("parentId", 1);
			setCommonAction(actionDetail4);

			ASObject actionDetail2 = new ASObject();
			actionDetail2.put("id", 2000000);
			actionDetail2.put("name", "SG8K系统");
			actionDetail2.put("parentId", 1);
			set8kAction(actionDetail2);

			ASObject actionDetail3 = new ASObject();
			actionDetail3.put("id", 3000000);
			actionDetail3.put("name", "SG9K系统");
			actionDetail3.put("parentId", 1);
			set9KAction(actionDetail3);

			List<ASObject> treeList = new ArrayList();
			treeList.add(actionDetail2);
			treeList.add(actionDetail3);
			treeList.add(actionDetail4);
			roleActionsTree.put("children", treeList);
		}

		return roleActionsTree;
	}

	private static void setCommonAction(ASObject commonAction) {
		List<ASObject> actionDetail4Child = new ArrayList();
		/**
		 * 常规图谱,由于要跟8k兼容，所以有些id对应的以前的8k的id
		 */
		ASObject sg8kAction10000 = new ASObject();
		sg8kAction10000.put("id", 4010000);
		sg8kAction10000.put("name", "常规图谱");
		sg8kAction10000.put("parentId", 400000);

		ASObject sg8kAction10100 = new ASObject();
		sg8kAction10100.put("id", 10100);
		sg8kAction10100.put("name", "总貌图");
		sg8kAction10100.put("parentId", 4010000);
		List<ASObject> sg8kAction10100Child = new ArrayList();
		ASObject sg8kAction10101 = new ASObject();
		sg8kAction10101.put("id", 10101);
		sg8kAction10101.put("name", "访问");
		sg8kAction10101.put("parentId", 10100);
		sg8kAction10100Child.add(sg8kAction10101);
		sg8kAction10100.put("children", sg8kAction10100Child);

		List<ASObject> sg8kAction10000Child = new ArrayList();
		sg8kAction10000Child.add(sg8kAction10100);
		sg8kAction10000.put("children", sg8kAction10000Child);

		ASObject sg8kAction50000 = new ASObject();
		sg8kAction50000.put("id", 4050000);
		sg8kAction50000.put("name", "设置模块");
		sg8kAction50000.put("parentId", 4000000);

		ASObject sg8kAction50200 = new ASObject();
		sg8kAction50200.put("id", 50200);
		sg8kAction50200.put("name", "总貌图设置");
		sg8kAction50200.put("parentId", 50000);

		List<ASObject> sg8kAction50200Child = new ArrayList();
		ASObject sg8kAction50201 = new ASObject();
		sg8kAction50201.put("id", 50201);
		sg8kAction50201.put("name", "访问");
		sg8kAction50201.put("parentId", 50200);

		ASObject sg8kAction50202 = new ASObject();
		sg8kAction50202.put("id", 50202);
		sg8kAction50202.put("name", "添加");
		sg8kAction50202.put("parentId", 50200);

		ASObject sg8kAction50203 = new ASObject();
		sg8kAction50203.put("id", 50203);
		sg8kAction50203.put("name", "删除");
		sg8kAction50203.put("parentId", 50200);

		ASObject sg8kAction50204 = new ASObject();
		sg8kAction50204.put("id", 50204);
		sg8kAction50204.put("name", "修改");
		sg8kAction50204.put("parentId", 50200);
		sg8kAction50200Child.add(sg8kAction50201);
		sg8kAction50200Child.add(sg8kAction50202);
		sg8kAction50200Child.add(sg8kAction50203);
		sg8kAction50200Child.add(sg8kAction50204);

		sg8kAction50200.put("children", sg8kAction50200Child);
		// --
		ASObject sg8kAction50100 = new ASObject();
		sg8kAction50100.put("id", 50100);
		sg8kAction50100.put("name", "导航树设置");
		sg8kAction50100.put("parentId", 50000);

		List<ASObject> sg8kAction50100Child = new ArrayList();
		ASObject sg8kAction50101 = new ASObject();
		sg8kAction50101.put("id", 50101);
		sg8kAction50101.put("name", "访问");
		sg8kAction50101.put("parentId", 50100);

		ASObject sg8kAction50102 = new ASObject();
		sg8kAction50102.put("id", 50102);
		sg8kAction50102.put("name", "添加");
		sg8kAction50102.put("parentId", 50100);

		ASObject sg8kAction50103 = new ASObject();
		sg8kAction50103.put("id", 50103);
		sg8kAction50103.put("name", "删除");
		sg8kAction50103.put("parentId", 50100);

		ASObject sg8kAction50104 = new ASObject();
		sg8kAction50104.put("id", 50104);
		sg8kAction50104.put("name", "修改");
		sg8kAction50104.put("parentId", 50100);
		sg8kAction50100Child.add(sg8kAction50101);
		sg8kAction50100Child.add(sg8kAction50102);
		sg8kAction50100Child.add(sg8kAction50103);
		sg8kAction50100Child.add(sg8kAction50104);

		sg8kAction50100.put("children", sg8kAction50100Child);

		ASObject sg8kAction50900 = new ASObject();
		sg8kAction50900.put("id", 50900);
		sg8kAction50900.put("name", "用户管理");
		sg8kAction50900.put("parentId", 50000);

		List<ASObject> sg8kAction50900Child = new ArrayList();
		ASObject sg8kAction50901 = new ASObject();
		sg8kAction50901.put("id", 50901);
		sg8kAction50901.put("name", "访问");
		sg8kAction50901.put("parentId", 50900);

		ASObject sg8kAction50902 = new ASObject();
		sg8kAction50902.put("id", 50902);
		sg8kAction50902.put("name", "添加");
		sg8kAction50902.put("parentId", 50900);

		ASObject sg8kAction50903 = new ASObject();
		sg8kAction50903.put("id", 50903);
		sg8kAction50903.put("name", "删除");
		sg8kAction50903.put("parentId", 50900);

		ASObject sg8kAction50904 = new ASObject();
		sg8kAction50904.put("id", 50904);
		sg8kAction50904.put("name", "修改");
		sg8kAction50904.put("parentId", 50900);

		ASObject sg8kAction50905 = new ASObject();
		sg8kAction50905.put("id", 50905);
		sg8kAction50905.put("name", "修改组织结构权限");
		sg8kAction50905.put("parentId", 50900);

		sg8kAction50900Child.add(sg8kAction50901);
		sg8kAction50900Child.add(sg8kAction50902);
		sg8kAction50900Child.add(sg8kAction50903);
		sg8kAction50900Child.add(sg8kAction50904);
		sg8kAction50900Child.add(sg8kAction50905);

		sg8kAction50900.put("children", sg8kAction50900Child);

		ASObject sg8kAction51000 = new ASObject();
		sg8kAction51000.put("id", 51000);
		sg8kAction51000.put("name", "角色管理");
		sg8kAction51000.put("parentId", 50000);

		List<ASObject> sg8kAction51000Child = new ArrayList();
		ASObject sg8kAction51001 = new ASObject();
		sg8kAction51001.put("id", 51001);
		sg8kAction51001.put("name", "访问");
		sg8kAction51001.put("parentId", 51000);

		ASObject sg8kAction51002 = new ASObject();
		sg8kAction51002.put("id", 51002);
		sg8kAction51002.put("name", "添加");
		sg8kAction51002.put("parentId", 51000);

		ASObject sg8kAction51003 = new ASObject();
		sg8kAction51003.put("id", 51003);
		sg8kAction51003.put("name", "删除");
		sg8kAction51003.put("parentId", 51000);

		ASObject sg8kAction51004 = new ASObject();
		sg8kAction51004.put("id", 51004);
		sg8kAction51004.put("name", "修改");
		sg8kAction51004.put("parentId", 51000);

		sg8kAction51000Child.add(sg8kAction51001);
		sg8kAction51000Child.add(sg8kAction51002);
		sg8kAction51000Child.add(sg8kAction51003);
		sg8kAction51000Child.add(sg8kAction51004);

		sg8kAction51000.put("children", sg8kAction51000Child);

		ASObject sg8kAction51200 = new ASObject();
		sg8kAction51200.put("id", 51200);
		sg8kAction51200.put("name", "远程中心设置");
		sg8kAction51200.put("parentId", 50000);

		List<ASObject> sg8kAction51200Child = new ArrayList();
		ASObject sg8kAction51201 = new ASObject();
		sg8kAction51201.put("id", 51201);
		sg8kAction51201.put("name", "访问");
		sg8kAction51201.put("parentId", 51200);

		sg8kAction51200Child.add(sg8kAction51201);

		sg8kAction51200.put("children", sg8kAction51200Child);

		ASObject sg8kAction4051300 = new ASObject();
		sg8kAction4051300.put("id", 4051300);
		sg8kAction4051300.put("name", "地图管理");
		sg8kAction4051300.put("parentId", 50000);

		List<ASObject> sg8kAction4051300Child = new ArrayList();
		ASObject sg8kAction4051301 = new ASObject();
		sg8kAction4051301.put("id", 4051301);
		sg8kAction4051301.put("name", "访问");
		sg8kAction4051301.put("parentId", 4051300);

		ASObject sg8kAction4051302 = new ASObject();
		sg8kAction4051302.put("id", 4051302);
		sg8kAction4051302.put("name", "设置");
		sg8kAction4051302.put("parentId", 4051300);

		sg8kAction4051300Child.add(sg8kAction4051301);
		sg8kAction4051300Child.add(sg8kAction4051302);

		sg8kAction4051300.put("children", sg8kAction4051300Child);

		// 获取外部数据源
		ASObject sg8kAction4051400 = new ASObject();
		sg8kAction4051400.put("id", 4051400);
		sg8kAction4051400.put("name", "获取外部数据设置");
		sg8kAction4051400.put("parentId", 50000);

		List<ASObject> sg8kAction4051400Child = new ArrayList();
		ASObject sg8kAction4051401 = new ASObject();
		sg8kAction4051401.put("id", 4051401);
		sg8kAction4051401.put("name", "访问");
		sg8kAction4051401.put("parentId", 4051400);

		sg8kAction4051400Child.add(sg8kAction4051401);

		sg8kAction4051400.put("children", sg8kAction4051400Child);

		List<ASObject> sg8kAction50000Child = new ArrayList();
		sg8kAction50000Child.add(sg8kAction51200);
		sg8kAction50000Child.add(sg8kAction50200);
		sg8kAction50000Child.add(sg8kAction50100);
		sg8kAction50000Child.add(sg8kAction50900);
		sg8kAction50000Child.add(sg8kAction51000);
		sg8kAction50000Child.add(sg8kAction4051300);
		sg8kAction50000Child.add(sg8kAction4051400);
		sg8kAction50000.put("children", sg8kAction50000Child);

		/**
		 * 事件屏蔽
		 */
		ASObject sg8kAction120000 = new ASObject();
		sg8kAction120000.put("id", 120000);
		sg8kAction120000.put("name", "事件屏蔽");
		sg8kAction120000.put("parentId", 4000000);

		ASObject sg8kAction120100 = new ASObject();
		sg8kAction120100.put("id", 120100);
		sg8kAction120100.put("name", "用户事件屏蔽管理");
		sg8kAction120100.put("parentId", 120000);
		List<ASObject> sg8kAction120100Child = new ArrayList();
		ASObject sg8kAction120101 = new ASObject();
		sg8kAction120101.put("id", 120101);
		sg8kAction120101.put("name", "修改");
		sg8kAction120101.put("parentId", 120100);
		sg8kAction120100Child.add(sg8kAction120101);
		sg8kAction120100.put("children", sg8kAction120100Child);

		ASObject sg8kAction120200 = new ASObject();
		sg8kAction120200.put("id", 120200);
		sg8kAction120200.put("name", "事件推送设置");
		sg8kAction120200.put("parentId", 120000);
		List<ASObject> sg8kAction120200Child = new ArrayList();
		ASObject sg8kAction120201 = new ASObject();
		sg8kAction120201.put("id", 120201);
		sg8kAction120201.put("name", "修改");
		sg8kAction120201.put("parentId", 120200);
		sg8kAction120200Child.add(sg8kAction120201);
		sg8kAction120200.put("children", sg8kAction120200Child);

		List<ASObject> sg8kAction120000Child = new ArrayList();
		sg8kAction120000Child.add(sg8kAction120100);
		sg8kAction120000Child.add(sg8kAction120200);

		sg8kAction120000.put("children", sg8kAction120000Child);
		/**
		 * 事件屏蔽
		 */

		actionDetail4Child.add(sg8kAction10000);
		actionDetail4Child.add(sg8kAction50000);
		actionDetail4Child.add(sg8kAction120000);

		commonAction.put("children", actionDetail4Child);
	}

	private static void set9KAction(ASObject sg9kAction) {
		/*
		 * List<ASObject> actionDetail3Child = new ArrayList(); ASObject
		 * sg9kAction10000 = new ASObject(); sg9kAction10000.put("id", 210000);
		 * sg9kAction10000.put("name", "常规图谱"); sg9kAction10000.put("parentId",
		 * 200000);
		 * 
		 * List<ASObject> sg9kAction10000Child = new ArrayList();
		 * 
		 * ASObject sg9kAction10100 = new ASObject(); sg9kAction10100.put("id",
		 * 210100); sg9kAction10100.put("name", "总貌图");
		 * sg9kAction10100.put("parentId", 210000); List<ASObject>
		 * sg9kAction10100Child = new ArrayList(); ASObject sg9kAction10101 =
		 * new ASObject(); sg9kAction10101.put("id", 210101);
		 * sg9kAction10101.put("name", "访问"); sg9kAction10101.put("parentId",
		 * 210100); sg9kAction10100Child.add(sg9kAction10101);
		 * sg9kAction10100.put("children", sg9kAction10100Child);
		 * sg9kAction10000Child.add(sg9kAction10100);
		 * 
		 * sg9kAction10000.put("children", sg9kAction10000Child);
		 * 
		 * actionDetail3Child.add(sg9kAction10000);
		 * 
		 * sg9kAction.put("children", actionDetail3Child);
		 */
		List<String> actions = Lists.newArrayList(
				"310000-常规图谱-300000", 
				"310100-总貌图-310000", "310101-访问-310100",
				"310200-波形图-310000", "310201-访问-310200", 
				"310300-趋势图-310000", "310301-访问-310300",
				"310400-PV图-310000","310401-访问-310400", 
				"310500-键相波形图-310000", "310501-访问-310500", 
				
				"320000-列表/报表-300000",
				"320100-参数列表-320000", "320101-访问-320100",
				"320200-报警列表-320000", "320201-访问-320200", 
				
				"330000-设置-300000",
				"330100-机组编辑-330000", "330101-访问-330100", 
				"330200-机组设置-330000", "330201-访问-330200",
				"330300-总貌图设置-330000", "330301-访问-330300", 
				"330400-采集器设置-330000", "330401-访问-330400",
				"330500-分段报警设置-330000", "330501-访问-330500",
				"330600-包络报警设置-330000", "330601-访问-330600",
				"330700-支持平台设置-330000", "330701-访问-330700",
				
				"340000-界面框架-300000",
				"340100-系统日志-340000","340101-访问-340100",
				"340200-报警灯-340000","340201-访问-340200"
				
				);
		sg9kAction.put("children", getActions(actions, 300000));
	}

	// [{id:1,name:2,parentId:2}]
	public static List<ASObject> getActions(List<String> actions, Integer pid) {
		List<ASObject> lists = preToChangeAsObject(actions);
		List<ASObject> results = Lists.newArrayList();
		for (ASObject root : lists) {
			Integer parentId = (Integer) root.get("parentId");
			if (parentId.compareTo(pid) == 0) {
				results.add(root);
			}
		}
		for (ASObject root : results) {
			setChilds(root, lists);
		}
		return results;
	}

	public static void setChilds(ASObject root, List<ASObject> lists) {

		Integer rootId = (Integer) root.get("id");
		Integer parentId = null;
		List<ASObject> childs = (List<ASObject>) root.get("children");

		for (ASObject child : lists) {
			parentId = (Integer) child.get("parentId");
			if (parentId.compareTo(rootId) == 0) {
				// 找到
				if (null == childs) {
					childs = new ArrayList<ASObject>();
					root.put("children", childs);
				}
				childs.add(child);
				setChilds(child, lists);
			}
		}
	}

	public static List<ASObject> preToChangeAsObject(List<String> actions) {
		List<ASObject> lists = Lists.newArrayList();
		ASObject one = null;
		for (String action : actions) {
			one = new ASObject();
			String[] oneStrings = action.split("-");
			one.put("id", Integer.parseInt(oneStrings[0]));
			one.put("name", oneStrings[1]);
			one.put("parentId", Integer.parseInt(oneStrings[2]));
			lists.add(one);
		}
		return lists;
	}

	private static void set8kAction(ASObject sg8kAction) {

		List<ASObject> actionDetail2Child = new ArrayList();

		/**
		 * 常规图谱
		 */
		ASObject sg8kAction10000 = new ASObject();
		sg8kAction10000.put("id", 10000);
		sg8kAction10000.put("name", "常规图谱");
		sg8kAction10000.put("parentId", 2000000);

		/*
		 * ASObject sg8kAction10100 = new ASObject(); sg8kAction10100.put("id",
		 * 10100); sg8kAction10100.put("name", "总貌图");
		 * sg8kAction10100.put("parentId", 10000); List<ASObject>
		 * sg8kAction10100Child = new ArrayList(); ASObject sg8kAction10101 =
		 * new ASObject(); sg8kAction10101.put("id", 10101);
		 * sg8kAction10101.put("name", "访问"); sg8kAction10101.put("parentId",
		 * 10100); sg8kAction10100Child.add(sg8kAction10101);
		 * sg8kAction10100.put("children", sg8kAction10100Child);
		 */

		ASObject sg8kAction10200 = new ASObject();
		sg8kAction10200.put("id", 10200);
		sg8kAction10200.put("name", "波形频谱图");
		sg8kAction10200.put("parentId", 10000);
		List<ASObject> sg8kAction10200Child = new ArrayList();
		ASObject sg8kAction10201 = new ASObject();
		sg8kAction10201.put("id", 10201);
		sg8kAction10201.put("name", "访问");
		sg8kAction10201.put("parentId", 10200);
		sg8kAction10200Child.add(sg8kAction10201);
		sg8kAction10200.put("children", sg8kAction10200Child);

		ASObject sg8kAction10300 = new ASObject();
		sg8kAction10300.put("id", 10300);
		sg8kAction10300.put("name", "趋势图");
		sg8kAction10300.put("parentId", 10000);
		List<ASObject> sg8kAction10300Child = new ArrayList();
		ASObject sg8kAction10301 = new ASObject();
		sg8kAction10301.put("id", 10301);
		sg8kAction10301.put("name", "访问");
		sg8kAction10301.put("parentId", 10300);
		sg8kAction10300Child.add(sg8kAction10301);
		sg8kAction10300.put("children", sg8kAction10300Child);

		ASObject sg8kAction10400 = new ASObject();
		sg8kAction10400.put("id", 10400);
		sg8kAction10400.put("name", "相关分析图");
		sg8kAction10400.put("parentId", 10000);
		List<ASObject> sg8kAction10400Child = new ArrayList();
		ASObject sg8kAction10401 = new ASObject();
		sg8kAction10401.put("id", 10401);
		sg8kAction10401.put("name", "访问");
		sg8kAction10401.put("parentId", 10400);
		sg8kAction10400Child.add(sg8kAction10401);
		sg8kAction10400.put("children", sg8kAction10400Child);

		ASObject sg8kAction10500 = new ASObject();
		sg8kAction10500.put("id", 10500);
		sg8kAction10500.put("name", "轴心轨迹图");
		sg8kAction10500.put("parentId", 10000);
		List<ASObject> sg8kAction10500Child = new ArrayList();
		ASObject sg8kAction10501 = new ASObject();
		sg8kAction10501.put("id", 10501);
		sg8kAction10501.put("name", "访问");
		sg8kAction10501.put("parentId", 10500);
		sg8kAction10500Child.add(sg8kAction10501);
		sg8kAction10500.put("children", sg8kAction10500Child);

		ASObject sg8kAction10700 = new ASObject();
		sg8kAction10700.put("id", 10700);
		sg8kAction10700.put("name", "极坐标图");
		sg8kAction10700.put("parentId", 10000);
		List<ASObject> sg8kAction10700Child = new ArrayList();
		ASObject sg8kAction10701 = new ASObject();
		sg8kAction10701.put("id", 10701);
		sg8kAction10701.put("name", "访问");
		sg8kAction10701.put("parentId", 10700);
		sg8kAction10700Child.add(sg8kAction10701);
		sg8kAction10700.put("children", sg8kAction10700Child);

		ASObject sg8kAction10600 = new ASObject();
		sg8kAction10600.put("id", 10600);
		sg8kAction10600.put("name", "轴心位置图");
		sg8kAction10600.put("parentId", 10000);
		List<ASObject> sg8kAction10600Child = new ArrayList();
		ASObject sg8kAction10601 = new ASObject();
		sg8kAction10601.put("id", 10601);
		sg8kAction10601.put("name", "访问");
		sg8kAction10601.put("parentId", 10600);
		sg8kAction10600Child.add(sg8kAction10601);
		sg8kAction10600.put("children", sg8kAction10600Child);

		ASObject sg8kAction10800 = new ASObject();
		sg8kAction10800.put("id", 10800);
		sg8kAction10800.put("name", "键相波形");
		sg8kAction10800.put("parentId", 10000);
		List<ASObject> sg8kAction10800Child = new ArrayList();
		ASObject sg8kAction10801 = new ASObject();
		sg8kAction10801.put("id", 10801);
		sg8kAction10801.put("name", "访问");
		sg8kAction10801.put("parentId", 10800);
		sg8kAction10800Child.add(sg8kAction10801);
		sg8kAction10800.put("children", sg8kAction10800Child);

		List<ASObject> sg8kAction10000Child = new ArrayList();
		sg8kAction10000Child.add(sg8kAction10800);
		sg8kAction10000Child.add(sg8kAction10600);
		sg8kAction10000Child.add(sg8kAction10700);
		sg8kAction10000Child.add(sg8kAction10500);
		sg8kAction10000Child.add(sg8kAction10400);
		sg8kAction10000Child.add(sg8kAction10300);
		sg8kAction10000Child.add(sg8kAction10200);
		sg8kAction10000.put("children", sg8kAction10000Child);
		/**
		 * 常规图谱
		 */

		/**
		 * 启停机图谱
		 */
		ASObject sg8kAction20000 = new ASObject();
		sg8kAction20000.put("id", 20000);
		sg8kAction20000.put("name", "启停机图谱");
		sg8kAction20000.put("parentId", 2000000);

		ASObject sg8kAction20100 = new ASObject();
		sg8kAction20100.put("id", 20100);
		sg8kAction20100.put("name", "转速时间图");
		sg8kAction20100.put("parentId", 20000);
		List<ASObject> sg8kAction20100Child = new ArrayList();
		ASObject sg8kAction20101 = new ASObject();
		sg8kAction20101.put("id", 20101);
		sg8kAction20101.put("name", "访问");
		sg8kAction20101.put("parentId", 20100);
		sg8kAction20100Child.add(sg8kAction20101);
		sg8kAction20100.put("children", sg8kAction20100Child);

		ASObject sg8kAction20200 = new ASObject();
		sg8kAction20200.put("id", 20200);
		sg8kAction20200.put("name", "Bode图");
		sg8kAction20200.put("parentId", 20000);
		List<ASObject> sg8kAction20200Child = new ArrayList();
		ASObject sg8kAction20201 = new ASObject();
		sg8kAction20201.put("id", 20201);
		sg8kAction20201.put("name", "访问");
		sg8kAction20201.put("parentId", 20200);
		sg8kAction20200Child.add(sg8kAction20201);
		sg8kAction20200.put("children", sg8kAction20200Child);

		ASObject sg8kAction20300 = new ASObject();
		sg8kAction20300.put("id", 20300);
		sg8kAction20300.put("name", "Nyquist图");
		sg8kAction20300.put("parentId", 20000);
		List<ASObject> sg8kAction20300Child = new ArrayList();
		ASObject sg8kAction20301 = new ASObject();
		sg8kAction20301.put("id", 20301);
		sg8kAction20301.put("name", "访问");
		sg8kAction20301.put("parentId", 20300);
		sg8kAction20300Child.add(sg8kAction20301);
		sg8kAction20300.put("children", sg8kAction20300Child);

		ASObject sg8kAction20400 = new ASObject();
		sg8kAction20400.put("id", 20400);
		sg8kAction20400.put("name", "瀑布图");
		sg8kAction20400.put("parentId", 20000);
		List<ASObject> sg8kAction20400Child = new ArrayList();
		ASObject sg8kAction20401 = new ASObject();
		sg8kAction20401.put("id", 20401);
		sg8kAction20401.put("name", "访问");
		sg8kAction20401.put("parentId", 20400);
		sg8kAction20400Child.add(sg8kAction20401);
		sg8kAction20400.put("children", sg8kAction20400Child);

		List<ASObject> sg8kAction20000Child = new ArrayList();
		sg8kAction20000Child.add(sg8kAction20100);
		sg8kAction20000Child.add(sg8kAction20200);
		sg8kAction20000Child.add(sg8kAction20300);
		sg8kAction20000Child.add(sg8kAction20400);

		sg8kAction20000.put("children", sg8kAction20000Child);
		/**
		 * 启停机图谱
		 */

		/**
		 * 列表模块
		 */
		ASObject sg8kAction30000 = new ASObject();
		sg8kAction30000.put("id", 30000);
		sg8kAction30000.put("name", "列表模块");
		sg8kAction30000.put("parentId", 2000000);

		ASObject sg8kAction30300 = new ASObject();
		sg8kAction30300.put("id", 30300);
		sg8kAction30300.put("name", "实时参数列表");
		sg8kAction30300.put("parentId", 30000);
		List<ASObject> sg8kAction30300Child = new ArrayList();
		ASObject sg8kAction30301 = new ASObject();
		sg8kAction30301.put("id", 30301);
		sg8kAction30301.put("name", "访问");
		sg8kAction30301.put("parentId", 30300);
		sg8kAction30300Child.add(sg8kAction30301);
		sg8kAction30300.put("children", sg8kAction30300Child);

		ASObject sg8kAction30400 = new ASObject();
		sg8kAction30400.put("id", 30400);
		sg8kAction30400.put("name", "历史参数列表");
		sg8kAction30400.put("parentId", 30000);
		List<ASObject> sg8kAction30400Child = new ArrayList();
		ASObject sg8kAction30401 = new ASObject();
		sg8kAction30401.put("id", 30401);
		sg8kAction30401.put("name", "访问");
		sg8kAction30401.put("parentId", 30400);
		sg8kAction30400Child.add(sg8kAction30401);
		sg8kAction30400.put("children", sg8kAction30400Child);

		ASObject sg8kAction30100 = new ASObject();
		sg8kAction30100.put("id", 30100);
		sg8kAction30100.put("name", "实时报警列表");
		sg8kAction30100.put("parentId", 30000);
		List<ASObject> sg8kAction30100Child = new ArrayList();
		ASObject sg8kAction30101 = new ASObject();
		sg8kAction30101.put("id", 30101);
		sg8kAction30101.put("name", "访问");
		sg8kAction30101.put("parentId", 30100);
		sg8kAction30100Child.add(sg8kAction30101);
		sg8kAction30100.put("children", sg8kAction30100Child);

		ASObject sg8kAction30200 = new ASObject();
		sg8kAction30200.put("id", 30200);
		sg8kAction30200.put("name", "历史报警列表");
		sg8kAction30200.put("parentId", 30000);
		List<ASObject> sg8kAction30200Child = new ArrayList();
		ASObject sg8kAction30201 = new ASObject();
		sg8kAction30201.put("id", 30201);
		sg8kAction30201.put("name", "访问");
		sg8kAction30201.put("parentId", 30200);
		sg8kAction30200Child.add(sg8kAction30201);
		sg8kAction30200.put("children", sg8kAction30200Child);

		ASObject sg8kAction30500 = new ASObject();
		sg8kAction30500.put("id", 30500);
		sg8kAction30500.put("name", "机组事件列表	");
		sg8kAction30500.put("parentId", 30000);
		List<ASObject> sg8kAction30500Child = new ArrayList();
		ASObject sg8kAction30501 = new ASObject();
		sg8kAction30501.put("id", 30501);
		sg8kAction30501.put("name", "访问");
		sg8kAction30501.put("parentId", 30500);
		sg8kAction30500Child.add(sg8kAction30501);
		sg8kAction30500.put("children", sg8kAction30500Child);

		List<ASObject> sg8kAction30000Child = new ArrayList();
		sg8kAction30000Child.add(sg8kAction30100);
		sg8kAction30000Child.add(sg8kAction30200);
		sg8kAction30000Child.add(sg8kAction30300);
		sg8kAction30000Child.add(sg8kAction30400);
		sg8kAction30000Child.add(sg8kAction30500);

		sg8kAction30000.put("children", sg8kAction30000Child);
		/**
		 * 列表模块
		 */

		/**
		 * 报表模块
		 */
		ASObject sg8kAction40000 = new ASObject();
		sg8kAction40000.put("id", 40000);
		sg8kAction40000.put("name", "报表模块");
		sg8kAction40000.put("parentId", 2000000);

		ASObject sg8kAction40200 = new ASObject();
		sg8kAction40200.put("id", 40200);
		sg8kAction40200.put("name", "设备运行报表");
		sg8kAction40200.put("parentId", 40000);
		List<ASObject> sg8kAction40200Child = new ArrayList();
		ASObject sg8kAction40201 = new ASObject();
		sg8kAction40201.put("id", 40201);
		sg8kAction40201.put("name", "访问");
		sg8kAction40201.put("parentId", 40200);
		sg8kAction40200Child.add(sg8kAction40201);
		sg8kAction40200.put("children", sg8kAction40200Child);

		ASObject sg8kAction40100 = new ASObject();
		sg8kAction40100.put("id", 40100);
		sg8kAction40100.put("name", "机组报表");
		sg8kAction40100.put("parentId", 40000);
		List<ASObject> sg8kAction40100Child = new ArrayList();
		ASObject sg8kAction40101 = new ASObject();
		sg8kAction40101.put("id", 40101);
		sg8kAction40101.put("name", "访问");
		sg8kAction40101.put("parentId", 40100);
		sg8kAction40100Child.add(sg8kAction40101);
		sg8kAction40100.put("children", sg8kAction40100Child);

		ASObject sg8kAction40300 = new ASObject();
		sg8kAction40300.put("id", 40300);
		sg8kAction40300.put("name", "厂级报表");
		sg8kAction40300.put("parentId", 40000);
		List<ASObject> sg8kAction40300Child = new ArrayList();
		ASObject sg8kAction40301 = new ASObject();
		sg8kAction40301.put("id", 40301);
		sg8kAction40301.put("name", "访问");
		sg8kAction40301.put("parentId", 40300);
		sg8kAction40300Child.add(sg8kAction40301);
		sg8kAction40300.put("children", sg8kAction40300Child);

		List<ASObject> sg8kAction40000Child = new ArrayList();
		sg8kAction40000Child.add(sg8kAction40100);
		sg8kAction40000Child.add(sg8kAction40200);
		sg8kAction40000Child.add(sg8kAction40300);
		sg8kAction40000.put("children", sg8kAction40000Child);
		/**
		 * 报表模块
		 */

		/**
		 * 设置模块
		 */
		ASObject sg8kAction50000 = new ASObject();
		sg8kAction50000.put("id", 50000);
		sg8kAction50000.put("name", "设置模块");
		sg8kAction50000.put("parentId", 2000000);

		ASObject sg8kAction50300 = new ASObject();
		sg8kAction50300.put("id", 50300);
		sg8kAction50300.put("name", "机组设置");
		sg8kAction50300.put("parentId", 50000);

		List<ASObject> sg8kAction50300Child = new ArrayList();
		ASObject sg8kAction50301 = new ASObject();
		sg8kAction50301.put("id", 50301);
		sg8kAction50301.put("name", "访问");
		sg8kAction50301.put("parentId", 50300);

		ASObject sg8kAction50302 = new ASObject();
		sg8kAction50302.put("id", 50302);
		sg8kAction50302.put("name", "添加");
		sg8kAction50302.put("parentId", 50300);

		ASObject sg8kAction50303 = new ASObject();
		sg8kAction50303.put("id", 50303);
		sg8kAction50303.put("name", "删除");
		sg8kAction50303.put("parentId", 50300);

		ASObject sg8kAction50304 = new ASObject();
		sg8kAction50304.put("id", 50304);
		sg8kAction50304.put("name", "修改");
		sg8kAction50304.put("parentId", 50300);
		sg8kAction50300Child.add(sg8kAction50301);
		sg8kAction50300Child.add(sg8kAction50302);
		sg8kAction50300Child.add(sg8kAction50303);
		sg8kAction50300Child.add(sg8kAction50304);

		sg8kAction50300.put("children", sg8kAction50300Child);

		ASObject sg8kAction50500 = new ASObject();
		sg8kAction50500.put("id", 50500);
		sg8kAction50500.put("name", "偏差学习/设置");
		sg8kAction50500.put("parentId", 50000);

		List<ASObject> sg8kAction50500Child = new ArrayList();
		ASObject sg8kAction50501 = new ASObject();
		sg8kAction50501.put("id", 50501);
		sg8kAction50501.put("name", "访问");
		sg8kAction50501.put("parentId", 50500);

		ASObject sg8kAction50504 = new ASObject();
		sg8kAction50504.put("id", 50504);
		sg8kAction50504.put("name", "修改");
		sg8kAction50504.put("parentId", 50500);

		sg8kAction50500Child.add(sg8kAction50501);
		sg8kAction50500Child.add(sg8kAction50504);

		sg8kAction50500.put("children", sg8kAction50500Child);

		/*
		 * ASObject sg8kAction50200 = new ASObject(); sg8kAction50200.put("id",
		 * 50200); sg8kAction50200.put("name", "总貌图设置");
		 * sg8kAction50200.put("parentId", 50000);
		 * 
		 * List<ASObject> sg8kAction50200Child = new ArrayList(); ASObject
		 * sg8kAction50201 = new ASObject(); sg8kAction50201.put("id", 50201);
		 * sg8kAction50201.put("name", "访问"); sg8kAction50201.put("parentId",
		 * 50200);
		 * 
		 * ASObject sg8kAction50202 = new ASObject(); sg8kAction50202.put("id",
		 * 50202); sg8kAction50202.put("name", "添加");
		 * sg8kAction50202.put("parentId", 50200);
		 * 
		 * ASObject sg8kAction50203 = new ASObject(); sg8kAction50203.put("id",
		 * 50203); sg8kAction50203.put("name", "删除");
		 * sg8kAction50203.put("parentId", 50200);
		 * 
		 * ASObject sg8kAction50204 = new ASObject(); sg8kAction50204.put("id",
		 * 50204); sg8kAction50204.put("name", "修改");
		 * sg8kAction50204.put("parentId", 50200);
		 * sg8kAction50200Child.add(sg8kAction50201);
		 * sg8kAction50200Child.add(sg8kAction50202);
		 * sg8kAction50200Child.add(sg8kAction50203);
		 * sg8kAction50200Child.add(sg8kAction50204);
		 * 
		 * sg8kAction50200.put("children", sg8kAction50200Child);
		 */

		ASObject sg8kAction50400 = new ASObject();
		sg8kAction50400.put("id", 50400);
		sg8kAction50400.put("name", "采集器设置");
		sg8kAction50400.put("parentId", 50000);

		List<ASObject> sg8kAction50400Child = new ArrayList();
		ASObject sg8kAction50401 = new ASObject();
		sg8kAction50401.put("id", 50401);
		sg8kAction50401.put("name", "访问");
		sg8kAction50401.put("parentId", 50400);

		ASObject sg8kAction50402 = new ASObject();
		sg8kAction50402.put("id", 50402);
		sg8kAction50402.put("name", "添加");
		sg8kAction50402.put("parentId", 50400);

		ASObject sg8kAction50403 = new ASObject();
		sg8kAction50403.put("id", 50403);
		sg8kAction50403.put("name", "删除");
		sg8kAction50403.put("parentId", 50400);

		ASObject sg8kAction50404 = new ASObject();
		sg8kAction50404.put("id", 50404);
		sg8kAction50404.put("name", "修改");
		sg8kAction50404.put("parentId", 50400);
		sg8kAction50400Child.add(sg8kAction50401);
		sg8kAction50400Child.add(sg8kAction50402);
		sg8kAction50400Child.add(sg8kAction50403);
		sg8kAction50400Child.add(sg8kAction50404);

		sg8kAction50400.put("children", sg8kAction50400Child);

		/*
		 * ASObject sg8kAction50100 = new ASObject(); sg8kAction50100.put("id",
		 * 50100); sg8kAction50100.put("name", "导航树设置");
		 * sg8kAction50100.put("parentId", 50000);
		 * 
		 * List<ASObject> sg8kAction50100Child = new ArrayList(); ASObject
		 * sg8kAction50101 = new ASObject(); sg8kAction50101.put("id", 50101);
		 * sg8kAction50101.put("name", "访问"); sg8kAction50101.put("parentId",
		 * 50100);
		 * 
		 * ASObject sg8kAction50102 = new ASObject(); sg8kAction50102.put("id",
		 * 50102); sg8kAction50102.put("name", "添加");
		 * sg8kAction50102.put("parentId", 50100);
		 * 
		 * ASObject sg8kAction50103 = new ASObject(); sg8kAction50103.put("id",
		 * 50103); sg8kAction50103.put("name", "删除");
		 * sg8kAction50103.put("parentId", 50100);
		 * 
		 * ASObject sg8kAction50104 = new ASObject(); sg8kAction50104.put("id",
		 * 50104); sg8kAction50104.put("name", "修改");
		 * sg8kAction50104.put("parentId", 50100);
		 * sg8kAction50100Child.add(sg8kAction50101);
		 * sg8kAction50100Child.add(sg8kAction50102);
		 * sg8kAction50100Child.add(sg8kAction50103);
		 * sg8kAction50100Child.add(sg8kAction50104);
		 * 
		 * sg8kAction50100.put("children", sg8kAction50100Child);
		 */

		ASObject sg8kAction50700 = new ASObject();
		sg8kAction50700.put("id", 50700);
		sg8kAction50700.put("name", "厂级报表设置");
		sg8kAction50700.put("parentId", 50000);

		List<ASObject> sg8kAction50700Child = new ArrayList();
		ASObject sg8kAction50701 = new ASObject();
		sg8kAction50701.put("id", 50701);
		sg8kAction50701.put("name", "访问");
		sg8kAction50701.put("parentId", 50700);

		ASObject sg8kAction50704 = new ASObject();
		sg8kAction50704.put("id", 50704);
		sg8kAction50704.put("name", "修改");
		sg8kAction50704.put("parentId", 50700);
		sg8kAction50700Child.add(sg8kAction50701);
		sg8kAction50700Child.add(sg8kAction50704);

		sg8kAction50700.put("children", sg8kAction50700Child);

		ASObject sg8kAction50800 = new ASObject();
		sg8kAction50800.put("id", 50800);
		sg8kAction50800.put("name", "机组报表设置");
		sg8kAction50800.put("parentId", 50000);

		List<ASObject> sg8kAction50800Child = new ArrayList();
		ASObject sg8kAction50801 = new ASObject();
		sg8kAction50801.put("id", 50801);
		sg8kAction50801.put("name", "访问");
		sg8kAction50801.put("parentId", 50800);

		ASObject sg8kAction50804 = new ASObject();
		sg8kAction50804.put("id", 50804);
		sg8kAction50804.put("name", "修改");
		sg8kAction50804.put("parentId", 50800);
		sg8kAction50800Child.add(sg8kAction50801);
		sg8kAction50800Child.add(sg8kAction50804);

		sg8kAction50800.put("children", sg8kAction50800Child);

		/*
		 * ASObject sg8kAction50900 = new ASObject(); sg8kAction50900.put("id",
		 * 50900); sg8kAction50900.put("name", "用户管理");
		 * sg8kAction50900.put("parentId", 50000);
		 * 
		 * List<ASObject> sg8kAction50900Child = new ArrayList(); ASObject
		 * sg8kAction50901 = new ASObject(); sg8kAction50901.put("id", 50901);
		 * sg8kAction50901.put("name", "访问"); sg8kAction50901.put("parentId",
		 * 50900);
		 * 
		 * ASObject sg8kAction50902 = new ASObject(); sg8kAction50902.put("id",
		 * 50902); sg8kAction50902.put("name", "添加");
		 * sg8kAction50902.put("parentId", 50900);
		 * 
		 * ASObject sg8kAction50903 = new ASObject(); sg8kAction50903.put("id",
		 * 50903); sg8kAction50903.put("name", "删除");
		 * sg8kAction50903.put("parentId", 50900);
		 * 
		 * ASObject sg8kAction50904 = new ASObject(); sg8kAction50904.put("id",
		 * 50904); sg8kAction50904.put("name", "修改");
		 * sg8kAction50904.put("parentId", 50900);
		 * 
		 * ASObject sg8kAction50905 = new ASObject(); sg8kAction50905.put("id",
		 * 50905); sg8kAction50905.put("name", "修改组织结构权限");
		 * sg8kAction50905.put("parentId", 50900);
		 * 
		 * sg8kAction50900Child.add(sg8kAction50901);
		 * sg8kAction50900Child.add(sg8kAction50902);
		 * sg8kAction50900Child.add(sg8kAction50903);
		 * sg8kAction50900Child.add(sg8kAction50904);
		 * sg8kAction50900Child.add(sg8kAction50905);
		 * 
		 * sg8kAction50900.put("children", sg8kAction50900Child);
		 */

		/*
		 * ASObject sg8kAction51000 = new ASObject(); sg8kAction51000.put("id",
		 * 51000); sg8kAction51000.put("name", "角色管理");
		 * sg8kAction51000.put("parentId", 50000);
		 * 
		 * List<ASObject> sg8kAction51000Child = new ArrayList(); ASObject
		 * sg8kAction51001 = new ASObject(); sg8kAction51001.put("id", 51001);
		 * sg8kAction51001.put("name", "访问"); sg8kAction51001.put("parentId",
		 * 51000);
		 * 
		 * ASObject sg8kAction51002 = new ASObject(); sg8kAction51002.put("id",
		 * 51002); sg8kAction51002.put("name", "添加");
		 * sg8kAction51002.put("parentId", 51000);
		 * 
		 * ASObject sg8kAction51003 = new ASObject(); sg8kAction51003.put("id",
		 * 51003); sg8kAction51003.put("name", "删除");
		 * sg8kAction51003.put("parentId", 51000);
		 * 
		 * ASObject sg8kAction51004 = new ASObject(); sg8kAction51004.put("id",
		 * 51004); sg8kAction51004.put("name", "修改");
		 * sg8kAction51004.put("parentId", 51000);
		 * 
		 * sg8kAction51000Child.add(sg8kAction51001);
		 * sg8kAction51000Child.add(sg8kAction51002);
		 * sg8kAction51000Child.add(sg8kAction51003);
		 * sg8kAction51000Child.add(sg8kAction51004);
		 * 
		 * sg8kAction51000.put("children", sg8kAction51000Child);
		 */

		ASObject sg8kAction50600 = new ASObject();
		sg8kAction50600.put("id", 50600);
		sg8kAction50600.put("name", "采集器升级");
		sg8kAction50600.put("parentId", 50000);

		List<ASObject> sg8kAction50600Child = new ArrayList();
		ASObject sg8kAction50601 = new ASObject();
		sg8kAction50601.put("id", 50601);
		sg8kAction50601.put("name", "访问");
		sg8kAction50601.put("parentId", 50600);

		ASObject sg8kAction50602 = new ASObject();
		sg8kAction50602.put("id", 50602);
		sg8kAction50602.put("name", "添加");
		sg8kAction50602.put("parentId", 50600);

		ASObject sg8kAction50603 = new ASObject();
		sg8kAction50603.put("id", 50603);
		sg8kAction50603.put("name", "删除");
		sg8kAction50603.put("parentId", 50600);

		ASObject sg8kAction50604 = new ASObject();
		sg8kAction50604.put("id", 50604);
		sg8kAction50604.put("name", "修改");
		sg8kAction50604.put("parentId", 50600);

		sg8kAction50600Child.add(sg8kAction50601);
		sg8kAction50600Child.add(sg8kAction50602);
		sg8kAction50600Child.add(sg8kAction50603);
		sg8kAction50600Child.add(sg8kAction50604);

		sg8kAction50600.put("children", sg8kAction50600Child);

		ASObject sg8kAction51100 = new ASObject();
		sg8kAction51100.put("id", 51100);
		sg8kAction51100.put("name", "OPC连接");
		sg8kAction51100.put("parentId", 50000);

		List<ASObject> sg8kAction51100Child = new ArrayList();
		ASObject sg8kAction51101 = new ASObject();
		sg8kAction51101.put("id", 51101);
		sg8kAction51101.put("name", "访问");
		sg8kAction51101.put("parentId", 51100);

		sg8kAction51100Child.add(sg8kAction51101);

		sg8kAction51100.put("children", sg8kAction51100Child);

		/*
		 * ASObject sg8kAction51200 = new ASObject(); sg8kAction51200.put("id",
		 * 51200); sg8kAction51200.put("name", "远程中心设置");
		 * sg8kAction51200.put("parentId", 50000);
		 * 
		 * List<ASObject> sg8kAction51200Child = new ArrayList(); ASObject
		 * sg8kAction51201 = new ASObject(); sg8kAction51201.put("id", 51201);
		 * sg8kAction51201.put("name", "访问"); sg8kAction51201.put("parentId",
		 * 51200);
		 * 
		 * sg8kAction51200Child.add(sg8kAction51201);
		 * 
		 * sg8kAction51200.put("children", sg8kAction51200Child);
		 */

		List<ASObject> sg8kAction50000Child = new ArrayList();
		// sg8kAction50000Child.add(sg8kAction50100);
		// sg8kAction50000Child.add(sg8kAction50200);
		sg8kAction50000Child.add(sg8kAction50300);
		sg8kAction50000Child.add(sg8kAction50400);
		sg8kAction50000Child.add(sg8kAction50500);
		sg8kAction50000Child.add(sg8kAction50600);
		sg8kAction50000Child.add(sg8kAction50700);
		sg8kAction50000Child.add(sg8kAction50800);
		// sg8kAction50000Child.add(sg8kAction50900);
		sg8kAction50000Child.add(sg8kAction51100);
		// sg8kAction50000Child.add(sg8kAction51200);

		sg8kAction50000.put("children", sg8kAction50000Child);
		/**
		 * 设置模块
		 */

		/**
		 * 报表模块
		 */
		ASObject sg8kAction61000 = new ASObject();
		sg8kAction61000.put("id", 61000);
		sg8kAction61000.put("name", "界面框架");
		sg8kAction61000.put("parentId", 2000000);

		ASObject sg8kAction60100 = new ASObject();
		sg8kAction60100.put("id", 60100);
		sg8kAction60100.put("name", "系统日志");
		sg8kAction60100.put("parentId", 61000);
		List<ASObject> sg8kAction60100Child = new ArrayList();
		ASObject sg8kAction60101 = new ASObject();
		sg8kAction60101.put("id", 60101);
		sg8kAction60101.put("name", "访问");
		sg8kAction60101.put("parentId", 60101);
		sg8kAction60100Child.add(sg8kAction60101);
		sg8kAction60100.put("children", sg8kAction60100Child);

		ASObject sg8kAction60200 = new ASObject();
		sg8kAction60200.put("id", 60200);
		sg8kAction60200.put("name", "报警灯");
		sg8kAction60200.put("parentId", 61000);
		List<ASObject> sg8kAction60200Child = new ArrayList();
		ASObject sg8kAction60201 = new ASObject();
		sg8kAction60201.put("id", 60201);
		sg8kAction60201.put("name", "访问");
		sg8kAction60201.put("parentId", 60200);
		sg8kAction60200Child.add(sg8kAction60201);
		sg8kAction60200.put("children", sg8kAction60200Child);

		ASObject sg8kAction60300 = new ASObject();
		sg8kAction60300.put("id", 60300);
		sg8kAction60300.put("name", "调试信息");
		sg8kAction60300.put("parentId", 61000);
		List<ASObject> sg8kAction60300Child = new ArrayList();
		ASObject sg8kAction60301 = new ASObject();
		sg8kAction60301.put("id", 60301);
		sg8kAction60301.put("name", "访问");
		sg8kAction60301.put("parentId", 60300);
		sg8kAction60300Child.add(sg8kAction60301);
		sg8kAction60300.put("children", sg8kAction60300Child);

		List<ASObject> sg8kAction61000Child = new ArrayList();
		sg8kAction61000Child.add(sg8kAction60100);
		sg8kAction61000Child.add(sg8kAction60200);
		sg8kAction61000Child.add(sg8kAction60300);

		sg8kAction61000.put("children", sg8kAction61000Child);
		/**
		 * 报表模块
		 */

		/**
		 * 维护
		 */
		ASObject sg8kAction70000 = new ASObject();
		sg8kAction70000.put("id", 70000);
		sg8kAction70000.put("name", "维护");
		sg8kAction70000.put("parentId", 2000000);

		ASObject sg8kAction70100 = new ASObject();
		sg8kAction70100.put("id", 70100);
		sg8kAction70100.put("name", "数据管理");
		sg8kAction70100.put("parentId", 70000);
		List<ASObject> sg8kAction70100Child = new ArrayList();
		ASObject sg8kAction70101 = new ASObject();
		sg8kAction70101.put("id", 70101);
		sg8kAction70101.put("name", "删除");
		sg8kAction70101.put("parentId", 70100);
		sg8kAction70100Child.add(sg8kAction70101);
		sg8kAction70100.put("children", sg8kAction70100Child);

		List<ASObject> sg8kAction70000Child = new ArrayList();
		sg8kAction70000Child.add(sg8kAction70100);

		sg8kAction70000.put("children", sg8kAction70000Child);
		/**
		 * 维护
		 */

		/**
		 * 智能诊断
		 */
		ASObject sg8kAction80000 = new ASObject();
		sg8kAction80000.put("id", 80000);
		sg8kAction80000.put("name", "智能诊断");
		sg8kAction80000.put("parentId", 2000000);

		ASObject sg8kAction80100 = new ASObject();
		sg8kAction80100.put("id", 80100);
		sg8kAction80100.put("name", "诊断列表");
		sg8kAction80100.put("parentId", 80000);
		List<ASObject> sg8kAction80100Child = new ArrayList();
		ASObject sg8kAction80101 = new ASObject();
		sg8kAction80101.put("id", 80101);
		sg8kAction80101.put("name", "访问");
		sg8kAction80101.put("parentId", 80100);
		sg8kAction80100Child.add(sg8kAction80101);
		sg8kAction80100.put("children", sg8kAction80100Child);

		List<ASObject> sg8kAction80000Child = new ArrayList();
		sg8kAction80000Child.add(sg8kAction80100);

		sg8kAction80000.put("children", sg8kAction80000Child);
		/**
		 * 智能诊断
		 */

		/**
		 * 试车模块
		 */
		ASObject sg8kAction90000 = new ASObject();
		sg8kAction90000.put("id", 90000);
		sg8kAction90000.put("name", "试车模块");
		sg8kAction90000.put("parentId", 2000000);

		ASObject sg8kAction90100 = new ASObject();
		sg8kAction90100.put("id", 90100);
		sg8kAction90100.put("name", "数据管理");
		sg8kAction90100.put("parentId", 90000);
		List<ASObject> sg8kAction90100Child = new ArrayList();
		ASObject sg8kAction90101 = new ASObject();
		sg8kAction90101.put("id", 90101);
		sg8kAction90101.put("name", "访问");
		sg8kAction90101.put("parentId", 90100);
		sg8kAction90100Child.add(sg8kAction90101);
		sg8kAction90100.put("children", sg8kAction90100Child);

		List<ASObject> sg8kAction90000Child = new ArrayList();
		sg8kAction90000Child.add(sg8kAction90100);

		sg8kAction90000.put("children", sg8kAction90000Child);
		/**
		 * 试车模块
		 */

		/**
		 * 事件监视
		 */
		ASObject sg8kAction100000 = new ASObject();
		sg8kAction100000.put("id", 100000);
		sg8kAction100000.put("name", "事件监视");
		sg8kAction100000.put("parentId", 2000000);

		ASObject sg8kAction100100 = new ASObject();
		sg8kAction100100.put("id", 100100);
		sg8kAction100100.put("name", "事件监视管理");
		sg8kAction100100.put("parentId", 100000);
		List<ASObject> sg8kAction100100Child = new ArrayList();
		ASObject sg8kAction100101 = new ASObject();
		sg8kAction100101.put("id", 100101);
		sg8kAction100101.put("name", "访问");
		sg8kAction100101.put("parentId", 100100);

		ASObject sg8kAction100102 = new ASObject();
		sg8kAction100102.put("id", 100102);
		sg8kAction100102.put("name", "修改");
		sg8kAction100102.put("parentId", 100100);

		sg8kAction100100Child.add(sg8kAction100101);
		sg8kAction100100Child.add(sg8kAction100102);

		sg8kAction100100.put("children", sg8kAction100100Child);

		List<ASObject> sg8kAction100000Child = new ArrayList();
		sg8kAction100000Child.add(sg8kAction100100);

		sg8kAction100000.put("children", sg8kAction100000Child);
		/**
		 * 事件监视
		 */

		/**
		 * 报告生成
		 */
		ASObject sg8kAction110000 = new ASObject();
		sg8kAction110000.put("id", 110000);
		sg8kAction110000.put("name", "报告生成");
		sg8kAction110000.put("parentId", 2000000);

		ASObject sg8kAction110100 = new ASObject();
		sg8kAction110100.put("id", 110100);
		sg8kAction110100.put("name", "报告生成管理");
		sg8kAction110100.put("parentId", 110000);
		List<ASObject> sg8kAction110100Child = new ArrayList();
		ASObject sg8kAction110101 = new ASObject();
		sg8kAction110101.put("id", 110101);
		sg8kAction110101.put("name", "访问");
		sg8kAction110101.put("parentId", 110100);

		ASObject sg8kAction110102 = new ASObject();
		sg8kAction110102.put("id", 110102);
		sg8kAction110102.put("name", "修改");
		sg8kAction110102.put("parentId", 110100);

		sg8kAction110100Child.add(sg8kAction110101);
		sg8kAction110100Child.add(sg8kAction110102);
		sg8kAction110100.put("children", sg8kAction110100Child);

		List<ASObject> sg8kAction110000Child = new ArrayList();
		sg8kAction110000Child.add(sg8kAction110100);

		sg8kAction110000.put("children", sg8kAction110000Child);
		/**
		 * 报告生成
		 */

		/**
		 * 事件屏蔽 ASObject sg8kAction120000 = new ASObject();
		 * sg8kAction120000.put("id", 120000); sg8kAction120000.put("name",
		 * "事件屏蔽"); sg8kAction120000.put("parentId", 2);
		 * 
		 * ASObject sg8kAction120100 = new ASObject();
		 * sg8kAction120100.put("id", 120100); sg8kAction120100.put("name",
		 * "数据管理"); sg8kAction120100.put("parentId", 120000); List
		 * <ASObject> sg8kAction120100Child = new ArrayList(); ASObject
		 * sg8kAction120101 = new ASObject(); sg8kAction120101.put("id",
		 * 120101); sg8kAction120101.put("name", "访问");
		 * sg8kAction120101.put("parentId", 120101);
		 * sg8kAction120100Child.add(sg8kAction120101);
		 * sg8kAction120100.put("children", sg8kAction120100Child);
		 * 
		 * 
		 * List<ASObject> sg8kAction120000Child = new ArrayList();
		 * sg8kAction120000Child.add(sg8kAction120100);
		 * 
		 * sg8kAction120000.put("children", sg8kAction120000Child);
		 */

		/**
		 * 故障诊断案例库
		 */
		ASObject sg8kAction130000 = new ASObject();
		sg8kAction130000.put("id", 130000);
		sg8kAction130000.put("name", "故障诊断案例库");
		sg8kAction130000.put("parentId", 2000000);

		ASObject sg8kAction130100 = new ASObject();
		sg8kAction130100.put("id", 130100);
		sg8kAction130100.put("name", "故障诊断案例库管理");
		sg8kAction130100.put("parentId", 130000);
		List<ASObject> sg8kAction130100Child = new ArrayList();
		ASObject sg8kAction130101 = new ASObject();
		sg8kAction130101.put("id", 130101);
		sg8kAction130101.put("name", "访问");
		sg8kAction130101.put("parentId", 130101);
		sg8kAction130100Child.add(sg8kAction130101);
		sg8kAction130100.put("children", sg8kAction130100Child);

		List<ASObject> sg8kAction130000Child = new ArrayList();
		sg8kAction130000Child.add(sg8kAction130100);

		sg8kAction130000.put("children", sg8kAction130000Child);
		/**
		 * 故障诊断案例库
		 */

		actionDetail2Child.add(sg8kAction10000);
		actionDetail2Child.add(sg8kAction20000);
		actionDetail2Child.add(sg8kAction30000);
		actionDetail2Child.add(sg8kAction40000);
		actionDetail2Child.add(sg8kAction50000);
		actionDetail2Child.add(sg8kAction61000);
		actionDetail2Child.add(sg8kAction70000);
		actionDetail2Child.add(sg8kAction80000);
		actionDetail2Child.add(sg8kAction90000);
		actionDetail2Child.add(sg8kAction100000);
		actionDetail2Child.add(sg8kAction110000);
		// actionDetail2Child.add(sg8kAction120000);
		actionDetail2Child.add(sg8kAction130000);

		sg8kAction.put("children", actionDetail2Child);
	}
}
