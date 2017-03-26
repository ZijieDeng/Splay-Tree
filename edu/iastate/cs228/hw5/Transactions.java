package edu.iastate.cs228.hw5;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  
 * @author Zijie Deng
 *
 */

/**
 * 
 * The Transactions class simulates video transactions at a video store.
 *
 */
public class Transactions {

	/**
	 * The main method generates a simulation of rental and return activities.
	 * 
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO
		//
		// 1. Construct a VideoStore object.
		// 2. Simulate transactions as in the example given in Section 4 of the
		// the project description.
		VideoStore v = new VideoStore("videoList1.txt");
		System.out.print(
				"Transactions at a Video Store\nkeys: 1 (rent)\t\t2 (bulk rent)\n      3 (return)\t4 (bulk return)\n      5 (summary)\t6 (exit)\n");
		Scanner scan = new Scanner(System.in);
		while (true) {
			try {
				System.out.print("\nTransaction: ");
				int i = Integer.parseInt(scan.nextLine());
				if (i == 1) {
					System.out.print("Film to rent: ");
					String s = scan.nextLine();
					v.videoRent(VideoStore.parseFilmName(s), VideoStore.parseNumCopies(s));
				} else if (i == 2) {
					System.out.print("Video file (rent): ");
					String s = scan.nextLine();
					v.bulkRent(s);
				} else if (i == 3) {
					System.out.print("Film to return: ");
					String s = scan.nextLine();
					v.videoReturn(VideoStore.parseFilmName(s), VideoStore.parseNumCopies(s));
				} else if (i == 4) {
					System.out.print("Video file (return): ");
					String s = scan.nextLine();
					v.bulkReturn(s);
				} else if (i == 5) {
					System.out.print("\n"+v.transactionsSummary());
				} else if (i == 6) {
					scan.close();
					break;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
	}
}
