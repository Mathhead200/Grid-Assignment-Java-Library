
import com.mathhead200.math.*;
import com.mathhead200.MH;


public class TestMath
{
	public static void main(String[] args) {
		String s = args.length > 0 ? args[0] : MH.ask("Enter an equation: ");

		StringEquation eq = new StringEquation(s);
		System.out.println( " " + eq );
		System.out.println( "Answer: " + eq.solve() );
	}
}
