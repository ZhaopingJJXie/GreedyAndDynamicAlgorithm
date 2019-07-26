
import java.util.*;

public class GreedyDynamicAlgorithms {

	/**
	 * Goal: find the smallest number of red intervals to select, such that every
	 * blue interval overlaps with at least one of the selected red intervals.
	 * Output this number
	 * 
	 * @param red  - the list of red intervals
	 * @param blue - the list blue intervals
	 * @return
	 */
	public static int optimalIntervals(ArrayList<Interval> red, ArrayList<Interval> blue) {
		// TODO

		/**
		 * the idea is sort the two lists by start and recursively check every
		 * neighboring blue interval is intersected if yes, continue, if not choose the
		 * best one from the red list here the best means the start is < the finish of
		 * the first blue interval's finish and the finish is > the second blue's start
		 * and you recursively check every other gaps between blues
		 */
		if (red == null || blue == null) {
			throw new IllegalArgumentException();
		}
		if (red.size() == 0 || blue.size() == 0) {
			return -1;
		}
//		create a new arrayList for the chosen red array
		HashSet<Interval> chosenReds = new HashSet<Interval>();
//		sort the blue and red lists by starting time
		Interval.sortByStartTime(blue);
		Interval.sortByStartTime(red);
//		for (int ii = 0; ii < red.size(); ii++) {
//			System.out.println(red.get(ii).start +  ", " + red.get(ii).finish);
//		}
//		System.out.println("*********************");
//		for (int jj = 0; jj < blue.size(); jj++) {
//			System.out.println(blue.get(jj).start +  ", " + blue.get(jj).finish);
//		}
		/**
		 * divide the red arraylist into several sub Arraylists based on if it can
		 * intersect the blue interval i.e for every blue interval, anyone can intersect
		 * with it are put in a array in and sort it by end time called subred list loop
		 * all the blue intervals check if the last element of the chosen red array list
		 * intersect with this blue interval if intersect, continue, else, chose the
		 * first element from the subred list
		 * 
		 **/
		for (int k = 0; k < blue.size(); k++) {
			ArrayList<Interval> currentRedSub = getSubRedArrayList(blue.get(k), red);
//			System.out.println("************");
//			System.out.println(blue.get(k).start + ", " + blue.get(k).finish);
//			System.out.println("get the subset");
//			for (Interval it : currentRedSub) {
//				System.out.println(it.start + ", " + it.finish);
//			}
//			System.out.println("############")
			if (currentRedSub == null || currentRedSub.size() == 0) {
				return -1;
			} else {
				// check is the resSub contains the element in the chosenReds
				int n = 0;
				while (n < currentRedSub.size()) {
					if (chosenReds.contains(currentRedSub.get(n))) {
						break;
					}
					n++;
				}
				if (n == currentRedSub.size()) {
					chosenReds.add(currentRedSub.get(currentRedSub.size() - 1));
				}
			}
		}
//	for (Interval tred : chosenReds) {
//		System.out.println("&&&&&&&&&&&&&&&");
//		System.out.println(tred.start + ", " + tred.finish);
//	}
		return chosenReds.size();

	}

	public static ArrayList<Interval> getSubRedArrayList(Interval blue, ArrayList<Interval> sortedReds) {
		if (blue == null || sortedReds == null) {
			throw new IllegalArgumentException();
		}
		if (sortedReds.size() == 0) {
			return null;
		}
		ArrayList<Interval> redSub = new ArrayList<Interval>();
		for (int i = 0; i < sortedReds.size(); i++) {
			Interval currentRed = sortedReds.get(i);
//			if the current interval intersect with the blue interval, add it to the redSub array
			if (currentRed.start <= blue.finish) {
				if (Interval.interset(blue, currentRed)) {
					redSub.add(currentRed);
				}
			} else {
				break;
			}
		}
		// sort it by finish time
		Interval.sortByFinishTime(redSub);
		return redSub;
	}

	/**
	 * Goal: find any path of lowest cost from the top-left of the grid (grid[0][0])
	 * to the bottom right of the grid (grid[m-1][n-1]). Output this sequence of
	 * directions
	 * 
	 * @param grid - the 2d grid containing the cost of each location in the grid.
	 * @return
	 */
	public static List<Direction> optimalGridPath(int[][] grid) {
		// TODO
		if (grid == null) {
			throw new IllegalArgumentException();
		}
		if (grid.length == 0) {
			return null;
		}
		return opt(grid, grid.length, grid[0].length);

	}

