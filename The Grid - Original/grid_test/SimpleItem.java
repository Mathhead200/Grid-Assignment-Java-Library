package grid_test;

import com.mathhead200.grid.*;


public class SimpleItem extends GridItem
{
	public SimpleItem(String name) {
		super("grid_test/blank.png", name);
	}

	public String save() {
		return getName();
	}

	public static GridItem load(String save) {
		return new SimpleItem(save);
	}
}
