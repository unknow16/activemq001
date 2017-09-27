package com.fuyi.activemq001;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicSender {

	public static void main(String[] args) throws Exception {
		//1.创建连接工厂，并创建启动连接
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection conn = cf.createConnection();
		conn.start();
		
		//2.创建会话和目的队列
		Session session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
		Destination dest = session.createTopic("fuyi-topic");
		
		//3.创建生产者，并发送消息
		MessageProducer producer = session.createProducer(dest);
		for(int i=0; i<3; i++) {
			Message msg = session.createTextMessage("topic-msg-" + i);
			producer.send(msg);
		}
		
		//4.关闭连接
		System.out.println("发送topic-msg完成！");
		session.commit();
		session.close();
		conn.close();
	}

}
