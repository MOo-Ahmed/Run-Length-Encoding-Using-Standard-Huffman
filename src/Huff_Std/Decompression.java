package Huff_Std;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Decompression {
	public static Map <Pair,String> HuffmanTable ;
	public static int numCall = 0 ;
	static String  NewMessage = "";

	public Decompression(String msg, Map <Pair,String> Table ) {
		HuffmanTable = new HashMap <Pair, String> ();
		HuffmanTable = Table ;
		String [] inputParts = msg.split(" ");
		for(int i = 0 ; i < inputParts.length ; i++) {
			String Hcode = new String() , AdBits = new String() ;
			Hcode = (inputParts[i].split(","))[0] ;
			if(inputParts[i].contains(","))	AdBits = inputParts[i].split(",")[1] ;
			else	AdBits = "EOB" ;
			Pair tmp = new Pair();
			for (Entry<Pair, String> entry : HuffmanTable.entrySet())  {
				if(entry.getValue().equals(Hcode) == true)
				{
					tmp = entry.getKey();
					//System.out.println("Cat = " + tmp.categoryNumber + " ,, zeros = " + tmp.NumOfZeros + " ,, " + entry.getKey().NumOfZeros);
					break; 
				}
				else	continue ;

			}
			for(int j = tmp.NumOfZeros ; j > 0 ; j-- ) {
				NewMessage += "0," ;
			}
			int num = toDecimal(AdBits) ;
			if(getBinaryRepresentation(num).equals(AdBits))	{
				
				NewMessage += num + "," ;
				//System.out.println("got hereee");
			}
			else	{
				AdBits = invertBinary(AdBits) ;
				num = -1 * toDecimal(AdBits) ;
				
				if(toDecimal(AdBits)!=0)	NewMessage += num + "," ;
				else NewMessage += "EOB" ;
			}
			//System.out.println(NewMessage);
		}
		//System.out.println(NewMessage);
	}

	public String getBinaryRepresentation(int number) {
		String binary = "" ;
		int mod , tmp = number ;
		for(int i = 0 ; tmp != 0 ; i++) {
			mod = tmp % 2 ;
			binary = mod + binary ;
			tmp /= 2 ;
		}
		//System.out.println(binary);
		return binary ;
	}

	public String invertBinary(String binary){
		numCall++ ;
		String tmp = binary , inv = "";
		for(int i = 0 ; i < tmp.length() ; i++) {
			if(tmp.charAt(i) == '0') {
				inv += '1' ;
			}
			else	inv += '0' ;
		}
		//System.out.println("binary = " + binary + "  inverted = " + inv + "  " + numCall);
		return inv ;
	}


	public int toDecimal(String binary) {
		//numCall++ ;
		if(binary == "EOB") return 0 ;
		int number = 0 ;
		for(int i = binary.length() -1 ; i >= 0 ; i--) {
			//System.out.print(binary.charAt(i) + "\t");
			int digit = Integer.parseInt(binary.charAt(i) + "") ;
			if(digit == 0)	number += 0 ;
			else	number += ( Math.pow(2, binary.length() -1 - i));
			//System.out.println("num == "+ number);
		}
		//System.out.println(number + " /// " + numCall);
		return number ;
	}
}
