
import com.mathhead200.MH;
import com.mathhead200.math.CMath;
import com.mathhead200.math.StringEquation;


public class TestMath2
{
	public static void main(String[] args) {
		String str = MH.ask("Enter a number: ");
		long num = (long)new StringEquation(str).solve(),
			phi = CMath.eulerPhi(num);
		String answer = String.format( "phi(%d) = %d\nPrime-ness: %f%%", num, phi, 100.0*phi/(num - 1) );
		System.out.println(answer);
	}

}
