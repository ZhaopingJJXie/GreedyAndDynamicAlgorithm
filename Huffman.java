
import java.util.*;

public class Huffman {
	
	private String input;
	private Node huffmanTree; //the huffman tree
	private Map<Character, String> mapping; //maps characters to binary strings
	
	
	/**
	 * The Huffman constructor
	 * 
	 */
	public Huffman(String input) {
		
		this.input = input;
		mapping = new HashMap<>();
		
		//first, we create a map from the letters in our string to their frequencies
		Map<Character, Integer> freqMap = getFreqs(input);
		
		//we'll be using a priority queue to store each node with its frequency,
		//as we need to continually find and merge the nodes with smallest frequency
		PriorityQueue<Node> huffman = new PriorityQueue<>();
		
		/*
		 * TODO:
		 * 1) add all nodes to the priority queue
		 * 2) continually merge the two lowest-frequency nodes until only one tree remains in the queue
		 * 3) Use this tree to create a mapping from characters (the leaves)
		 *    to their binary strings (the path along the tree to that leaf)
		 *    
		 * Remember to store the final tree as a global variable, as you will need it
		 * to decode your encrypted string
		 */
		
		for(Character c : freqMap.keySet()) {
			Node currentNode = new Node(c, freqMap.get(c), null, null );
			huffman.add(currentNode);
		}
		int treeNum = huffman.size();
		while(treeNum >= 2) {
			Node right = huffman.poll();
			Node left = huffman.poll();
			Node mergedNode = new Node(null, right.freq + left.freq,  left, right );
			huffman.add(mergedNode);
			treeNum--;
		}
		huffmanTree= huffman.peek();
		
	}
	
	/**
	 * Use the global mapping to convert your input string into a binary string
	 */
	public String encode() {
		String str = "";
		encode(huffmanTree, "");
		for (int i = 0; i < input.length(); i++) {
			str = str+ mapping.get(input.charAt(i));
		}
		return str;
	}
	public void encode(Node root, String st) {
		if (root==null) {
			return;
		}
		if(root.letter!=null && root.freq==input.length()) {
			for (int i = 0; i < input.length(); i++) {
				st = st+"0";
			}
			mapping.put(root.letter, st);
			
		}
		if(root.isLeaf()) {
			mapping.put(root.letter, st);
		}
		else {
			encode(root.left, st+"0");
			encode(root.right, st + "1");
		}
		
		
	}
	
	/**
	 * Use the huffmanTree to decrypt the encoding back into the original input
	 * 
	 * You should convert each prefix-free group of binary numbers in the
	 * encoding to a character
	 * 
	 * @param encoding - the encoded string that needs to be decrypted
	 * @return the original string (should be the same as "input")
	 */
	public String decode(String encoding) {
		//TODO
		String s= "";
		int index = 0 ; 
		while (index < encoding.length()) {
			Node no= huffmanTree;
			while(!no.isLeaf()) {
				if(encoding.charAt(index)=='0') {
					no = no.left;	
				}
				else {
					no = no.right;
				}
				index++;
			}
			s= s+ no.letter;
		}
		
		return s;
	}
	
	/**
	 * This function tells us how well the compression algorithm worked
	 * 
	 * note that a char is represented internal using 8 bits
	 * 
	 * ex. if the string "aabc" maps to "0 0 10 11", we would have
	 * a compression ratio of (6) / (8 * 4) = 0.1875
	 */
	public static double compressionRatio(String input) {
		Huffman h = new Huffman(input);
		String encoding = h.encode();
		int encodingLength = encoding.length();
		int originalLength = 8 * input.length();
		return encodingLength / (double) originalLength;
	}
	
	/**
	 * We've given you this function, which helps you create
	 * a frequency map from the input string
	 */
	private Map<Character, Integer> getFreqs(String input) {
		Map<Character, Integer> freqMap = new HashMap<>();
		for (char c : input.toCharArray()) {
			if (freqMap.containsKey(c)) {
				freqMap.put(c, freqMap.get(c) + 1);
			} else {
				freqMap.put(c, 1);
			}
		}
		return freqMap;
	}


	/**
	 * An inner Node class to build your huffman tree
	 * 
	 * Each node has:
	 * a frequency - the sum of the frequencies of all the node's leaves
	 * a letter - the character that this node represents (only for leaves)
	 * left and right children
	 */
	private class Node implements Comparable<Node> {
		private Character letter; //the letter of this node (only for leaves)
		private int freq; //frequency of this node
		private Node left; //add a 0 to you string
		private Node right; //add a 1 to your string
		
		public Node(Character letter, int freq, Node left, Node right) {
			this.letter = letter;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}
		
		public boolean isLeaf() {
			return left == null && right == null;
		}
		
		@Override
		public int compareTo(Node o) {
			return this.freq - o.freq;
		}
	}

}

