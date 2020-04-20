package Huff_Std;

import java.util.PriorityQueue; 
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map; 

class Node { 

	int freq; 
	Pair c; 

	Node left; 
	Node right; 
} 

class MyComparator implements Comparator<Node> { 
	public int compare(Node x, Node y) 
	{ 

		return x.freq - y.freq; 
	} 
} 

public class Huffman { 
	Map <Pair,String> HuffmanTable = new HashMap <Pair, String> ();
	public Huffman() {}
	// Here s is the huffman - code generated. 
	public Map <Pair,String> printCode(Node root, String s) 
	{ 
		
		if (root.left == null && root.right == null ) { 
			//System.out.println(root.c + ":" + s); 
			HuffmanTable.put(root.c , s) ;
		} 
		if(root.left != null)	printCode(root.left, s + "0"); 
		if(root.right != null)	printCode(root.right, s + "1"); 
		return HuffmanTable ;
	} 

	public Map <Pair,String>  build(ArrayList<Pair> Pairs) 
	{ 
		ArrayList<Pair> pairs = new ArrayList<Pair>();
		pairs.addAll(Pairs);
		ArrayList<Pair> descriptors = new ArrayList<Pair>(); 
		for(int i = 0 ; i < pairs.size() ; i++) {
			boolean isUnique = true ;
			Pair temp = new Pair() ;
			temp = pairs.get(i) ;
			for(int j = 0 ; j < descriptors.size() ; j++) {
				if(descriptors.get(j).NumOfZeros == temp.NumOfZeros && descriptors.get(j).categoryNumber == temp.categoryNumber) {
					isUnique = false ;
				}
			}
			if(isUnique == true) {
				descriptors.add(temp) ;
			}
		}
		int n = descriptors.size();
		for(int i = 0 ; i < descriptors.size() ; i++) {
			Pair temp = descriptors.get(i) ;
			temp.freq = 0 ;
			for(int j = 0 ; j < pairs.size() ; j++) {
				if(pairs.get(j).NumOfZeros == temp.NumOfZeros && pairs.get(j).categoryNumber == temp.categoryNumber) {
					temp.freq++ ;
				}
			}
		}
		PriorityQueue<Node> q = new PriorityQueue<Node>(n, new MyComparator()); 
		for (int i = 0; i < n; i++) { 
			Node hn = new Node(); 
			hn.c = descriptors.get(i); 
			hn.freq = hn.c.freq; 
			hn.left = null; 
			hn.right = null; 
			q.add(hn); 
		} 

		Node root = null; 
		while (q.size() > 1) { 

			Node x = q.peek(); 
			q.poll(); 
			Node y = q.peek(); 
			q.poll(); 
			Node f = new Node();
			f.freq = x.freq + y.freq; 
			//f.c = '-';
			f.left = x; 
			f.right = y; 
			root = f;  
			q.add(f); 
		} 
		return printCode(root, ""); 
	} 

	


} 

