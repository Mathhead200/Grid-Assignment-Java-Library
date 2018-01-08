
import java.io.*;
import java.util.*;


public class TestingGrounds
{
	private static class MSWForm
	{
		private static final File USER_PASS_FILE = new File("usr_info.list");
		private final Hashtable<String, String> passwords = new Hashtable<String, String>(0);
		private String username, password, content;

		public MSWForm(/*HttpServletRequest httpRequest*/) {
			//...
			try {
				BufferedReader fileIn = new BufferedReader( new FileReader(USER_PASS_FILE) );
				for( String line = fileIn.readLine(); line != null; line = fileIn.readLine() ) {
					String[] list = line.split(",", 2);
					if( list.length < 2 || line.charAt(0) == '#' )
						continue;
					passwords.put( list[0], list[1] );
				}
				fileIn.close();
			} catch(IOException e) {
				e.printStackTrace();
				passwords.clear();
			}
		}

		public boolean isValid() {
			return passwords.get(username) == password;
		}

		public boolean update() {
			if( !isValid() )
				return false;
			try {
				FileWriter fileOut = new FileWriter( username + "/info.html" );
				fileOut.write(content);
				fileOut.close();
			} catch(IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}

	public static void main(String[] args) {
		java.io.PrintStream out = System.out;
		for( Class<?> c = args.getClass(); c != null; c = c.getSuperclass()  )
			out.println( c.getName() );
	}
}
