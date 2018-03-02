package com.sgck.notification;

public interface NotificationProducer {
	public boolean sendNofication(String topic,String tag,byte[] content);
}
