import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * @author Moshe, Reut
 * @since 16/4/2018
 */
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//The source folder of the data
		File folder = new File("src/Letters/");
		//Array of the files in the source folder
		File[] listOfFiles = folder.listFiles();

		//These arrays hold the rows that return files by percentage 
		ArrayList<Example> eighty = new ArrayList<>();//80%
		ArrayList<Example> twenty = new ArrayList<>();//20%

		//This array preserves the desired distribution of the number of rows per file in percentages
		int[] percentages = {20,80};

		FileModification fm;

		//Go through each of the data files to divide the desired shape
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				fm = new FileModification(listOfFiles[i].getAbsolutePath());//Holding a specific file
				System.out.println(fm.numOfLinesInFile());

				//Split the file by percentage.
				//The 80% of the file is returned in the temp[1] and the 20% of the file is returned in the temp[0] and 10% of noise.
				//0% of noise in the data.
				ArrayList<Example>[] temp = fm.splitFile(percentages, 1,0);
				eighty.addAll(temp[1]);
				twenty.addAll(temp[0]);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}

		//Have to scramble the information so that the letters are not arranged in order but will be messy
		Collections.shuffle(eighty);
		Collections.shuffle(twenty);

		int[] weights = new int[170];

		Perceptron.algorithm(weights, eighty, Letter.BET);

		System.out.println("\t\t\t" + Arrays.toString(weights));

		Perceptron.test(weights, twenty, Letter.GIMEL);

		System.out.println("");
	}
}

