import static org.junit.Assert.*;
import java.util.*;
import org.junit.Before;
import org.junit.Test;

//import GreedyDynamicAlgorithms.Interval;

public class GreedyDynamicAlgorithmsTest {
	GreedyDynamicAlgorithms gda = new GreedyDynamicAlgorithms();
	GreedyDynamicAlgorithms.Interval red1, red2, red3, red4, red5, red6, red7;
	GreedyDynamicAlgorithms.Interval blue1, blue2, blue3, blue4;
	ArrayList<GreedyDynamicAlgorithms.Interval> red = new ArrayList<GreedyDynamicAlgorithms.Interval>();
	ArrayList<GreedyDynamicAlgorithms.Interval> blue = new ArrayList<GreedyDynamicAlgorithms.Interval>();
	int[][] grid = {{5, 1, 1}, {2,4,7},
			{2, 4, 5}, {5, 6, 3}
			};
	@Before
	public void setUp() throws Exception {
		red1 = new GreedyDynamicAlgorithms.Interval(0,4);
		red2 = new GreedyDynamicAlgorithms.Interval(9,10);
		red3 = new GreedyDynamicAlgorithms.Interval(4,8);
		red4 = new GreedyDynamicAlgorithms.Interval(2,5);
		red5 = new GreedyDynamicAlgorithms.Interval(11,12);
		red6 = new GreedyDynamicAlgorithms.Interval(9,11);
		red7 = new GreedyDynamicAlgorithms.Interval(10,12);
		blue1 = new GreedyDynamicAlgorithms.Interval(0,2);
		blue2 = new GreedyDynamicAlgorithms.Interval(7,10);
		blue3 = new GreedyDynamicAlgorithms.Interval(5,5);
		blue4 = new GreedyDynamicAlgorithms.Interval(11,13);
		red.add(red1);
		red.add(red2);
		red.add(red3);
		red.add(red4);
		red.add(red5);
		red.add(red6);
		red.add(red7);
		blue.add(blue1);
		blue.add(blue2);
		blue.add(blue3);
		blue.add(blue4);
		
	}

	@Test
	public void testOptimalIntervals() {
//		fail("Not yet implemented");
		int num = GreedyDynamicAlgorithms.optimalIntervals(red, blue);
		System.out.println("red number is: "+ num);
	}

	@Test
	public void testOptimalGridPath() {
		List<GreedyDynamicAlgorithms.Direction> l = GreedyDynamicAlgorithms.optimalGridPath(grid);
		System.out.println(l.size());
		for (GreedyDynamicAlgorithms.Direction dr : l) {
			System.out.println(dr);
		}
	}

	
	@Test
	public void testInterset() {
		boolean in = GreedyDynamicAlgorithms.Interval.interset(blue1, red4);
		assertEquals(in, true);
	}
	
	@Test
	public void testsortByFinishTime() {
		GreedyDynamicAlgorithms.Interval.sortByFinishTime(blue);
		for (int i = 0; i <blue.size(); i++) {
//			System.out.println(blue.get(i).start + ", " + blue.get(i).finish);
		}
	}
	
//	@Test
//	public void testMinCost() {
//		System.out.println("mini cost: " +GreedyDynamicAlgorithms.minCost(grid, 4, 3));
//	}
	
}
