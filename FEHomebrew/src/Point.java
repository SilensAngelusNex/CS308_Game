
/**
 * A class to represent points in I^2 (an ordered pair of integers)
 * @author Weston
 *
 */
public class Point implements Comparable<Point>{
		private int myX;
		private int myY;
		
		/**
		 * Constructs a point (x, x)
		 * @param x
		 * @param y
		 */
		public Point(int x, int y){
			myX = x;
			myY = y;
		}
		
		/**
		 * @return the Point's x value
		 */
		public int getX(){
			return myX;
		}
		/**
		 * @return the Point's x value
		 */
		public int getY(){
			return myY;
		}
		
		/**
		 * Overrides compareTo, so TreeSet will see two points with the same x and y values as the same object.
		 */
		@Override
		public int compareTo(Point p){
			if (myX != p.myX){
				return myX - p.myX;
			} else {
				return myY - p.myY;
			}
		}
		/**
		 * Overrides equals, so TreeSet will see two points with the same x and y values as the same object.
		 */
		@Override
		public boolean equals(Object o){
			return !(!(o instanceof Point) || compareTo((Point) o) != 0);
		}
		
		/**
		 * @return a point with a y value one greater than the given point.
		 */
		public Point up(){
			return new Point(myX, myY + 1);
		}
		/**
		 * @return a point with a y value one less than the given point.
		 */
		public Point down(){
			return new Point(myX, myY - 1);
		}
		/**
		 * @return a point with a x value one less than the given point.
		 */
		public Point left(){
			return new Point(myX - 1, myY);
		}
		/**
		 * @return a point with a x value one greater than the given point.
		 */
		public Point right(){
			return new Point(myX + 1, myY);
		}
		
		/**
		 * If the point is outside a region 0 <= x <= size, 0 <= y <= size, it moves to the point inside the region
		 * closest to it. If it is inside the region already, it is not changed.
		 * @param boxSize
		 */
		public void putInside(int boxSize){
			if (myX < 0)
				myX = 0;
			if (myX >= boxSize)
				myX = boxSize - 1;
			if (myY < 0)
				myY = 0;
			if (myY >= boxSize)
				myY = boxSize - 1;
		}
		
		/**
		 * Finds the squared euclidean disatnce between two points.
		 * @param p
		 * @return int
		 */
		public int sqrDist(Point p){
			return (myX - p.myX) * (myX - p.myX) + (myY - p.myY) * (myY - p.myY);
		}
		/**
		 * Finds the rectilinear distance (distance along a grid) between two points.
		 * @param p
		 * @return int
		 */
		public int rectDist(Point p){
			return Math.abs((myX - p.myX)) + Math.abs((myY - p.myY));
		}

		/**
		 * Returns "(x, y)"
		 */
		public String toString(){
			return String.format("(%d, %d)", myX, myY);
		}

	}