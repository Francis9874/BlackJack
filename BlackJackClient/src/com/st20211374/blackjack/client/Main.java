package com.st20211374.blackjack.client;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException { 
		String ip = "127.0.0.1";
		int port = 5056;
		
		SampleClient client = new SampleClient(ip, port);
		client.start();

		new InputCommandHandler(client.getInputStream());
	}
}
