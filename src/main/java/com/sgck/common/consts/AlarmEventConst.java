package com.sgck.common.consts;

import java.util.ArrayList;
import java.util.List;

import flex.messaging.io.amf.ASObject;

public class AlarmEventConst {

	public  static List<ASObject> eventList = null;
	
	public final static List<ASObject> getEventList(){
		if(eventList == null){
			eventList = new ArrayList<ASObject>();
			
			//type 0测点，机组都有，1只有测点有，2只有机组有
			//belongFilterStatus屏蔽规则状态  0 屏蔽，1 开通 2 都有
			//belongSubSystem 属于子系统 SYSMON = 0;SYS6K = 1;SYS8K = 2;SYS9K = 3;
			ASObject alarm1 = new ASObject();
			alarm1.put("id", 1);
			alarm1.put("text", "高高报/低低报");
			alarm1.put("type", 0);
			alarm1.put("belongFilterStatus", 0);
			alarm1.put("belongSubSystem", 0);
			
			ASObject alarm2 = new ASObject();
			alarm2.put("id", 2);
			alarm2.put("text", "高报/低报");
			alarm2.put("type", 0);
			alarm2.put("belongFilterStatus", 0);
			alarm2.put("belongSubSystem", 0);
			
			ASObject alarm3 = new ASObject();
			alarm3.put("id", 3);
			alarm3.put("text", "启停机");
			alarm3.put("type", 2);
			alarm3.put("belongFilterStatus", 0);
			alarm3.put("belongSubSystem", 0);

			ASObject alarm4 = new ASObject();
			alarm4.put("id", 4);
			alarm4.put("text", "通频值偏差");
			alarm4.put("type", 0);
			alarm4.put("belongFilterStatus", 1);
			alarm4.put("belongSubSystem", 0);
			
			ASObject alarm5 = new ASObject();
			alarm5.put("id", 5);
			alarm5.put("text", "1x偏差");
			alarm5.put("type", 0);
			alarm5.put("belongFilterStatus", 1);
			alarm5.put("belongSubSystem", 0);
	
			ASObject alarm6 = new ASObject();
			alarm6.put("id", 6);
			alarm6.put("text", "2x偏差");
			alarm6.put("type", 0);
			alarm6.put("belongFilterStatus", 1);
			alarm6.put("belongSubSystem", 0);
			
			ASObject alarm7 = new ASObject();
			alarm7.put("id", 7);
			alarm7.put("text", "0.5x偏差");
			alarm7.put("type", 0);
			alarm7.put("belongFilterStatus", 1);
			alarm7.put("belongSubSystem", 0);
			
			ASObject alarm8 = new ASObject();
			alarm8.put("id", 8);
			alarm8.put("text", "可选偏差");
			alarm8.put("type", 0);
			alarm8.put("belongFilterStatus", 1);
			alarm8.put("belongSubSystem", 0);
			
			ASObject alarm9 = new ASObject();
			alarm9.put("id", 9);
			alarm9.put("text", "残余量偏差");
			alarm9.put("type", 0);
			alarm9.put("belongFilterStatus", 1);
			alarm9.put("belongSubSystem", 0);
			
			eventList.add(alarm1);
			eventList.add(alarm2);
			eventList.add(alarm3);
			eventList.add(alarm4);
			eventList.add(alarm5);
			eventList.add(alarm6);
			eventList.add(alarm7);
			eventList.add(alarm8);
			eventList.add(alarm9);

		}
		return eventList;
	}

	
}
