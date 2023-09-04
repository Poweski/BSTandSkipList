import Dictionaries.*;

import java.util.Comparator;
import java.util.Random;

public class Main {

	public static void main(String[] args) {

		Comparator<Integer> intComparator = Integer::compareTo;

		BSTDictionary<Integer,Integer> bst = new BSTDictionary<>(intComparator);
		RBDictionary<Integer,Integer> rbt = new RBDictionary<>();
		SkipDictionary<Integer,Integer> skip0 = new SkipDictionary<>(0.0, intComparator);
		SkipDictionary<Integer,Integer> skip025 = new SkipDictionary<>(0.25, intComparator);
		SkipDictionary<Integer,Integer> skip05 = new SkipDictionary<>(0.5, intComparator);
		SkipDictionary<Integer,Integer> skip08 = new SkipDictionary<>(0.8, intComparator);

		int[] ranges = new int[]{ 10, 50, 100, 200, 500, 800, 1000, 1500, 3000 };

		for (int range : ranges)
			calculateTime(bst, range);
//		for (int range : ranges)
//			calculateTime(rbt, range);
//		for (int range : ranges)
//			calculateTime(skip0, range);
//		for (int range : ranges)
//			calculateTime(skip025, range);
//		for (int range : ranges)
//			calculateTime(skip05, range);
//		for (int range : ranges)
//			calculateTime(skip08, range);
	}

	public static void calculateTime(IDictionary<Integer,Integer> dictionary, int range) {
		System.out.println("\n" + range);

		long time1, time2;
		Random generator = new Random();

		time1 = System.nanoTime();
		for (int i = 0; i < range; i++)
			dictionary.insert(generator.nextInt(10000), i);
		time2 = System.nanoTime();
		System.out.println("Insert: " + (time2-time1)/1000);

		time1 = System.nanoTime();
		for (int i = 0; i < range; i++)
			dictionary.get(generator.nextInt(1000));
		time2 = System.nanoTime();
		System.out.println("Get: " + (time2-time1)/1000);

		time1 = System.nanoTime();
		for (int i = 0; i < range; i++)
			dictionary.remove(generator.nextInt(1000));
		time2 = System.nanoTime();
		System.out.println("Remove: " + (time2-time1)/1000);

		dictionary.clear();
	}
}