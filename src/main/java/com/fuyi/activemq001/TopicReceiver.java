package com.fuyi.activemq001;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicReceiver {

	public static void main(String[] args) throws Exception {
		
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection conn = cf.createConnection();
		conn.start();
		
		final Session session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
		Destination dest = session.createTopic("fuyi-topic");
		
		MessageConsumer consumer = session.createConsumer(dest);
		consumer.setMessageListener(new MessageListener() {
			
			public void onMessage(Message message) {
				if(message instanceof TextMessage) {
					try {
						TextMessage textMsg = (TextMessage) message;
						System.out.println("收到topic-msg" + textMsg.getText());
						session.commit();
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		Thread.sleep(20000);
		session.close();
		conn.close();
	}

}
