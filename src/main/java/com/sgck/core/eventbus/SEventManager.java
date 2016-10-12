package com.sgck.core.eventbus;

import com.sgck.common.log.DSLogger;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import net.engio.mbassy.bus.error.PublicationError;

public class SEventManager {
	
	protected static IBusConfiguration configuration = null;
    protected static MBassador bus = null;
    
    protected static final IPublicationErrorHandler failingHandler = new IPublicationErrorHandler() {
        @Override
        public void handleError(PublicationError error) {
            DSLogger.error("MbassadorUtils::failingHandler " + error.getMessage());
        }
    };

    protected static IBusConfiguration SyncAsync() {
        return new BusConfiguration()
	            .addFeature(Feature.SyncPubSub.Default())
	            .addFeature(Feature.AsynchronousHandlerInvocation.Default())
	            .addFeature(Feature.AsynchronousMessageDispatch.Default());
    }

    public static synchronized MBassador createBus() {
    	if(bus == null){
    		if(configuration == null){
				configuration = SyncAsync();
			}
    		bus = new MBassador(configuration);
    		bus.addErrorHandler(failingHandler);
    	}
        return bus;
    }
    
	static public boolean addListener(SEventListener listener,String eventName,String funcName){
		if(listener != null){
			listener.addListener(eventName,funcName);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 同步发送事件
	 * @param eventName
	 */
	static public void fireEvent(String eventName){
		MBassador bus = createBus();
		bus.post(eventName).now();
	}
	
	/**
	 * 异步发送事件
	 * @param eventName
	 */
	static public void fireEventAsyn(String eventName){
		MBassador bus = createBus();
		bus.post(eventName).asynchronously();
	}
}
