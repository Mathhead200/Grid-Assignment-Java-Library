
import com.mathhead200.MH;
import com.mathhead200.array.*;
import java.util.*;
import java.lang.management.*;


public class ArrayTest
{
	public static void putTime(BinaryHashTree<Integer, String> tree) {
		Integer key = Integer.parseInt( MH.ask("[put] Enter a key (Integer): ") );
		String value = MH.ask("[put] Enter a value (String): "),
			ret;
		long time = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
		ret = tree.put(key, value);
		for( int i = 0; i < 99999; i++ )
			tree.put(key, value);
		time = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime() - time;
		System.out.println( "put() -> " + ret + ", time = " + (time / 1e9) + " s" );
	}

	public static void getTime(BinaryHashTree<Integer, String> tree) {
		Integer key = Integer.parseInt( MH.ask("[get] Enter a key (Integer): ") );
		String ret;
		long time = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
		ret = tree.get(key);
		for( int i = 0; i < 99999; i++ )
			tree.get(key);
		time = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime() - time;
		System.out.println( "get() -> " + ret + ", time = " + (time / 1e9) + " s" );
	}

	public static void removeTime(BinaryHashTree<Integer, String> tree) {
		Integer key = Integer.parseInt( MH.ask("[remove] Enter a key (Integer): ") );
		String ret;
		long time = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
		ret = tree.remove(key);
		for( int i = 0; i < 99999; i++ )
			tree.remove(key);
		time = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime() - time;
		System.out.println( "remove() -> " + ret + ", time = " + (time / 1e9) + " s" );
	}

	public static void balanceTime(BinaryHashTree<Integer, String> tree) {
		long time = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
		tree.balance();
		time = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime() - time;
		System.out.println( "balance(), time = " + (time / 1e9) + " s" );
	}

	public static void main(String[] args) {
		//! problems balancing the AVLTree
		BinaryHashTree<Integer, String> tree = new AVLBinaryHashTree<Integer, String>();
		Random rand = new Random();
		final int SIZE = 2393;

		System.out.print("Constructing unbalanced tree");
		for( int i = -SIZE; i <= SIZE; i++ ) {
			if( i % (SIZE / 5) == 0 )
				System.out.print('.');
			byte valBytes[] = new byte[rand.nextInt(8) + 1];
			rand.nextBytes(valBytes);
			tree.put( i, new String(valBytes) );
		}
		System.out.println(" Done!");
		//System.out.println( tree + "\n" );

		try {
			putTime(tree);
			//System.out.println( tree + "\n" );
		} catch(NumberFormatException e) {
			System.err.println( e + ": " + e.getLocalizedMessage() );
		}

		try {
			getTime(tree);
		} catch(NumberFormatException e) {
			System.err.println( e + ": " + e.getLocalizedMessage() );
		}

		try {
			removeTime(tree);
			//System.out.println( tree + "\n" );
		} catch(NumberFormatException e) {
			System.err.println( e + ": " + e.getLocalizedMessage() );
		}

		balanceTime(tree);
		//System.out.println( tree + "\n" );

		try {
			putTime(tree);
			//System.out.println( tree + "\n" );
		} catch(NumberFormatException e) {
			System.err.println( e + ": " + e.getLocalizedMessage() );
		}

		try {
			getTime(tree);
		} catch(NumberFormatException e) {
			System.err.println( e + ": " + e.getLocalizedMessage() );
		}

		try {
			removeTime(tree);
			//System.out.println( tree + "\n" );
		} catch(NumberFormatException e) {
			System.err.println( e + ": " + e.getLocalizedMessage() );
		}
	}
}
