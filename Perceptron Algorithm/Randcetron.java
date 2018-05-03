import java.util.ArrayList;

public class Randcetron {
	double sRate=0;
	Letter x;
	public Randcetron(Letter x) {
		this.x=x;
	}
	
	public void test(ArrayList<Example> ListOfexample) {
		ArrayList<Example> ex=ListOfexample;
		double total_fired=0;
		double func=0;
		double pos_pos=0;
		double total_counter=0;
		double neg_neg=0;
		double letterCounter=0;
		for (Example example : ex) {
			Letter cur=example.getLetter();
			int [] bits=example.getBits();
			
			func=(Math.random()*2);
			if(x==cur) {
				letterCounter++;
			}
			boolean r = ((func>1)&&(cur==x));
			boolean f = ((func<=1)&&(cur!=x));
			/*FIX*/
			if(r) {//func>weights[0] ??
				pos_pos++;
			} if(f) {
				neg_neg++;
			} if(func>1){
				total_fired++;
			}
			total_counter++;
		}
		sRate=((pos_pos*(6.0/7)+neg_neg*(1.0/7)))/total_counter * 100;
//		if(sRate>14.0)
//		System.out.println(
//				"\ttotal examples:\t"+total_counter +
//				"\n\t positive positives:\t" + pos_pos + "\t%"+((pos_pos/letterCounter)*100)+
//				"\n\t negative negatives:\t"+neg_neg+  "\t%"+((neg_neg/(total_counter-letterCounter))*100)+
//				"\n\t positive negatives\t"+(total_fired-pos_pos)+
//				"\n\t negative positives:\t"+(letterCounter-pos_pos)+		
//				"\n\t current letter count:\t"+letterCounter+
//				"\n\t total_fired:\t"+total_fired+
//				"\n\t total succsess rate:\t%"+sRate
//				);
	}
}
