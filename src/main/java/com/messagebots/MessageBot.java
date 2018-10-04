package com.messagebots;

import akka.actor.AbstractActor;

public class MessageBot extends AbstractActor {

	public static class Message {
		public final String m1;

		public Message(String message) {
			this.m1 = message;
		}
	}
	
	public static class Response {
		public final String r1;

		public Response(String response) {
			this.r1 = response;
		}
	}
	
	public Receive createReceive() {
		//case returns
	    return receiveBuilder()
	            .match(Message.class, this::onMessage)
	            .match(Response.class, this::onResponse)
	            .build();
	}
	
	private void onMessage(Message message) {
	    System.out.println(getSelf().path() + message.m1);
	    
	    getSender().tell(new Response(randomResponseGenerator()), getSelf());
	    /* terminates children randomly but I don't have handling
	     *  for the dead letters
	     * if (((int) (Math.random()*10+1) % 2) == 0) {
	        getContext().stop(getSelf());
	    }*/
	}
	
	private void onResponse(Response response) {
	    System.out.println(getSelf().path() + response.r1);

	    getSender().tell(new Message(randomMessageGenerator()), getSelf());
	    /* terminates children randomly but I don't have handling
	     *  for the dead letters
	     * if (((int) (Math.random()*10+1) % 2) == 0) {
	        getContext().stop(getSelf());
	    }*/
	}
	
	private String randomMessageGenerator() {
    	final String[] messages = {"Hey", "What's Up", "Hi", "What's Good"};
    	return messages[(int) (Math.random()* 4)];
    }
	
	private String randomResponseGenerator() {
		final String[] messages = {"I'm ok", "Tired", "Woooooh", "Goodbye"};
		return messages[(int) (Math.random()* 4)];
	}
	
}