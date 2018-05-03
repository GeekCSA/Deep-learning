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
	 * @param ListOfexample ArrayList of Examples for training the perceptron.
	 * @param x Enum Letter, for what letter we want our perceptron to work with.
	 */
	Letter x;
	int weights[];
	boolean bipolar,rand;
	double sRate;
	int positive_positives;
	int negative_negatives;
	int total;
	int let;
	public void resetSTAT() {
		positive_positives=0;
		negative_negatives=0;
		total=0;
		let=0;
	}
	//Random weights init?
	public Perceptron(boolean rand,Letter x) {
		this.x=x;
		this.rand=rand;
		weights=new int[169];
		//		if(rand)randomize();
	}
	//	private void randomize(){
	//		for(int i = 0; i < weights.length; i++) {
	//			weights[i] = Math.random();
	//		}
	//	}
	public void reset() {
		weights=new int[169];
	}
	public  void train(Example example){
		int func=0,flag=-1;
		Letter cur=example.getLetter();
		int [] bits=example.getBits();
		/*test*/
		for(int i=1,j=0; i<weights.length;i++,j++) {
			func+=weights[i]*bits[j];
		}
		boolean r = (func > weights[0]) && (cur == x);
		boolean f = (func <= weights[0]) && (cur != x);
		/*FIX*/
		if(!(r||f)) {
			if(cur == x)flag=1;
			weights[0] += flag * 1;
			for(int i=1 ,j=0; i<weights.length;i++,j++) {
				weights[i]=weights[i]+flag *bits[j];
			}

		}
	}

	/**
	 * 
	 * @param weights weights array, where weights[0] is the threshold.
	 * @param ListOfexample ArrayList of Examples for training the perceptron.
	 * @param x Enum Letter, for what letter we want our perceptron to work with.
	 * @return 
	 */
	public boolean test(Example example) {
		int func=0,flag=0;
		Letter cur=example.getLetter();
		int [] bits=example.getBits();

		/*test*/
		for(int i=1,j=0; i<weights.length;i++,j++) {
			func+=weights[i]*bits[j];
		}
		if((cur==x)&&(func>weights[0]))positive_positives++;
		if((cur!=x)&&func<=weights[0])negative_negatives++;
		if(cur==x)let++;
		total++;
		if(func>weights[0])return true;
		else return false;
		
	}
}