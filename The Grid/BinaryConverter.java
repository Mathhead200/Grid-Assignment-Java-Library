

public class BinaryConverter
{
	public static void main(String[] args)
	{
		System.out.println( Integer.toBinaryString(
			Integer.parseInt("1101010111", 2) +
			Integer.parseInt("111100", 2) +
			Integer.parseInt("1", 2) +
			Integer.parseInt("11", 2) +
			Integer.parseInt("101", 2)
		));
	}
}
