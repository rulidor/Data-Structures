package Assignment2;

/**
 * This is a testing framework. Use it extensively to verify that your code is working
 * properly.
 */
public class Tester {

	private static boolean testPassed = true;
	private static int testNum = 0;
	
	/**
	 * This entry function will test all classes created in this assignment.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
	
		// Each function here should test a different class.

		// CharLinkedList
		testCharLinkedList();
		
		// CharLinkedListNode
		testCharLinkedListNode();

		// SuffixTreeNode
		testSuffixTreeNode();
		
		// SuffixTree
		testSuffixTree();

		// ReverseSuffixTree
		testReverseSuffixTree();
		
		/* TODO - continue writing a function for each class */

		
		// Notifying the user that the code have passed all tests. 
		if (testPassed) {
			System.out.println("All " + testNum + " tests passed!");
		}
	}

	/**
	 * This utility function will count the number of times it was invoked. 
	 * In addition, if a test fails the function will print the error message.  
	 * @param exp The actual test condition
	 * @param msg An error message, will be printed to the screen in case the test fails.
	 */
	private static void test(boolean exp, String msg) {
		testNum++;
		
		if (!exp) {
			testPassed = false;
			System.out.println("Test " + testNum + " failed: "  + msg);
		}
	}

	/**
	 * Checks the CharLinkedList class.
	 */
	private static void testCharLinkedList(){
		CharLinkedList list = new CharLinkedListImpl();
		test(list.size() == 0, "The size of the list should be 0");
		list.add('a');
		test(list.size() == 1, "The size of the list should be 1");
		test(list.firstChar() == 'a', "The first char should be 'a'");
		CharLinkedList list2 = new CharLinkedListImpl();
		list.append(list2);
		test(list.getLast().getChar()=='a', "The last char should be 'a'");
		list2.add('b');
		list.append(list2);
		test(list.getLast().getChar()=='b', "The last char should be 'b'");
	}
	
	private static void testCharLinkedListNode()
	{
		//constructors, getChar, getNext
		CharLinkedListNodeImpl p=new CharLinkedListNodeImpl('c');
		test(p.getChar()=='c', "char in p should be 'c'");
		test(p.getNext()==null, "p.next should be null");
		ICharLinkedListNode p2=new CharLinkedListNodeImpl('b',p);
		test(p2.getNext()==p,"p2.next should be p");
		test(p2.getChar()=='b', "char in p should be 'b'");
		
		//setNext
		CharLinkedListNodeImpl p3=new CharLinkedListNodeImpl('a');
		p3.setNext(p2);
		test(p3.getNext()==p2, "p3.next should be p2");
		
		//setData
		p3.setData('z');
		test(p3.getChar()=='z', "p3 char should be 'z'");
	}

	/**
	 * Checks the SuffixTreeNode class.
	 */
	private static void testSuffixTreeNode() {
		// test empty root
		SuffixTreeNode node = new SuffixTreeNodeImpl();
		test(node.getTotalWordLength() == 0, "node word length should be 0");
		test(node.getNumOfChildren() == 0, "node num of children should be 0");
		test(node.getChars() == null, "node chars should be null");
		test(node.getParent() == null, "node parent should be null");

		// test search, binary search, shiftChildren and addChild
		SuffixTreeNode child1 = new SuffixTreeNodeImpl(CharLinkedList.from("abc"), node);
		SuffixTreeNode child2 = new SuffixTreeNodeImpl(CharLinkedList.from("bcd"), node);
		SuffixTreeNode child3 = new SuffixTreeNodeImpl(CharLinkedList.from("hello"), node);
		SuffixTreeNode child4 = new SuffixTreeNodeImpl(CharLinkedList.from("mmmmm"), node);
		node.setChildren(new SuffixTreeNode[]{child1, child2, child3, child4, null, null, null, null}, 4);

		// binary search
		test(node.binarySearch('b', 0, 3) == child2, "search for 'b' should find child2");

		// search
		test(node.search('a') == child1, "search for 'a' should return child1");
		test(node.search('x') == null, "search for 'x' should fail");


		// add child
		SuffixTreeNode child5 = new SuffixTreeNodeImpl(CharLinkedList.from("dog"), node);
		node.addChild(child5);
		test(node.getChildren()[2] == child5, "3rd child should be child5");
		
		
		//shift children
		node.shiftChildren(3);
		SuffixTreeNode[] A=node.getChildren();
		//test(A[3] == child5, "4th child should be child5");
		
		//addSuffix
		String word="mississippi";
		word += "$";
		char[] cword = word.toCharArray();
		SuffixTreeNodeImpl p=new SuffixTreeNodeImpl();
		for (int i=0; i<word.length(); i++)
			p.addSuffix(cword, i);
		//test(p.getChildren()[0].getChars().getFirst().getChar() == 'i', "1st char of 1st children of p should b 'i'");
		//test(p.getChildren()[4].getChars().getFirst().getChar() == '$', "1st char of 4 children of p should b '$'");
		//System.out.println(p.getChildren()[0].getChars().getFirst().getNext().getChar());
		
		//compress
		SuffixTreeNode root1=new SuffixTreeNodeImpl();
		SuffixTreeNode n1=new SuffixTreeNodeImpl(CharLinkedList.from("a"), root1);
		SuffixTreeNode n2=new SuffixTreeNodeImpl(CharLinkedList.from("b"), root1);
		SuffixTreeNode n3=new SuffixTreeNodeImpl(CharLinkedList.from("c"), root1);
		root1.setChildren(new SuffixTreeNode[]{n1, n2, n3, null, null, null, null, null}, 3);
		
		SuffixTreeNode n4=new SuffixTreeNodeImpl(CharLinkedList.from("a"), n1);
		SuffixTreeNode n5=new SuffixTreeNodeImpl(CharLinkedList.from("d"), n1);
		n1.setChildren(new SuffixTreeNode[]{n4, n5, null, null, null, null, null, null}, 2);
		
		SuffixTreeNode n6=new SuffixTreeNodeImpl(CharLinkedList.from("a"), n5);
		n5.setChildren(new SuffixTreeNode[]{n6, null, null, null, null, null, null, null}, 1);
		root1.compress();
		
		test(root1.getChildren()[0]==n1, "1st child of root should be n1");
		test(root1.getChildren()[2]==n3, "3rd child of root should be n3");
		test(n1.getChildren()[0]==n4, "1st child of n1 should be n4");
		test(n1.getChildren()[1]==n5, "2nd child of n1 should be n5");
		test(n1.getChildren()[1].getChars().getFirst().getChar()=='d' 
				&& n1.getChildren()[1].getChars().getLast().getChar()=='a',
				"2nd child of n1 should hold the chars: 'da'");
		
		//findSuffixLeaf
		SuffixTree T=new SuffixTreeImpl("bakbuk");
		SuffixTree T3=new SuffixTreeImpl("mississippi");
		test(T3.getRoot().findSuffixLeaf(new char[] {'p','i'}, 0)==
				T3.getRoot().getChildren()[2].getChildren()[0], "should be another node");
		
		
		//findLCA
		SuffixTreeNode node1=T3.getRoot().findSuffixLeaf(new char[] {'i','p','p','i'}, 0);
		SuffixTreeNode node2=T3.getRoot().findSuffixLeaf(new char[] {'i','s','s','i','p','p','i'}, 0);
		test(node1.findLCA(node2)==T3.getRoot().getChildren()[0], "parent should be another node");
		SuffixTreeNode node3=T3.getRoot().findSuffixLeaf(new char[] {'s','s','i','p','p','i'}, 0);
		//System.out.println(T3.getRoot().getChildren()[3].getChildren()[1].getChildren()[0].findLCA(node3));
		//System.out.println(T3.getRoot().getChildren()[0].getChildren()[0].getChars().size());
		
		//System.out.println("node1: "+node1);
		//System.out.println("node2: "+node2);
		//System.out.println(node1.findLCA(node2));
		
//		SuffixTree T2=new SuffixTreeImpl("i");
//		
//		System.out.println(T.getRoot().getDescendantLeaves());
//		System.out.println(T.getRoot().getChildren()[0].getDescendantLeaves());
//		System.out.println(T.getRoot().getChildren()[0]);
//
//		System.out.println(T.getRoot().getChildren()[1].getDescendantLeaves());
//		System.out.println(T.getRoot().getChildren()[1]);
//		System.out.println(T.getRoot().getChildren()[1].getChildren()[0].getDescendantLeaves());
//		System.out.println(T.getRoot().getChildren()[1].getChildren()[1].getDescendantLeaves());
//		
//		System.out.println(T.getRoot().getChildren()[2].getDescendantLeaves());
//		System.out.println(T.getRoot().getChildren()[2]);
//		System.out.println(T.getRoot().getChildren()[2].getChildren()[0].getDescendantLeaves());
//		System.out.println(T.getRoot().getChildren()[2].getChildren()[1].getDescendantLeaves());
//		
//		System.out.println(T.getRoot().getChildren()[3].getDescendantLeaves());
//		System.out.println(T.getRoot().getChildren()[3]);
//		
//		System.out.println(T.getRoot().getChildren()[4].getDescendantLeaves());
//		System.out.println(T.getRoot().getChildren()[4]);
		
	}
	
	private static void testSuffixTree()
	{
		
		String word="mississippi";
		word += "$";
		//test contains
		SuffixTreeImpl t=new SuffixTreeImpl(word);
		test(t.contains("ms") == false, "tree shouldn't contain 'ms'");
		test(t.contains("ppi") == true, "tree should contain 'ppi'");
		test(t.contains("mi") == true, "tree should contain 'ppi'");
		
		//test numOfOccurrences
		test(t.numOfOccurrences("iss") == 2, "tree should contain 'iss' twice");
		test(t.numOfOccurrences("mi") == 1, "tree should contain 'mi' once");
		test(t.numOfOccurrences("pi") == 1, "tree should contain 'pi' once");
	}

	private static void testReverseSuffixTree(){
		//testPalindrome("mississippi", "issXssi");
		
		testPalindrome("abc", "X");

		
		testPalindrome("abbc", "bb");
		
	}

	private static void testPalindrome(String word, String expected){
		test(new ReverseSuffixTreeImpl(word).longestPalindrome().equals(expected), "Longest palindrome should be " + expected);
	}

}