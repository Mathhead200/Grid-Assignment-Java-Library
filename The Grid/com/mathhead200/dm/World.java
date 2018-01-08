package com.mathhead200.dm;


public class World
{
 //Member Variables
	Square[][][] squares;

	private int mapx;
	private int mapy;
	private int mapz;
	private Dim iconSize = new Dim(32, 32);

	public Class<? extends Area> area;
	public Class<? extends Turf> turf;

	public static final Class<? extends Area> DEFAULT_AREA = Area.class;
	public static final Class<? extends Turf> DEFAULT_TURF = Turf.class;

 //Private Methods
	private void link(Atom atom, int x, int y, int z) {
		atom.world = this;
		atom.x = x;
		atom.y = y;
		atom.z = z;
	}

	private Square virginSquare(int x, int y, int z) {
		try {
			Square s = new Square( area.newInstance(), turf.newInstance() );
			link( s.area, x, y, z );
			link( s.turf, x, y, z );
			return s;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

 //Constructors
	public World(int mapx, int mapy, int mapz, Class<? extends Area> area, Class<? extends Turf> turf) {
		this.mapx = mapx;
		this.mapy = mapy;
		this.mapz = mapz;
		this.area = area;
		this.turf = turf;
		squares = new Square[mapx][mapy][mapz];
		for( int x = 0; x < mapx; x++ )
			for( int y = 0; y < mapy; y++ )
				for( int z = 0; z < mapz; z++ )
					squares[x][y][z] = virginSquare(x, y, z);
	}

	public World(int mapx, int mapy, int mapz) {
		this(mapx, mapy, mapz, DEFAULT_AREA, DEFAULT_TURF);
	}

 //Accessor Methods
	public int getMapx() {
		return mapx;
	}

	public int getMapy() {
		return mapy;
	}

	public int getMapz() {
		return mapz;
	}

	public Dim getIconSize() {
		return iconSize;
	}

 //Mutator Methods
	public void setMapx(int mapx) {
		Square[][][] nSqrs = new Square[mapx][mapy][mapz];
		for( int x = 0; x < this.mapx; x++ )
			for( int y = 0; y < mapy; y++ )
				for( int z = 0; z < mapz; z++ )
					nSqrs[x][y][z] = squares[x][y][z];
		for( int x = this.mapx; x < mapx; x++ )
			for( int y = 0; y < mapy; y++ )
				for( int z = 0; z < mapz; z++ )
					nSqrs[x][y][z] = virginSquare(x, y, z);
		this.squares = nSqrs;
		this.mapx = mapx;
	}

	public void setMapy(int mapy) {
		Square[][][] nSqrs = new Square[mapx][mapy][mapz];
		for( int x = 0; x < mapx; x++ ) {
			for( int y = 0; y < this.mapy; y++ )
				for( int z = 0; z < mapz; z++ )
					nSqrs[x][y][z] = squares[x][y][z];
			for( int y = this.mapy; y < mapy; y++ )
				for( int z = 0; z < mapz; z++ )
					nSqrs[x][y][z] = virginSquare(x, y, z);
		}
		this.squares = nSqrs;
		this.mapy = mapy;
	}

	public void setMapz(int mapz) {
		Square[][][] nSqrs = new Square[mapx][mapy][mapz];
		for( int x = 0; x < mapx; x++ )
			for( int y = 0; y < mapy; y++ ) {
				for( int z = 0; z < this.mapz; z++ )
					nSqrs[x][y][z] = squares[x][y][z];
				for( int z = this.mapz; z < mapz; z++ )
					nSqrs[x][y][z] = virginSquare(x, y, z);
			}
		this.squares = nSqrs;
		this.mapz = mapz;
	}

	public void setIconSize(Dim iconSize) {
		this.iconSize = iconSize;
	}

	public void setIconSize(int iconWidth, int iconHeight) {
		setIconSize( new Dim(iconWidth, iconHeight) );
	}

 //Other Methods
	public boolean add(Mob mob, Atom loc) {
		if( loc == null || loc.world != this )
			return false;
		squares[loc.x][loc.y][loc.z].mobs.add(mob);
		link( mob, loc.x, loc.y, loc.z );
		return true;
	}

	public boolean add(Mob mob, int x, int y, int z) {
		return add( mob, locate(x, y, z) );
	}

	public boolean add(Obj obj, Atom loc) {
		if( loc == null || loc.world != this )
			return false;
		squares[loc.x][loc.y][loc.z].objs.add(obj);
		link( obj, loc.x, loc.y, loc.z );
		return true;
	}

	public boolean add(Obj obj, int x, int y, int z) {
		return add( obj, locate(x, y, z) );
	}

	public boolean set(Turf turf, Atom loc) {
		if( loc == null || loc.world != this )
			return false;
		squares[loc.x][loc.y][loc.z].turf = turf;
		link( turf, loc.x, loc.y, loc.z );
		return true;
	}

	public boolean set(Turf turf, int x, int y, int z) {
		return set( turf, locate(x, y, z) );
	}

	public boolean set(Area area, Atom loc) {
		if( loc == null || loc.world != this )
			return false;
		squares[loc.x][loc.y][loc.z].area = area;
		link( area, loc.x, loc.y, loc.z );
		return true;
	}

	public boolean set(Area area, int x, int y, int z) {
		return set( area, locate(x, y, z) );
	}

	public Atom locate(int x, int y, int z) {
		if( x < 0 || x >= mapx
			|| y < 0 || y >= mapy
			|| z < 0 || z >= mapz
		)
			return null;
		return squares[x][y][z].turf;
	}
}
