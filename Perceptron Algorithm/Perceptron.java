import java.awt.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class Perceptron {
	/**
	 * 
	 * @param weights weights array, where weights[0] is the threshold, initialized to all zeros.
	 * @param ListOfexample ArrayList of Examples for training the Preceptron.
	 * @param x Enum Letter, for what letter we want our preceptron to work with.
	 */
	public static void algorithm(int[] weights,ArrayList<Example> ListOfexample,Letter x){

		int func=0;

		for (Example example : ListOfexample) {
			Letter cur=example.getLetter();
			int [] bits=example.getBits();

			/*test*/
			for(int i=1,j=0; i<weights.length;i++,j++) {
				func+=weights[i]*bits[j];
			}
			boolean r=((func)>weights[0])&&(cur==x);
			boolean f=((func)<=weights[0])&&(cur!=x);
			/*FIX*/
			if(!(r||f)) {
				if(!r) {
					weights[0]+=1;
					for(int i=1,j=0; i<weights.length;i++,j++) {
						weights[i]=weights[i]+bits[j];
					}
				}else {
					weights[0]-=1;
					for(int i=1,j=0; i<weights.length;i++,j++) {
						weights[i]=weights[i]-bits[j];
					}
				}
			}
		}
	}

	public static void test(int[] weights,ArrayList<Example> ListOfexample, Letter x) {

		int func=0;
		int sCounter=0;
		int counter=0;
		
		for (Example example : ListOfexample) {
			Letter cur=example.getLetter();
			int [] bits=example.getBits();
			
			/*test*/
			for(int i=1,j=0; i<weights.length;i++,j++) {
				func+=weights[i]*bits[j];
			}
			boolean r=((func)>weights[0])&&(cur==x);
			boolean f=((func)<=weights[0])&&(cur!=x);
			/*FIX*/
			if((r||f)) {
				sCounter++;
			}
			counter++;
		}
		
		System.out.println("\ttotal examples: "+counter +
				"\ttotal success: " + sCounter +
				"\n\t succsess rate: "+(double)sCounter/counter * 100);
	}
}
