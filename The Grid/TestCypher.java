
import java.io.*;
import com.mathhead200.MH;


public class TestCypher
{
	public static void main(String[] args) throws IOException, InterruptedException {
		BufferedReader in = new BufferedReader( new FileReader("cipher1.txt") );
		String sypher = "";
		for( int i = in.read(); i >= 0; i = in.read() )
			sypher += (char)i;

		PrintWriter out;
		{
			File f = new File("decrypted1/key_file.txt");
			if( !f.isFile() )
				f.createNewFile();
			out = new PrintWriter( new FileWriter(f) );
		}

		char start = (char)1, end = (char)127;
		char[] key = { start, start, start };
		do {
			String k = new String(key);
			//System.out.println(k);

			String decrypt = MH.encryptString( sypher, k );

			int alpha = 0, space = 0, other = 0;
			for( char c : decrypt.toCharArray() )
				if( ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') || ('0' <= c && c <= '9') )
					alpha++;
				else if( c == ' ' || c == '\n' || c == '\t' )
					space++;
				else
					other++;
				int total = alpha + space + other;
			if( alpha >= .75 * total && space >= .10 * total ) {
				File outFile = new File("decrypted1/" + (int)key[0] + "-" + (int)key[1] + "-" + (int)key[2] + ".txt");
				if( !outFile.isFile() )
					try {
						outFile.createNewFile();
					} catch(IOException e) {
						System.err.println(e);
						System.err.println(outFile);
					}
				out.println( k + " " + outFile );
				System.out.println( "Found: " + k + " " + outFile );
				FileWriter fOut = new FileWriter(outFile);
				fOut.write(decrypt);
				fOut.close();
			}

			for( int i = 0; i < key.length; i++ ) {
				if( i == 2 )
					System.out.println( "Done: __" + key[2] + " (" + (int)key[2] + ")" );
				key[i] = (char)( 1 + (int)key[i] );
				if( (int)key[i] > (int)end )
					key[i] = start;
				else
					break;
			}
		} while( key[0] > start || key[1] > start || key[2] > start );

		out.close();
	}
}
