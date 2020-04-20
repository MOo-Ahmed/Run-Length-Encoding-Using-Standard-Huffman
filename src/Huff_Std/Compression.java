package Huff_Std;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Compression {
	public String streamInput ;
	public String [] inputParts ;
	public ArrayList<Pair> pairs ;
	public ArrayList<Pair> categories ;
	public static Map <Pair,String> HuffmanTable ;
	String CompressionOutputStream ;

	public Compression(String streamInput) throws IOException {
		pairs = new ArrayList<Pair>() ;
		/*System.out.println("Enter your stream message : ");
		Scanner sc = new Scanner(System.in) ;
		streamInput = sc.nextLine() ;
		*/
		inputParts = streamInput.split(",") ;
		prepareZerosFrequencies(streamInput);
		prepareCategories();
		prepareDescriptors();
		prepareBinaryAdditionalBits();
		Huffman h = new Huffman();
		HuffmanTable = h.build(this.pairs);
		CompressionOutputStream = "" ;
		for (Entry<Pair, String> entry : HuffmanTable.entrySet())  {
			String temp = entry.getValue();
			for(int j = 0 ; j < pairs.size() ; j++) {
				if(pairs.get(j).categoryNumber == entry.getKey().categoryNumber && pairs.get(j).NumOfZeros == entry.getKey().NumOfZeros) {
					pairs.get(j) .huff = temp ;
				}
				else if(pairs.get(j).categoryNumber == -1) {
					pairs.get(j) .huff = temp ;
				}
			}
		}
		for(int i = 0 ; i < pairs.size() ; i++) {
			if(pairs.get(i).categoryNumber != -1 && i != pairs.size()-1)
				CompressionOutputStream = CompressionOutputStream + pairs.get(i).huff + "," + pairs.get(i).binary + " ";
			else {
				CompressionOutputStream = CompressionOutputStream + pairs.get(i).huff ;
			}
		} 
		BufferedWriter writer = new BufferedWriter(new FileWriter
				("D:/FCAI-CU material/Multimedia/implementations/Huff_Std/Stream.txt"));
	    writer.write(CompressionOutputStream);
	    writer.close();
		//System.out.println(CompressionOutputStream);
		//System.out.println(HuffmanTable);
	}

	public void prepareZerosFrequencies(String msg) {
		for(int i = 0 ; i < inputParts.length ; i++) {
			if(inputParts[i].equals("0") == true)	continue ;
			int countOfZeros = 0 ;
			for(int j = i-1 ; j >= 0 && inputParts[j].contains("0") == true ; j--) {
				countOfZeros++ ;
			}
			Pair p = new Pair(countOfZeros, Integer.parseInt(inputParts[i])) ;
			pairs.add(p) ;
		}
		//System.out.println(pairs);

	} 

	public void prepareCategories() {
		categories = new ArrayList<Pair>() ;
		for(int i = 0 ; i < 50 ; i++) {
			Pair p1 = new Pair() , p2 = new Pair() ;
			p1.next = i+1 ;
			p2.next = p1.next * -1 ;
			p1.categoryNumber = p2.categoryNumber = NumberOfBitsToRepresent(i+1) ;
			p1.binary = getBinaryRepresentation(i+1) ;
			p2.binary = invertBinary(p1.binary);
			categories.add(p1);
			categories.add(p2);
			/*System.out.println(p1.next + "\t" + p1.categoryNumber + "\t" + p1.binary);
			System.out.println(p2.next + "\t" + p2.categoryNumber + "\t" + p2.binary);
			System.out.println("\n");*/
		}
	}

	public void prepareBinaryAdditionalBits() {
		for(int i = 0 ; i < pairs.size() ; i++) {
			if(pairs.get(i).next > 0) {
				pairs.get(i).binary = categories.get((pairs.get(i).next - 1) *2 ).binary ;
			}
			else if(pairs.get(i).next < 0) {
				pairs.get(i).binary = categories.get((pairs.get(i).next*-1 - 1) *2 +1).binary ;
			}
			else {
				pairs.get(i).binary = "" ;
			}
		}
	}

	public int NumberOfBitsToRepresent(int number) {
		int n = 0 ;
		int tmp = number ;
		for(int i = 0 ; tmp != 0 ; i++) {
			n++ ;
			tmp /= 2 ;
		}
		return n ;
	}

	public String getBinaryRepresentation(int number) {
		String binary = "" ;
		int mod , tmp = number ;
		for(int i = 0 ; tmp != 0 ; i++) {
			mod = tmp % 2 ;
			binary = mod + binary ;
			tmp /= 2 ;
		}

		return binary ;
	}

	public String invertBinary(String binary){
		String tmp = binary , inv = "";
		for(int i = 0 ; i < tmp.length() ; i++) {
			if(tmp.charAt(i) == '0') {
				inv += '1' ;
			}
			else	inv += '0' ;
		}
		return inv ;
	}

	public void prepareDescriptors() {
		for(int i = 0 ; i < pairs.size() ; i++) {
			if(pairs.get(i).next > 0)	pairs.get(i).categoryNumber = NumberOfBitsToRepresent(pairs.get(i).next) ;
			else	pairs.get(i).categoryNumber = NumberOfBitsToRepresent(pairs.get(i).next * -1) ;
			//System.out.println(pairs.get(i).next + "\t" + pairs.get(i).categoryNumber);
		}
	}
}