package edu.iastate.cs228.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 
 * @author Zijie Deng
 *
 */

public class VideoStore {
	protected SplayTree<Video> inventory; // all the videos at the store

	// ------------
	// Constructors
	// ------------

	/**
	 * Default constructor sets inventory to an empty tree.
	 */
	VideoStore() {
		inventory = new SplayTree<Video>();
	}

	/**
	 * Constructor accepts a video file to create its inventory. Refer to
	 * Section 3.2 of the project description for details regarding the format
	 * of a video file.
	 * 
	 * The construtor works in two steps:
	 * 
	 * 1. Calls the default constructor. 2. Has the splay tree inventory call
	 * its method addBST() to add videos to the tree.
	 * 
	 * @param videoFile
	 *            no format checking on the file
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 *             if the number of copies of any film in videoFile is <= 0
	 */
	VideoStore(String videoFile) throws FileNotFoundException, IllegalArgumentException {
		this();
		setUpInventory(videoFile);
	}

	/**
	 * Accepts a video file to initialize the splay tree inventory. To be
	 * efficient, add videos to the inventory by calling the addBST() method,
	 * which does not splay.
	 * 
	 * Refer to Section 3.2 for the format of video file.
	 * 
	 * @param videoFile
	 *            correctly formated if exists
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 *             if the number of copies of any film in videoFile is <= 0
	 */
	public void setUpInventory(String videoFile) throws FileNotFoundException, IllegalArgumentException {
		inventory.clear();
		bulkImport(videoFile);
	}

	// ------------------
	// Inventory Addition
	// ------------------

	/**
	 * Find a Video object by film title.
	 * 
	 * @param film
	 * @return
	 */
	public Video findVideo(String film) {
		return inventory.findElement(new Video(film));
	}

	/**
	 * Updates the splay tree inventory by adding a given number of video copies
	 * of the film. (Splaying is justified as new videos are more likely to be
	 * rented.)
	 * 
	 * Calls the add() method of SplayTree to add the video object. If true is
	 * returned, the film was not on the inventory before, and has been added.
	 * If false is returned, the film is already on the inventory. The root of
	 * the splay tree must store the corresponding Video object for the film.
	 * Calls findElement() of the SplayTree class to get this Video object,
	 * which then calls getNumCopies() and addNumCopies() of the Video class to
	 * increase the number of copies of the corresponding film by n
	 * 
	 * @param film
	 *            title of the film
	 * @param n
	 *            number of video copies
	 * @throws IllegalArgumentException
	 *             if n <= 0
	 */
	public void addVideo(String film, int n) throws IllegalArgumentException {
		if (n <= 0)
			throw new IllegalArgumentException();
		Video newV = new Video(film, n);
		boolean added = inventory.add(newV);
		if (added == false) {
			Video v = inventory.findElement(newV);
			v.addNumCopies(n);
		}
	}

	/**
	 * Add one video copy of the film.
	 * 
	 * @param film
	 *            title of the film
	 */
	public void addVideo(String film) {
		Video newV = new Video(film);
		boolean added = inventory.add(newV);
		if (added == false) {
			Video v = inventory.findElement(newV);
			v.addNumCopies(1);
		}
	}

