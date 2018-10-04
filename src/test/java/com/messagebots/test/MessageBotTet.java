package com.messagebots.test;

import static org.junit.Assert.assertEquals;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.messagebots.MessageBot;
import com.messagebots.MessageBot.Response;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;

public class MessageBotTet {
	static ActorSystem system;

	@BeforeClass
	public static void setup() {
		system = ActorSystem.create();
	}

	@AfterClass
	public static void teardown() {
		TestKit.shutdownActorSystem(system);
		system = null;
	}

	@Test
	public void testResponse() {
		// tests that the actor responds to the sender with a string
		final TestKit testProbe = new TestKit(system);
		final ActorRef mBot1 = system.actorOf(Props.create(MessageBot.class), "Bot1");
		mBot1.tell(new MessageBot.Message("Hi"), testProbe.getRef());
		Response response = testProbe.expectMsgClass(Response.class);
		assertEquals(String.class, response.r1.getClass());
	}
}
