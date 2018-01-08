package com.mathhead200.dm.test;

import javax.swing.*;
import com.mathhead200.dm.*;


public class MapTest
{
	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame("Map Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		World world = new World( 20, 20, 1, World.DEFAULT_AREA, Grass.class );
		Client client = new Client(world);

		Bug me = new Bug();
		world.add( me, 3, 3, 0 );
		client.mob = me;

		frame.getContentPane().add(client);
		frame.pack();
		frame.setVisible(true);
	}
}
