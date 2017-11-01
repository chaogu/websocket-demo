package hello;

import spark.Spark;

public class HelloWorld {
	public static void main(String[] args) {
		EchoWebSocket socket = new EchoWebSocket();
		Spark.webSocket("/echo", new EchoWebSocket());

		Spark.get("/brocast", (req, res) -> {
			socket.brocast();
			return "done";
		});
	}
}