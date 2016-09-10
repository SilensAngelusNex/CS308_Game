

public class Point implements Comparable<Point>{
		private int x;
		private int y;
		
		public Point(int X, int Y){
			x = X;
			y = Y;
		}
		
		public int getX(){
			return x;
		}
		public int getY(){
			return y;
		}
		
		@Override
		public int compareTo(Point p){
			if (x != p.x){
				return x - p.x;
			} else {
				return y - p.y;
			}
		}
		
		@Override
		public boolean equals(Object o){
			return !(!(o instanceof Point) || compareTo((Point) o) != 0);
		}
		
		public Point up(){
			return new Point(x, y + 1);
		}
		public Point down(){
			return new Point(x, y - 1);
		}
		public Point left(){
			return new Point(x - 1, y);
		}
		public Point right(){
			return new Point(x + 1, y);
		}
		
		public void putInside(int size){
			if (x < 0)
				x = 0;
			if (x >= size)
				x = size - 1;
			if (y < 0)
				y = 0;
			if (y >= size)
				y = size - 1;
		}
		
		public int sqrDist(Point p){
			return (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y);
		}
		public int rectDist(Point p){
			return Math.abs((x - p.x)) + Math.abs((y - p.y));
		}

		public String toString(){
			return String.format("(%d, %d)", x, y);
		}

	}