	/**
	 * Update the splay trees inventory.
	 * 
	 * The videoFile format is given in Section 3.2 of the project description.
	 * 
	 * @param videoFile
	 *            correctly formated if exists
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 *             if the number of copies of any film in videoFile is <= 0
	 */
	public void bulkImport(String videoFile) throws FileNotFoundException, IllegalArgumentException {
		File file = new File(videoFile);
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String filmName = parseFilmName(s);
			int filmNum = parseNumCopies(s);
			if (filmNum <= 0)
				throw new IllegalArgumentException();
			else
				inventory.add(new Video(filmName, filmNum));

		}
		scan.close();
		// if (v == null)
		// throw new FileNotFoundException();
		// if (v.getNumCopies() <= 0)
		// throw new IllegalArgumentException();
	}

	// ----------------------------
	// Video Query, Rental & Return
	// ----------------------------

	/**
	 * Search the splay tree inventory to determine if a video is available.
	 * 
	 * @param film
	 * @return true if available
	 */
	public boolean available(String film) {
		Video v = inventory.findElement(new Video(film));
		if (v != null && v.getNumAvailableCopies() > 0)
			return true;
		return false;
	}

	/**
	 * Update inventory.
	 * 
	 * Search if the film is in inventory by calling findElement(new Video(film,
	 * 1)).
	 * 
	 * If the film is not in inventory, prints the message "Film <film> is not
	 * in inventory", where <film> shall be replaced with the string that is the
	 * value of the parameter film. If the film is in inventory with no copy
	 * left, prints the message "Film <film> has been rented out".
	 * 
	 * If there is at least one available copy but n is greater than the number
	 * of such copies, rent all available copies. In this case, no
	 * AllCopiesRentedOutException is thrown.
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException
	 *             if n <= 0
	 * @throws FilmNotInInventoryException
	 *             if film is not in the inventory
	 * @throws AllCopiesRentedOutException
	 *             if there is zero available copy for the film.
	 */
	public void videoRent(String film, int n)
			throws IllegalArgumentException, FilmNotInInventoryException, AllCopiesRentedOutException {
		if (n <= 0)
			throw new IllegalArgumentException();
		Video v = inventory.findElement(new Video(film, n));
		if (v == null)
			throw new FilmNotInInventoryException("Film " + film + " is not in inventory");
		else if (v.getNumAvailableCopies() == 0)
			throw new AllCopiesRentedOutException("Film " + film + " has been rented out");
		else if (v.getNumAvailableCopies() > 0 && n > v.getNumAvailableCopies()) {
			n = v.getNumAvailableCopies();
			v.rentCopies(n);
		} else {
			v.rentCopies(n);
		}
	}

	/**
	 * Update inventory.
	 * 
	 * 1. Calls videoRent() repeatedly for every video listed in the file. 2.
	 * For each requested video, do the following: a) If it is not in inventory
	 * or is rented out, an exception will be thrown from rent(). Based on the
	 * exception, prints out the following message:
	 * "Film <film> is not in inventory" or "Film <film> has been rented out."
	 * In the message, <film> shall be replaced with the name of the video. b)
	 * Otherwise, update the video record in the inventory.
	 * 
	 * @param videoFile
	 *            correctly formatted if exists
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 *             if the number of copies of any film is <= 0
	 * @throws FilmNotInInventoryException
	 *             if any film from the videoFile is not in the inventory
	 * @throws AllCopiesRentedOutException
	 *             if there is zero available copy for some film in videoFile
	 */
	public void bulkRent(String videoFile) throws FileNotFoundException, IllegalArgumentException,
			FilmNotInInventoryException, AllCopiesRentedOutException {
		boolean[] bs = new boolean[3];
		File file = new File(videoFile);
		Scanner scan = new Scanner(file);
		String msg = "";
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String filmName = parseFilmName(s);
			int filmNum = parseNumCopies(s);
			if (filmNum <= 0) {
				bs[0] = true;
				msg += "Film " + filmName + " has an invalid request\n";
				continue;
			}
			Video v = inventory.findElement(new Video(filmName, filmNum));
			if (v == null) {
				bs[1] = true;
				msg += "Film " + filmName + " is not in inventory\n";
			} else if (v.getNumAvailableCopies() == 0) {
				bs[2] = true;
				msg += "Film " + filmName + " has been rented out\n";
			} else
				v.rentCopies(filmNum);
		}
		scan.close();
		msg = msg.trim();
		if (bs[0])
			throw new IllegalArgumentException(msg);
		if (bs[1])
			throw new FilmNotInInventoryException(msg);
		if (bs[2])
			throw new AllCopiesRentedOutException(msg);
		scan.close();
	}

	/**
	 * Update inventory.
	 * 
	 * If n exceeds the number of rented video copies, accepts up to that number
	 * of rented copies while ignoring the extra copies.
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException
	 *             if n <= 0
	 * @throws FilmNotInInventoryException
	 *             if film is not in the inventory
	 */
	public void videoReturn(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException {
		if (n <= 0)
			throw new IllegalArgumentException();
		Video v = inventory.findElement(new Video(film, n));
		if (v == null)
			throw new FilmNotInInventoryException();
		if (n > v.getNumRentedCopies())
			n = v.getNumRentedCopies();
		v.returnCopies(n);
	}

	/**
	 * Update inventory.
	 * 
	 * Handles excessive returned copies of a film in the same way as
	 * videoReturn() does.
	 * 
	 * @param videoFile
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 *             if the number of return copies of any film is <= 0
	 * @throws FilmNotInInventoryException
	 *             if a film from videoFile is not in inventory
	 */
	public void bulkReturn(String videoFile)
			throws FileNotFoundException, IllegalArgumentException, FilmNotInInventoryException {
		File file = new File(videoFile);
		Scanner scan = new Scanner(file);
		boolean[] bs = new boolean[2];
		String msg = "";
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String filmName = parseFilmName(s);
			int filmNum = parseNumCopies(s);
			if (filmNum <= 0) {
				bs[0] = true;
				msg += "Film " + filmName + " has an invalid request\n";
				continue;
			}
			Video v = inventory.findElement(new Video(filmName, filmNum));
			if (v == null) {
				bs[1] = true;
				msg += "Film " + filmName + " is not in inventory\n";
			} else {
				videoReturn(filmName, filmNum);
			}
		}
		scan.close();
		msg = msg.trim();
		if (bs[0])
			throw new IllegalArgumentException(msg);
		if (bs[1])
			throw new FilmNotInInventoryException(msg);
		scan.close();
	}

	// ------------------------
	// Methods with No Splaying
	// ------------------------

	/**
	 * Performs inorder traveral on the splay tree inventory to list all the
	 * videos by film title, whether rented or not. Below is a sample string if
	 * printed out:
	 * 
	 * 
	 * Films in inventory:
	 * 
	 * A Streetcar Named Desire (1) Brokeback Mountain (1) Forrest Gump (1)
	 * Psycho (1) Singin' in the Rain (2) Slumdog Millionaire (5) Taxi Driver
	 * (1) The Godfather (1)
	 * 
	 * 
	 * @return
	 */
	public String inventoryList() {
		String s = "Films in inventory:\n\n";
		for (Video v : inventory) {
			int n = v.getNumCopies();
			if (n > 0)
				s += v.getFilm() + " (" + n + ")\n";
		}
		return s;
	}

	/**
	 * Calls rentedVideosList() and unrentedVideosList() sequentially. For the
	 * string format, see Transaction 5 in the sample simulation in Section 4 of
	 * the project description.
	 * 
	 * @return
	 */
	public String transactionsSummary() {
		return rentedVideosList() + "\n" + unrentedVideosList();
	}

	/**
	 * Performs inorder traversal on the splay tree inventory. Use a splay tree
	 * iterator.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * 
	 * Rented films:
	 * 
	 * Brokeback Mountain (1) Singin' in the Rain (2) Slumdog Millionaire (1)
	 * The Godfather (1)
	 * 
	 * 
	 * @return
	 */
	private String rentedVideosList() {
		String s = "Rented films:\n\n";
		for (Video v : inventory) {
			int n = v.getNumRentedCopies();
			if (n > 0)
				s += v.getFilm() + " (" + n + ")\n";
		}
		return s;
	}

	/**
	 * Performs inorder traversal on the splay tree inventory. Use a splay tree
	 * iterator. Prints only the films that have unrented copies.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * 
	 * Films remaining in inventory:
	 * 
	 * A Streetcar Named Desire (1) Forrest Gump (1) Psycho (1) Slumdog
	 * Millionaire (4) Taxi Driver (1)
	 * 
	 * 
	 * @return
	 */
	private String unrentedVideosList() {
		String s = "Films remaining in inventory:\n\n";
		for (Video v : inventory) {
			int n = v.getNumAvailableCopies();
			if (n > 0)
				s += v.getFilm() + " (" + n + ")\n";
		}
		return s;
	}

	/**
	 * Parse the film name from an input line.
	 * 
	 * @param line
	 * @return
	 */
	public static String parseFilmName(String line) {
		String[] string = line.split(" ");
		String s = string[string.length - 1];
		boolean hasNum = false;
		if (s.startsWith("(") && s.endsWith(")")) {
			String num = s.substring(1, s.length() - 1);
			try {
				Integer.parseInt(num);
				hasNum = true;
			} catch (NumberFormatException e) {
				hasNum = false;
			}
		}
		if (hasNum) {
			int index = line.indexOf(s);
			return line.substring(0, index).trim();
		} else
			return line.trim();
	}

	/**
	 * Parse the number of copies from an input line.
	 * 
	 * @param line
	 * @return
	 */
	public static int parseNumCopies(String line) {
		String[] string = line.split(" ");
		String s = string[string.length - 1];
		boolean hasNum = false;
		int number = 1;
		if (s.startsWith("(") && s.endsWith(")")) {
			String num = s.substring(1, s.length() - 1);
			try {
				number = Integer.parseInt(num);
				hasNum = true;
			} catch (NumberFormatException e) {
				hasNum = false;
			}
		}
		return number;
	}
}
