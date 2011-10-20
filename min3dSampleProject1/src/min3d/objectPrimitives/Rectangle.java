package min3d.objectPrimitives;

import java.util.ArrayList;

import min3d.Utils;
import min3d.core.Object3dContainer;
import min3d.vos.Color4;

public class Rectangle extends Object3dContainer
{
	public Rectangle(float $width, float $height, int $segsW, int $segsH)
	{
		
		this($width, $height, $segsW, $segsH, new Color4(255, 0, 0, 255));
	}
	
	public Rectangle(float $width, float $height, int $segsW, int $segsH, Color4 color)
	{
		
		super(4 * $segsW * $segsH, 2 * $segsW * $segsH);
		float z = 0.0f;
		int row, col;

		float w = $width / $segsW;
		float h = $height / $segsH;

		float width5 = $width/2f;
		float height5 = $height/2f;
		
		// Add vertices
		
		for (row = 0; row <= $segsH; row++)
		{
			for (col = 0; col <= $segsW; col++)
			{
				this.vertices().addVertex(
					col*w - width5, row*h - height5,z,	
					(float)col/(float)$segsW, 1 - (float)row/(float)$segsH,	
					0,0,1f,	
					color.r, color.g, color.b, color.a
				);
			}
		}
		
		// Add faces
		
		int colspan = $segsW + 1;
		
		for (row = 1; row <= $segsH; row++)
		{
			for (col = 1; col <= $segsW; col++)
			{
				int lr = row * colspan + col;
				int ll = lr - 1;
				int ur = lr - colspan;
				int ul = ur - 1;
				Utils.addQuad(this, ul,ur,lr,ll);
			}
		}
	}
	 public void moveto(float xneu, float yneu) {
		 this.position().x = xneu;
		 this.position().y = yneu;
	 }

		public int x;
		public int y;
		public int width;
		public int height;
		public int id;
		public float spawntime = 0f;
		public int direction;
		public int size;
		
		public static ArrayList<Rectangle> alleEcke = new ArrayList<Rectangle>();
		
		private Rectangle() {
			this(0, 0, 0, 0);
		}
		
		
		
		public static Rectangle neuesEck() {
			Rectangle ergebnis = new Rectangle();
			alleEcke.add(ergebnis);
			ergebnis.direction = (int) Math.round((Math.random())); //0 righttoleft 1 lefttoright
			ergebnis.size = (int) Math.round((Math.random()*3)); // scale 0-3
			
			ergebnis.spawntime = System.currentTimeMillis();
			return ergebnis;	
		}

		/**
		 * Constructs a new <code>Rectangle</code> whose top-left corner is
		 * specified as (<code>x</code>,&nbsp;<code>y</code>) and whose width and
		 * height are specified by the arguments of the same name.
		 * 
		 * @param x
		 *            the specified x coordinate
		 * @param y
		 *            the specified y coordinate
		 * @param width
		 *            the width of the <code>Rectangle</code>
		 * @param height
		 *            the height of the <code>Rectangle</code>
		 */
		public Rectangle(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		public boolean contains(int X, int Y, int W, int H) {
			int w = this.width;
			int h = this.height;


			if ((w | h | W | H) < 0) {
				// At least one of the dimensions is negative...
				return false;
			}
			// Note: if any dimension is zero, tests below must return false...
			int x = this.x;
			int y = this.y;
			if (X < x || Y < y) {
				return false;
			}
			w += x;
			W += X;
			if (W <= X) {
				// X+W overflowed or W was zero, return false if...
				// either original w or W was zero or
				// x+w did not overflow or
				// the overflowed x+w is smaller than the overflowed X+W
				if (w >= x || W > w) {
					return false;
				}
			} else {
				// X+W did not overflow and W was not zero, return false if...
				// original w was zero or
				// x+w did not overflow and x+w is smaller than X+W
				if (w >= x && W > w) {
					return false;
				}
			}
			h += y;
			H += Y;
			if (H <= Y) {
				if (h >= y || H > h) {
					// System.out.println("r4");
					return false;
				}
			} else {
				if (h >= y && H > h) {
					// System.out.println("r5");
					return false;
				}
			}
			return true;
		}

		public boolean contains(int x, int y) {
			return inside(x, y);
		}

		public boolean inside(int X, int Y) {
			int w = this.width;
			int h = this.height;
			if ((w | h) < 0) {
				// At least one of the dimensions is negative...
				return false;
			}
			// Note: if either dimension is zero, tests below must return false...
			int x = this.x;
			int y = this.y;
			if (X < x || Y < y) {
				return false;
			}
			w += x;
			h += y;
			// overflow || intersect
			return ((w < x || w > X) && (h < y || h > Y));
		}

	

}
