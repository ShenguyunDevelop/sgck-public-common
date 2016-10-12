package com.sgck.core.eventbus;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicBoolean;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;

public abstract class SEventListener {
	private Map<String,String> eventHandlerMap = new ConcurrentHashMap<String,String>();
	private Map<String, Method> methodCache = new ConcurrentHashMap<String, Method>();
	
	//protected AtomicBoolean isSubcribed = new AtomicBoolean();
	protected boolean isSubcribed = false;
	
	@Handler
    public void handleSEvent(String message) {
		String actionName = eventHandlerMap.get(message);
		if(actionName != null){
			try {
				Method func = getMethodByName(actionName);
				func.invoke(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
	
	protected Method getMethodByName(String funcName) throws Exception
	{
		Method mt = methodCache.get(funcName);
		if (mt != null){
			return mt;
		}

		Method[] allMethod = this.getClass().getMethods();
		String internedName = funcName.intern();
		for (int i = 0; i < allMethod.length; i++){
			if (allMethod[i].getName() == internedName){ // 返回第一个，不支持重载
				mt = allMethod[i];
				break;
			}
		}

		if (mt != null){
			methodCache.put(funcName, mt);
		}

		return mt;
	}
	
	/**
	 * 添加事件响应函数,响应函数暂不支持参数
	 * @param eventName  事件名称
	 * @param funcName 当前类的成员函数名，现在只支持将当前类的成员函数作为事件响应函数，如果需要扩展为支持任意类的成员函数（甚至是静态函数）都很容易
	 */
	protected synchronized void on(String eventName,String funcName){
		eventHandlerMap.put(eventName,funcName);
		if (isSubcribed == false) {
			MBassador bus = SEventManager.createBus();
			bus.subscribe(this);
			isSubcribed = true;
		}
	}
	
	protected synchronized void un(String eventName){
		eventHandlerMap.remove(eventName);
		if(eventHandlerMap.isEmpty()){
			if (isSubcribed == true) {
				MBassador bus = SEventManager.createBus();
				bus.unsubscribe(this);
				isSubcribed = false;
			}
		}
	}
	
	/**
	 * on 函数的别名
	 */
	protected void addListener(String eventName,String funcName){
		on(eventName,funcName);
	}
	
	/**
	 * un 函数的别名
	 */
	protected void removeListener(String eventName){
		un(eventName);
	}
}
