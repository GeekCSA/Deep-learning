import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

/**
 * @author Moshe, Reut
 * @since 16/4/2018
 */
public class main {

	static ArrayList<Example> onlyNVY(ArrayList<Example> ex){
		ArrayList<Example> only=new ArrayList<Example>();
		for(Example exp:ex) {
			if(exp.getLetter()==Letter.BET||exp.getLetter()==Letter.GIMEL
					||exp.getLetter()==Letter.ZAIN||exp.getLetter()==Letter.KAF) {

			}else {
				only.add(exp);
			}

		}return only;
	}
	static double[] avg(ArrayList<double []> avg) {
		double ret[]=new double[170];
		Iterator<double []> it=avg.iterator();
		while(it.hasNext()) {
			double temp []=it.next();
			for(int i=0;i<temp.length;i++) {
				ret[i]+=temp[i];
			}
		}
		for(int i=0;i<ret.length;i++) {
			ret[i]/=avg.size();
		}
		return ret;
	}

	public static void main(String[] args) {
		
		Perceptron bet=new Perceptron(true,Letter.BET);
		Perceptron gimel=new Perceptron(true,Letter.GIMEL);
		Perceptron vav=new Perceptron(true,Letter.VAV);
		Perceptron zain=new Perceptron(true,Letter.ZAIN);
		Perceptron yud=new Perceptron(true,Letter.YUD);
		Perceptron nun=new Perceptron(true, Letter.NUN);
		Perceptron kaf=new Perceptron(true, Letter.KAF);

		File folder = new File("src/Latters/");
		File[] listOfFiles = folder.listFiles();

		//4 Examples ArrayLists for 4 fold validation.
		ArrayList<Example> first = new ArrayList<>();
		ArrayList<Example> second = new ArrayList<>();
		ArrayList<Example> third = new ArrayList<>();
		ArrayList<Example> fourth = new ArrayList<>();

		//This array preserves the desired distribution of the number of rows per file in percentages
		int[] percentages = {25,25,25,25};

		FileModification fm;

		//Go through each of the data files to divide the desired shape
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				fm = new FileModification(listOfFiles[i].getAbsolutePath());//Holding a specific file
				ArrayList<Example>[] temp = fm.splitFile(percentages, 1,0);
				first.addAll(temp[1]);
				second.addAll(temp[0]);
				third.addAll(temp[2]);
				fourth.addAll(temp[3]);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		ArrayList<ArrayList<Example>> trainer=new ArrayList<ArrayList<Example>>();
		trainer.add(first);
		trainer.add(second);
		trainer.add(third);
		trainer.add(fourth);
		boolean id=false;
		double rate=0;
		/*MAGIC*/
		int round=0;
		while(round<50) {
			System.out.println("***************************************************");
			System.out.println(round++);

			Collections.shuffle(first);
			Collections.shuffle(second);
			Collections.shuffle(third);
			Collections.shuffle(fourth);
			Collections.shuffle(trainer);

			for(int i=0; i<=2;i++) {
				for(int j=0;j<trainer.get(i).size();j++) {
					bet.train(trainer.get(i).get(j));
					gimel.train(trainer.get(i).get(j));
					vav.train(trainer.get(i).get(j));
					zain.train(trainer.get(i).get(j));
					yud.train(trainer.get(i).get(j));
					nun.train(trainer.get(i).get(j));
					kaf.train(trainer.get(i).get(j));

				}
			}
			int index=3;
			int counter=0;
			double id_counter=0;
			double size=trainer.get(index).size();
			for(int j=0;j<trainer.get(index).size();j++) {
				Letter Cur=trainer.get(index).get(j).getLetter();
				boolean B=bet.test(trainer.get(index).get(j));
				boolean G=gimel.test(trainer.get(index).get(j));
				boolean V=vav.test(trainer.get(index).get(j));
				boolean Z=zain.test(trainer.get(index).get(j));
				boolean Y=yud.test(trainer.get(index).get(j));
				boolean K=kaf.test(trainer.get(index).get(j));
				boolean N=nun.test(trainer.get(index).get(j));
				switch(Cur) {
				case BET:
					if(B==true&&!(G||V||Z||Y||K||N))
						id=true;				
					break;
				case GIMEL:
					if(G==true&&!(B||V||Z||Y||K||N))
						id=true;		
					break;
				case VAV:
					if(V==true&&!(B||G||Z||Y||K||N))
						id=true;
					break;
				case ZAIN:
					if(Z==true&&!(B||G||V||Y||K||N))
						id=true;
					break;
				case YUD:
					if(Y==true&&!(B||G||V||Z||K||N))
						id=true;
					break;
				case KAF:
					if(K==true&&!(B||G||V||Z||Y||N))
						id=true;
					break;
				case NUN:
					if(N==true&&!(B||G||V||Z||K||Y))
						id=true;
					break;
				default:
					id=false;
					break;
				}if(id==true)id_counter++;
									id=false;

				
				

			}
			rate=((id_counter+counter)/size)*100;
			System.out.println(rate);
//			System.out.println(rate+" counter "+id_counter+" size "+size+" "+counter);
			
			System.out.println("BET \tPP\t"+bet.positive_positives+"\t NN\t"+bet.negative_negatives+"\tletter\t"+bet.let);
			System.out.println("GIMEL \tPP\t"+gimel.positive_positives+"\t NN\t"+gimel.negative_negatives+"\tletter\t"+gimel.let);
			System.out.println("VAV \tPP\t"+vav.positive_positives+"\t NN\t"+vav.negative_negatives+"\tletter\t"+vav.let);
			System.out.println("YUD \tPP\t"+yud.positive_positives+"\t NN\t"+yud.negative_negatives+"\tletter\t"+yud.let);
			System.out.println("KAF \tPP\t"+kaf.positive_positives+"\t NN\t"+kaf.negative_negatives+"\tletter\t"+kaf.let);
			System.out.println("ZAIN \tPP\t"+zain.positive_positives+"\t NN\t"+zain.negative_negatives+"\tletter\t"+zain.let);
			System.out.println("NUN \tPP\t"+nun.positive_positives+"\t NN\t"+nun.negative_negatives+"\tletter\t"+nun.let);
			
			System.out.println("total\t"+bet.total);
			
			bet.resetSTAT();
			gimel.resetSTAT();
			vav.resetSTAT();
			yud.resetSTAT();
			kaf.resetSTAT();
			zain.resetSTAT();
			nun.resetSTAT();

		}
		System.out.println(rate);
		
		bet.reset();
		gimel.reset();
		vav.reset();
		zain.reset();
		yud.reset();
		kaf.reset();
		nun.reset();
	}


}





