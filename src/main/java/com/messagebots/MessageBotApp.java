package com.messagebots;

import java.io.IOException;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MessageBotApp {
  public static void main(String[] args) throws IOException {
    final ActorSystem system = ActorSystem.create("Messages");
    
    //creates master that spawns and watches children
    final ActorRef masterBot = system.actorOf(
		    Props.create(MessageBotMaster.class),
		    "Master");
		masterBot.tell(new MessageBotMaster.StartChild(), ActorRef.noSender());
    
    System.out.println("Press Enter to terminate");
    System.in.read();
    system.terminate();
  }
}
