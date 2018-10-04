package com.messagebots;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;

public class MessageBotMaster extends AbstractActor {
	// creates children 0-29
	public MessageBotMaster() {
		for (int i = 0; i < 30; i++) {
			final ActorRef child = getContext().actorOf(
					Props.create(MessageBot.class), "Bot" + i);
			getContext().watch(child);
		}
	}

	// case actions
	public Receive createReceive() {
		return receiveBuilder().match(StartChild.class, this::onStartChild)
				.match(Terminated.class, this::onChildTerminated).build();
	}

	// starting new child after termination
	private void onChildTerminated(Terminated terminated) {
		System.out.println("Child terminated, starting new one");
		final ActorRef child = getContext().actorOf(Props.create(MessageBot.class));
		getContext().watch(child);
	}

	//starts message chain
	private void onStartChild(StartChild startChild) {
		final MessageBot.Message message = new MessageBot.Message("And so it begins");
		for (ActorRef child : getContext().getChildren()) {
			child.tell(message, getContext().findChild("Bot" + (int) (Math.random() * 30)).get());
		}
	}

	public static class StartChild {
	}
}