	public static List<Direction> opt(int[][] grid, int row, int col) {
		ArrayList<Direction> dir = new ArrayList<Direction>();
		if (row == 1 && col == 1) {
			return dir;
		} else if (row == 1) {
			for (int k = 1; k < col; k++) {
				dir.add(Direction.RIGHT);
			}
			return dir;
		} else if (col == 1) {
			for (int m = 1; m < row; m++) {
				dir.add(Direction.DOWN);
			}
			return dir;
		} else {
			if (minCost(grid, row - 1, col) <= minCost(grid, row, col - 1)) {
				List<Direction> goDown = opt(grid, row - 1, col);
				goDown.add(Direction.DOWN);
				return goDown;
			} else {
				List<Direction> goRight = opt(grid, row, col - 1);
				goRight.add(Direction.RIGHT);
				return goRight;
			}
		}
	}

	/**
	 * A simple Direction enum directions can be either DOWN or RIGHT You will
	 * output a list of these in the grid-path problem
	 */
	public static enum Direction {
		DOWN, RIGHT
	}

	public static HashMap<int[], Integer> mapping;

	public static int minCost(int[][] grid, int row, int col) {
		int[] key = { row, col };
		if (row == 1 && col == 1) {
			mapping.put(key, grid[row - 1][col - 1]);
			return grid[row - 1][col - 1];
		} else {
			if (row == 1) {
				int sum = 0;
				for (int i = 0; i < col; i++) {
					sum += grid[row - 1][i];
				}
				mapping.put(key, sum);
				return sum;
			} else if (col == 1) {
				int sum = 0;
				for (int j = 0; j < row; j++) {
					sum += grid[j][col - 1];
				}
				mapping.put(key, sum);
				return sum;
			} else {
				int[] key1 = { row - 1, col };
				int[] key2 = { row, col - 1 };
				int sumOfTwo;
				int min1;
				int min2;

				if (mapping.containsKey(key1)) {
					// map contains key1
					min1 = mapping.get(key1);
				} else {
//					map does not contains key1
					min1 = minCost(grid, row - 1, col);
				}
				if (mapping.containsKey(key2)) {
//						map contains key2
					min2 = mapping.get(key2);
				} else {
//						map does not contains key2
					min2 = minCost(grid, row, col - 1);
				}

				// does not contain key1
				if (min1 <= min2) {
					sumOfTwo = min1 + grid[row - 1][col - 1];
				} else {
					// min1 >= min2
					sumOfTwo = min2 + grid[row - 1][col - 1];
				}

				mapping.put(key, sumOfTwo);
				return sumOfTwo;
			}
		}
	}

	/**
	 * A private Interval class to help with the interval question
	 */
	public static class Interval {

		int start;
		int finish;

		public Interval(int start, int finish) {
			this.start = start;
			this.finish = finish;
		}

		/**
		 * sorts a list of intervals by start time, you are free to use this on the
		 * first question
		 */
		public static void sortByStartTime(ArrayList<Interval> l) {
			Collections.sort(l, new Comparator<Interval>() {
				public int compare(Interval o1, Interval o2) {
					Interval i1 = (Interval) o1;
					Interval i2 = (Interval) o2;
					return i1.start - i2.start;
				}
			});
		}

		/**
		 * sorts a list of intervals by finish time, you are free to use this on the
		 * first question
		 */
		public static void sortByFinishTime(ArrayList<Interval> l) {
			Collections.sort(l, new Comparator<Interval>() {
				public int compare(Interval o1, Interval o2) {
					Interval i1 = (Interval) o1;
					Interval i2 = (Interval) o2;
					return i1.finish - i2.finish;
				}
			});
		}

		/**
		 * this is a function to check if a red interval intersects with a blue interval
		 * 
		 * @param blue
		 * @param red
		 * @return
		 */
		public static boolean interset(Interval blue, Interval red) {
			if (red == null || blue == null) {
				throw new IllegalArgumentException();
			}
			if (red.start > blue.finish) {
				return false;
			} else if (red.finish < blue.start) {
				return false;
			} else {
				return true;
			}
		}
	}
}
