package edu.iastate.cs228.hw5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 
 * Some tests are from lecture notes. If you have questions, please read lecture notes.
 * 
 * @author Yuxiang Zhang
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class _JUnitTest {

	@Test
	public void test_SplayTree_SplayTree() {
		// default constructor
		SplayTree<Integer> tree = new SplayTree<Integer>();
		assertEquals(0, tree.size());
		assertEquals(null, tree.getRoot());

		// root constructor
		tree = new SplayTree<Integer>(0);
		assertEquals(1, tree.size());
		assertEquals(new Integer(0), tree.getRoot());

		// copy constructor
		SplayTree<Integer> copy = new SplayTree<Integer>(tree);
		assertEquals(tree.size(), copy.size());
		assertEquals(tree.getRoot(), copy.getRoot());

		tree.remove(0);
		assertNotEquals(tree.size(), copy.size());
		assertNotEquals(tree.getRoot(), copy.getRoot());

		tree.clear();
		tree.addBST(2);
		tree.addBST(1);
		tree.addBST(3);
		String s = "";
		s += "2\n";
		s += "    1\n";
		s += "    3\n";
		assertEquals(s.trim(), tree.toString().trim());

		copy = new SplayTree<Integer>(tree);
		assertEquals(s.trim(), copy.toString().trim());

		copy.addBST(0);
		String t = "";
		t += "2\n";
		t += "    1\n";
		t += "        0\n";
		t += "        null\n";
		t += "    3\n";
		assertEquals(t.trim(), copy.toString().trim()); // make sure copy is modified
		assertEquals(s.trim(), tree.toString().trim()); // make sure tree is not modified

		tree.clear();
		tree.addBST(3);
		tree.addBST(1);
		tree.addBST(2);
		tree.addBST(4);
		tree.addBST(5);
		s = "";
		s += "3\n";
		s += "    1\n";
		s += "        null\n";
		s += "        2\n";
		s += "    4\n";
		s += "        null\n";
		s += "        5\n";
		assertEquals(s.trim(), tree.toString().trim());

		copy = new SplayTree<Integer>(tree);
		assertEquals(s.trim(), copy.toString().trim());
	}

	@Test
	public void test_SplayTree_add() {
		// example from PPT - Insertion
		SplayTree<Integer> tree = new SplayTree<Integer>();
		tree.addBST(50);
		tree.addBST(30);
		tree.addBST(10);
		tree.addBST(20);
		tree.addBST(15);
		tree.addBST(40);
		tree.addBST(60);
		tree.addBST(90);
		tree.addBST(70);
		tree.addBST(100);
		String s = "";
		s += "50\n";
		s += "    30\n";
		s += "        10\n";
		s += "            null\n";
		s += "            20\n";
		s += "                15\n";
		s += "                null\n";
		s += "        40\n";
		s += "    60\n";
		s += "        null\n";
		s += "        90\n";
		s += "            70\n";
		s += "            100\n";
		assertEquals(s.trim(), tree.toString().trim());

		tree.add(80);
		String t = "";
		t += "80\n";
		t += "    60\n";
		t += "        50\n";
		t += "            30\n";
		t += "                10\n";
		t += "                    null\n";
		t += "                    20\n";
		t += "                        15\n";
		t += "                        null\n";
		t += "                40\n";
		t += "            null\n";
		t += "        70\n";
		t += "    90\n";
		t += "        null\n";
		t += "        100\n";
		assertEquals(t.trim(), tree.toString().trim());
	}

	@Test
	public void test_SplayTree_addBST() {
		// example from https://en.wikipedia.org/wiki/Binary_search_tree
		SplayTree<Integer> tree = new SplayTree<Integer>();
		tree.addBST(8);
		tree.addBST(3);
		tree.addBST(1);
		tree.addBST(6);
		tree.addBST(4);
		tree.addBST(7);
		tree.addBST(10);
		tree.addBST(14);
		tree.addBST(13);
		String s = "";
		s += "8\n";
		s += "    3\n";
		s += "        1\n";
		s += "        6\n";
		s += "            4\n";
		s += "            7\n";
		s += "    10\n";
		s += "        null\n";
		s += "        14\n";
		s += "            13\n";
		s += "            null\n";
		assertEquals(s.trim(), tree.toString().trim());
	}

	@Test
	public void test_SplayTree_clear() {
		SplayTree<Integer> tree = new SplayTree<Integer>();

		tree.clear();
		assertEquals(null, tree.getRoot());
		assertEquals(0, tree.size());

		for (int i = 0; i < 10; i++)
			tree.add(i);
		tree.clear();
		assertEquals(null, tree.getRoot());
		assertEquals(0, tree.size());
	}

	@Test
	public void test_SplayTree_contains() {
		SplayTree<Integer> tree = new SplayTree<Integer>();
		assertEquals(0, tree.size());

		int data = 0, n = 50;
		Random r = new Random(n);
		for (int i = 0; i < n; i++, data = r.nextInt(n)) {
			tree.addBST(data);
			assertTrue(tree.contains(data));
		}
		for (int i = 0; i < n; i++, data = r.nextInt(n)) {
			tree.add(data);
			assertTrue(tree.contains(data));
		}
		for (int i = 0; i < n; i++, data = r.nextInt(n)) {
			tree.remove(data);
			assertFalse(tree.contains(data));
		}

		// test contains for copy
		SplayTree<Integer> copy = new SplayTree<Integer>(tree);
		for (int i = 0; i < n; i++, data = r.nextInt(n)) {
			copy.addBST(data);
			assertTrue(copy.contains(data));
		}
		for (int i = 0; i < n; i++, data = r.nextInt(n)) {
			copy.add(data);
			assertTrue(copy.contains(data));
		}
		for (int i = 0; i < n; i++, data = r.nextInt(n)) {
			copy.remove(data);
			assertFalse(copy.contains(data));
		}
	}

	@Test
	public void test_SplayTree_findElement() {
		// example from PPT - Data Access
		SplayTree<Integer> tree = new SplayTree<Integer>();
		tree.addBST(50);
		tree.addBST(30);
		tree.addBST(10);
		tree.addBST(20);
		tree.addBST(15);
		tree.addBST(40);
		tree.addBST(60);
		tree.addBST(90);
		tree.addBST(70);
		tree.addBST(100);
		String s = "";
		s += "50\n";
		s += "    30\n";
		s += "        10\n";
		s += "            null\n";
		s += "            20\n";
		s += "                15\n";
		s += "                null\n";
		s += "        40\n";
		s += "    60\n";
		s += "        null\n";
		s += "        90\n";
		s += "            70\n";
		s += "            100\n";
		assertEquals(s.trim(), tree.toString().trim());

		assertEquals(null, tree.findElement(80));
		String t = "";
		t += "70\n";
		t += "    50\n";
		t += "        30\n";
		t += "            10\n";
		t += "                null\n";
		t += "                20\n";
		t += "                    15\n";
		t += "                    null\n";
		t += "            40\n";
		t += "        60\n";
		t += "    90\n";
		t += "        null\n";
		t += "        100\n";
		assertEquals(t.trim(), tree.toString().trim());
	}

	@Test
	public void test_SplayTree_getRoot() {
		SplayTree<Integer> tree = new SplayTree<Integer>();
		assertEquals(null, tree.getRoot());

		tree.addBST(0);
		assertEquals(new Integer(0), tree.getRoot());

		tree.addBST(1);
		assertEquals(new Integer(0), tree.getRoot());

		tree.addBST(2);
		assertEquals(new Integer(0), tree.getRoot());

		tree.add(3);
		assertEquals(new Integer(3), tree.getRoot());

		tree.add(4);
		assertEquals(new Integer(4), tree.getRoot());
	}

	@Test
	public void test_SplayTree_iterator() {
		SplayTree<Integer> tree = new SplayTree<Integer>();
		tree.addBST(2);
		tree.addBST(1);
		tree.addBST(3);

		Iterator<Integer> iter = tree.iterator();
		assertEquals(new Integer(1), iter.next());
		assertEquals(new Integer(2), iter.next());
		assertEquals(new Integer(3), iter.next());
		assertFalse(iter.hasNext());

		iter.remove();
		String s = "";
		s += "2\n";
		s += "    1\n";
		s += "    null\n";
		assertEquals(s.trim(), tree.toString().trim());

		tree.clear();
		int size = 1000;
		for (int i = 0; i < size; i++)
			tree.addBST(i);
		Random r = new Random();
		for (int i = 0; i < size; i++)
			tree.splay(r.nextInt(size));
		iter = tree.iterator();
		for (int i = 0; i < size; i++)
			assertEquals(new Integer(i), iter.next());

		// example from PPT - Deletion
		tree.clear();
		tree.addBST(80);
		tree.addBST(60);
		tree.addBST(50);
		tree.addBST(30);
		tree.addBST(10);
		tree.addBST(20);
		tree.addBST(15);
		tree.addBST(40);
		tree.addBST(70);
		tree.addBST(90);
		tree.addBST(100);
		iter = tree.iterator();
		assertEquals(new Integer(10), iter.next());
		assertEquals(new Integer(15), iter.next());
		assertEquals(new Integer(20), iter.next());
		assertEquals(new Integer(30), iter.next());

		iter.remove(); // BST remove
		String t = "";
		t += "80\n";
		t += "    60\n";
		t += "        50\n";
		t += "            40\n";
		t += "                10\n";
		t += "                    null\n";
		t += "                    20\n";
		t += "                        15\n";
		t += "                        null\n";
		t += "                null\n";
		t += "            null\n";
		t += "        70\n";
		t += "    90\n";
		t += "        null\n";
		t += "        100\n";
		assertEquals(t.trim(), tree.toString().trim());

		try {
			iter.remove();
		} catch (Exception e) {
			assertEquals(IllegalStateException.class, e.getClass());
		}
	}

	@Test
	public void test_SplayTree_remove() {
		// example from PPT - Deletion
		SplayTree<Integer> tree = new SplayTree<Integer>();
		tree.addBST(80);
		tree.addBST(60);
		tree.addBST(50);
		tree.addBST(30);
		tree.addBST(10);
		tree.addBST(20);
		tree.addBST(15);
		tree.addBST(40);
		tree.addBST(70);
		tree.addBST(90);
		tree.addBST(100);

		// remove and join
		tree.remove(30);
		String s = "";
		s += "50\n";
		s += "    20\n";
		s += "        10\n";
		s += "            null\n";
		s += "            15\n";
		s += "        40\n";
		s += "    60\n";
		s += "        null\n";
		s += "        80\n";
		s += "            70\n";
		s += "            90\n";
		s += "                null\n";
		s += "                100\n";
		assertEquals(s.trim(), tree.toString().trim());
	}

	@Test
	public void test_SplayTree_size() {
		SplayTree<Integer> tree = new SplayTree<Integer>();
		assertEquals(0, tree.size());

		for (int i = 1; i <= 10; i++) {
			tree.addBST(i);
			assertEquals(i, tree.size());
		}
		for (int i = 11; i <= 20; i++) {
			tree.add(i);
			assertEquals(i, tree.size());
		}
		for (int i = 1; i <= 10; i++) {
			tree.addBST(i);
			assertEquals(20, tree.size());
		}
		for (int i = 11; i <= 20; i++) {
			tree.remove(i);
			assertEquals(30 - i, tree.size());
		}
	}

	@Test
	public void test_SplayTree_splay() {
		SplayTree<Integer> tree = new SplayTree<Integer>();
		for (int i = 14; i >= 2; i -= 2)
			tree.addBST(i);
		for (int i = 1; i <= 15; i += 2)
			tree.addBST(i);
		tree.splay(2);
		String s = "";
		s += "2\n";
		s += "    1\n";
		s += "    12\n";
		s += "        8\n";
		s += "            4\n";
		s += "                3\n";
		s += "                6\n";
		s += "                    5\n";
		s += "                    7\n";
		s += "            10\n";
		s += "                9\n";
		s += "                11\n";
		s += "        14\n";
		s += "            13\n";
		s += "            15\n";
		assertEquals(s.trim(), tree.toString().trim());

		for (int i = 1; i <= 15; i++) {
			tree.splay(i);
			assertEquals(new Integer(i), tree.getRoot());
		}
	}

	@Test
	public void test_SplayTree_toString() {
		SplayTree<Integer> tree = new SplayTree<Integer>();
		String s = "null\n";
		assertEquals(s.trim(), tree.toString().trim());

		tree.addBST(0);
		s = "0\n";
		assertEquals(s.trim(), tree.toString().trim());

		tree.clear();
		tree.addBST(2);
		tree.addBST(1);
		tree.addBST(3);
		s = "";
		s += "2\n";
		s += "    1\n";
		s += "    3\n";
		assertEquals(s.trim(), tree.toString().trim());

		tree.clear();
		tree.addBST(3);
		tree.addBST(1);
		tree.addBST(2);
		tree.addBST(4);
		tree.addBST(5);
		s = "";
		s += "3\n";
		s += "    1\n";
		s += "        null\n";
		s += "        2\n";
		s += "    4\n";
		s += "        null\n";
		s += "        5\n";
		assertEquals(s.trim(), tree.toString().trim());
	}

	@Test
	public void test_SplayTree_zig() {
		SplayTree<Integer> tree = new SplayTree<Integer>();
		tree.addBST(4);
		tree.addBST(2);
		tree.addBST(1);
		tree.addBST(3);
		tree.addBST(5);
		String s = "";
		s += "4\n";
		s += "    2\n";
		s += "        1\n";
		s += "        3\n";
		s += "    5\n";
		assertEquals(s.trim(), tree.toString().trim());

		tree.splay(2); // rotate right
		String t = "";
		t += "2\n";
		t += "    1\n";
		t += "    4\n";
		t += "        3\n";
		t += "        5\n";
		assertEquals(t.trim(), tree.toString().trim());

		tree.splay(4); // rotate left
		assertEquals(s.trim(), tree.toString().trim());
	}

	@Test
	public void test_SplayTree_zigZag() {
		SplayTree<Integer> tree = new SplayTree<Integer>();
		tree.addBST(6);
		tree.addBST(2);
		tree.addBST(1);
		tree.addBST(4);
		tree.addBST(3);
		tree.addBST(5);
		tree.addBST(7);
		String s = "";
		s += "6\n";
		s += "    2\n";
		s += "        1\n";
		s += "        4\n";
		s += "            3\n";
		s += "            5\n";
		s += "    7\n";
		assertEquals(s.trim(), tree.toString().trim());

		tree.splay(4);
		String t = "";
		t += "4\n";
		t += "    2\n";
		t += "        1\n";
		t += "        3\n";
		t += "    6\n";
		t += "        5\n";
		t += "        7\n";
		assertEquals(t.trim(), tree.toString().trim());
	}

	@Test
	public void test_SplayTree_zigZig() {
		SplayTree<Integer> tree = new SplayTree<Integer>();
		tree.addBST(6);
		tree.addBST(4);
		tree.addBST(2);
		tree.addBST(1);
		tree.addBST(3);
		tree.addBST(5);
		tree.addBST(7);
		String s = "";
		s += "6\n";
		s += "    4\n";
		s += "        2\n";
		s += "            1\n";
		s += "            3\n";
		s += "        5\n";
		s += "    7\n";
		assertEquals(s.trim(), tree.toString().trim());

		tree.splay(2);
		String t = "";
		t += "2\n";
		t += "    1\n";
		t += "    4\n";
		t += "        3\n";
		t += "        6\n";
		t += "            5\n";
		t += "            7\n";
		assertEquals(t.trim(), tree.toString().trim());

		tree.splay(6);
		assertEquals(s.trim(), tree.toString().trim());
	}

	@Test
	public void test_VideoStore_VideoStore() throws Exception {
		// example from PDF
		PrintWriter pw = new PrintWriter("videoList1.txt");
		pw.println("The Godfather");
		pw.println("Forrest Gump");
		pw.println("Brokeback Mountain (1)");
		pw.println("A Streetcar Named Desire (1)");
		pw.println("Slumdog Millionaire (5)");
		pw.println("Taxi Driver (1)");
		pw.println("Psycho (1)");
		pw.println("Singin' in the Rain (2)");
		pw.close();
		pw = new PrintWriter("videoList2.txt");
		pw.println("The Godfather (1)");
		pw.println("Forrest Gump (1)");
		pw.println("Slumdog Millionaire (4)");
		pw.close();
		pw = new PrintWriter("videoList3.txt");
		pw.println("Forrest Gump");
		pw.println("Slumdog Millionaire (1)");
		pw.close();

		VideoStore vs = new VideoStore("videoList1.txt");
		vs.videoRent("The Godfather", 1);
		try {
			vs.bulkRent("videoList2.txt");
		} catch (Exception e) {
			assertEquals(AllCopiesRentedOutException.class, e.getClass());
			assertEquals("Film The Godfather has been rented out", e.getMessage());
		}
		vs.videoRent("Brokeback Mountain", 1);
		vs.videoReturn("Slumdog Millionaire", 2);
		try {
			vs.videoRent("The Silence of the Lambs", 1);
		} catch (Exception e) {
			assertEquals(FilmNotInInventoryException.class, e.getClass());
			assertEquals("Film The Silence of the Lambs is not in inventory", e.getMessage());
		}
		vs.videoRent("Singin' in the Rain", 2);
		vs.bulkReturn("videoList3.txt");
		String s = "";
		s += "Rented films:\n";
		s += "\n"; // empty line
		s += "Brokeback Mountain (1)\n";
		s += "Singin' in the Rain (2)\n";
		s += "Slumdog Millionaire (1)\n";
		s += "The Godfather (1)\n";
		s += "\n"; // empty line
		s += "Films remaining in inventory:\n";
		s += "\n"; // empty line
		s += "A Streetcar Named Desire (1)\n";
		s += "Forrest Gump (1)\n";
		s += "Psycho (1)\n";
		s += "Slumdog Millionaire (4)\n";
		s += "Taxi Driver (1)\n";
		assertEquals(s.trim(), vs.transactionsSummary().trim());
	}

	@Test
	public void test_VideoStore_addVideo() {
		VideoStore vs = new VideoStore();
		for (int p = 1; p <= 5; p++) {
			vs.addVideo("Project " + p);
			assertEquals("Project " + p, vs.inventory.getRoot().getFilm());
		}
		assertEquals(1, vs.findVideo("Project 3").getNumCopies());

		vs.addVideo("Project 2");
		assertEquals(2, vs.findVideo("Project 2").getNumCopies());

		vs.addVideo("Project 1", 2);
		assertEquals(3, vs.findVideo("Project 1").getNumCopies());
	}

	@Test
	public void test_VideoStore_available() throws Exception {
		VideoStore vs = new VideoStore();
		assertFalse(vs.available("StarCraft"));

		vs.addVideo("StarCraft");
		assertTrue(vs.available("StarCraft"));

		vs.videoRent("StarCraft", 2);
		assertFalse(vs.available("StarCraft"));
	}

	@Test
	public void test_VideoStore_bulkRent() throws Exception {
		// example from Blackboard
		// -> Discussion Board
		// -> Forum: Project 5 Clarifications
		// -> Thread: Order of exceptions
		PrintWriter pw = new PrintWriter("bulkRent.txt");
		pw.println("The Silence of the Lambs");
		pw.println("Forrest Gump (-2)");
		pw.println("The Godfather");
		pw.close();

		VideoStore vs = new VideoStore();
		vs.addVideo("The Godfather");
		vs.videoRent("The Godfather", 1);

		String s = "";
		s += "Film The Silence of the Lambs is not in inventory\n";
		s += "Film Forrest Gump has an invalid request\n";
		s += "Film The Godfather has been rented out\n";
		
		try {
			vs.bulkRent("bulkRent.txt");
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
			assertEquals(s.trim(), e.getMessage().trim());
		}
	}

	@Test
	public void test_VideoStore_bulkReturn() throws Exception {
		// example from Blackboard
		// -> Discussion Board
		// -> Forum: Project 5 Clarifications
		// -> Thread: Order of exceptions
		PrintWriter pw = new PrintWriter("bulkReturn.txt");
		pw.println("The Silence of the Lambs");
		pw.println("Forrest Gump (-2)");
		pw.println("The Godfather");
		pw.close();

		VideoStore vs = new VideoStore();
		vs.addVideo("The Godfather");
		vs.videoRent("The Godfather", 1);

		String s = "";
		s += "Film The Silence of the Lambs is not in inventory\n";
		s += "Film Forrest Gump has an invalid request\n";

		try {
			vs.bulkReturn("bulkReturn.txt");
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
			assertEquals(s.trim(), e.getMessage().trim());
		}
	}

	@Test
	public void test_VideoStore_inventoryList() {
		VideoStore vs = new VideoStore();

		vs.addVideo("F", 6);
		vs.addVideo("E", 5);
		vs.addVideo("D", 4);
		vs.addVideo("A", 1);
		vs.addVideo("B", 2);
		vs.addVideo("C", 3);

		String s = "Films in inventory:\n\n";
		s += "A (1)\n";
		s += "B (2)\n";
		s += "C (3)\n";
		s += "D (4)\n";
		s += "E (5)\n";
		s += "F (6)\n";
		assertEquals(s.trim(), vs.inventoryList().trim());
	}

	@Test
	public void test_VideoStore_parseFilmName() {
		String line = "Hot Dog";
		assertEquals("Hot Dog", VideoStore.parseFilmName(line));

		line = "Hot Dog 2";
		assertEquals("Hot Dog 2", VideoStore.parseFilmName(line));

		line = "Hot Dog (5)";
		assertEquals("Hot Dog", VideoStore.parseFilmName(line));

		line = "Hot (10) Dog";
		assertEquals("Hot (10) Dog", VideoStore.parseFilmName(line));

		line = "World of Parentheses (((((5)))))";
		assertEquals("World of Parentheses (((((5)))))", VideoStore.parseFilmName(line));

		line = "World of Parentheses (1) (2) (3) (4) (5) (100)";
		assertEquals("World of Parentheses (1) (2) (3) (4) (5)", VideoStore.parseFilmName(line));

		line = "(1) (World) (2) (of) (3) (Parentheses) () )( () (100)";
		assertEquals("(1) (World) (2) (of) (3) (Parentheses) () )( ()", VideoStore.parseFilmName(line));

		line = "Forrest Gump (-2)"; // can parse -2 even though it is illegal
		assertEquals("Forrest Gump", VideoStore.parseFilmName(line));

		int r = new Random().nextInt();
		line = "Random (" + r + ")";
		assertEquals("Random", VideoStore.parseFilmName(line));
	}

	@Test
	public void test_VideoStore_parseNumCopies() {
		String line = "Hot Dog";
		assertEquals(1, VideoStore.parseNumCopies(line));

		line = "Hot Dog 2";
		assertEquals(1, VideoStore.parseNumCopies(line));

		line = "Hot Dog (5)";
		assertEquals(5, VideoStore.parseNumCopies(line));

		line = "Hot (10) Dog";
		assertEquals(1, VideoStore.parseNumCopies(line));

		line = "World of Parentheses (((((5)))))";
		assertEquals(1, VideoStore.parseNumCopies(line));

		line = "World of Parentheses (1) (2) (3) (4) (5) (100)";
		assertEquals(100, VideoStore.parseNumCopies(line));

		line = "(1) (World) (2) (of) (3) (Parentheses) () )( () (100)";
		assertEquals(100, VideoStore.parseNumCopies(line));

		line = "Forrest Gump (-2)"; // can parse -2 even though it is illegal
		assertEquals(-2, VideoStore.parseNumCopies(line));

		int r = new Random().nextInt();
		line = "Random (" + r + ")";
		assertEquals(r, VideoStore.parseNumCopies(line));
	}

	@Test
	public void test_VideoStore_videoRent() throws Exception {
		VideoStore vs = new VideoStore();

		vs.addVideo("StarCraft");
		vs.videoRent("StarCraft", 2);

		try {
			vs.videoRent("StarCraft", 2);
		} catch (Exception e) {
			assertEquals(AllCopiesRentedOutException.class, e.getClass());
			assertEquals("Film StarCraft has been rented out", e.getMessage());
		}

		try {
			vs.videoRent("Diablo", 3);
		} catch (Exception e) {
			assertEquals(FilmNotInInventoryException.class, e.getClass());
			assertEquals("Film Diablo is not in inventory", e.getMessage());
		}
	}

	@Test
	public void test_VideoStore_videoReturn() throws Exception {
		VideoStore vs = new VideoStore();

		vs.addVideo("StarCraft");
		vs.videoRent("StarCraft", 2);
		vs.videoReturn("StarCraft", 2);

		Video v = vs.findVideo("StarCraft");
		assertEquals(1, v.getNumCopies());
		assertEquals(0, v.getNumRentedCopies());
		assertEquals(1, v.getNumAvailableCopies());
	}

	@Test
	public void test_Video_Video() {
		Video v = new Video("StarCraft");
		assertEquals(1, v.getNumCopies());
		assertEquals(0, v.getNumRentedCopies());
		assertEquals(1, v.getNumAvailableCopies());

		v = new Video("Legacy of the Void", 5);
		assertEquals(5, v.getNumCopies());
		assertEquals(0, v.getNumRentedCopies());
		assertEquals(5, v.getNumAvailableCopies());
	}

	@Test
	public void test_Video_compareTo() {
		assertTrue(0 < new Video("Wings of Liberty").compareTo(new Video("Heart of the Swarm")));
		assertTrue(0 > new Video("Heart of the Swarm").compareTo(new Video("Legacy of the Void")));
		assertTrue(0 == new Video("Legacy of the Void").compareTo(new Video("Legacy of the Void")));
		assertTrue(0 == new Video("Legacy of the Void", 2).compareTo(new Video("Legacy of the Void", 5)));
	}

	@Test
	public void test_Video_rentCopies() throws Exception {
		Video v = new Video("Legacy of the Void", 5);
		assertEquals(5, v.getNumCopies());
		assertEquals(0, v.getNumRentedCopies());
		assertEquals(5, v.getNumAvailableCopies());

		v.rentCopies(4);
		assertEquals(5, v.getNumCopies());
		assertEquals(4, v.getNumRentedCopies());
		assertEquals(1, v.getNumAvailableCopies());

		v.rentCopies(2);
		assertEquals(5, v.getNumCopies());
		assertEquals(5, v.getNumRentedCopies());
		assertEquals(0, v.getNumAvailableCopies());

		try {
			v.rentCopies(0);
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

	@Test
	public void test_Video_returnCopies() {
		Video v = new Video("Legacy of the Void", 5);
		assertEquals(5, v.getNumCopies());
		assertEquals(0, v.getNumRentedCopies());
		assertEquals(5, v.getNumAvailableCopies());

		try {
			v.rentCopies(10);
		} catch (Exception e) {
			e.printStackTrace();
		}

		v.returnCopies(1);
		assertEquals(5, v.getNumCopies());
		assertEquals(4, v.getNumRentedCopies());
		assertEquals(1, v.getNumAvailableCopies());

		v.returnCopies(10);
		assertEquals(5, v.getNumCopies());
		assertEquals(0, v.getNumRentedCopies());
		assertEquals(5, v.getNumAvailableCopies());

		try {
			v.returnCopies(0);
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

	@Test
	public void test_Video_toString() {
		Video v = new Video("StarCraft II: Legacy of the Void", 5);
		assertEquals("StarCraft II: Legacy of the Void (5:0)", v.toString());
		try {
			v.rentCopies(4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("StarCraft II: Legacy of the Void (5:4)", v.toString());
		try {
			v.rentCopies(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("StarCraft II: Legacy of the Void (5:5)", v.toString());
	}

}
