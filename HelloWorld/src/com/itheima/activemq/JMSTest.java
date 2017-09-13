package com.itheima.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class JMSTest {

	@Test
	public void testProducer() {
		// 连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		// 连接
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			// 会话 接受或者发送消息的线程
			Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			// 消息的目的地
			Destination destination = session.createQueue("Hello World!");
			// 消息生产者
			MessageProducer messageProducer = session.createProducer(destination);
			for (int i = 0; i < 10; i++) {
				TextMessage message = session.createTextMessage("发布消息:" + i);
				System.out.println(message.getText());
				messageProducer.send(message);
			}
			session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void testConsumer() {
		// 连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		// 连接
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			// 会话 接受或者发送消息的线程
			Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			// 消息的目的地
			Destination destination = session.createQueue("Hello World!");
			// 消息消费者
			MessageConsumer createConsumer = session.createConsumer(destination);
			// 创建某个目的地消息的监听器
			createConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {
						String text = ((TextMessage) message).getText();
						System.out.println("接收消息:" + text);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (JMSException e) {
			e.printStackTrace();
		}
//		while (true) {
//
//		}
	}

}
