
import java.io.*;
import java.net.*;
import com.mathhead200.MH;


public class SocketTest
{
	public static void main(String[] args) throws IOException
	{
		Socket echoSocket = null;
		PrintWriter socketOut = null;
		BufferedReader socketIn = null;

		try {
			echoSocket = new Socket("192.168.0.1", 7); //connect to the echo server on port 7
			socketOut = new PrintWriter( echoSocket.getOutputStream() );
			socketIn = new BufferedReader( new InputStreamReader( echoSocket.getInputStream() ) );

			while(true) {
				String text = MH.ask("Enter text: ");
				if( text == null || text.equals("") || text.equals("quit") )
					break;
				socketOut.println(text);
				System.out.println( echoSocket + ": " + socketIn.readLine() );
			}

		} finally {
			if( echoSocket != null )
				echoSocket.close();
		}
	}
}
