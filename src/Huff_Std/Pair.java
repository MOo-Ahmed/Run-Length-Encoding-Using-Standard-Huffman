package Huff_Std;

public class Pair {
	public int NumOfZeros , next ,  categoryNumber , freq ;
	public String binary , huff;
	public boolean isEOB ;
	public Pair(int N , int nxt) {
		NumOfZeros = N ;
		next = nxt ;
		if(nxt == 0) {
			isEOB = true ;
			NumOfZeros = 0 ;
			categoryNumber = -1 ;
		}
	}
	
	public Pair() {}
	@Override
	public String toString() {
		return NumOfZeros + "/" + categoryNumber ;
		//return NumOfZeros + ",," + next + ",," + categoryNumber + ",," + huff + ",," + binary ;
	}

}
