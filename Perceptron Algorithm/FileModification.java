import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author Moshe
 * @since 16/4/2018
 */
public class FileModification {
	
	static void print_Csv(ArrayList<String> csv,String i) {
		String header="total examples,pos_pos,pos_pos%,neg_neg,neg_neg%,letterCounter,total_fired,sRate%,Learning_Rate,noise,%test,%train";
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("src/stat/"+i+".csv");
			writer.println(header);
			Iterator<String> it=csv.iterator();
			while(it.hasNext()) {
				String s=it.next();
				writer.println(s);
			}
			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	final static int maxRangeOfNoisePercent = 100;
	
	private File file;
	private long numOfLines;

	/**
	 * @param String path - path of a file
	 * @throws IOException
	 */
	public FileModification(String path) throws IOException {

		file = new File(path);
		if(!file.exists()) {

			throw new FileNotFoundException("Could not find file: " + file.getName());

		}
		numOfLines = -1;
	}

	/**
	 * 
	 * @return The number of lines in the file.
	 * @throws IOException
	 */
	public long numOfLinesInFile() throws IOException
	{
		if(numOfLines == -1){

			Stream<String> lines = Files.lines(file.toPath(), Charset.defaultCharset());
			numOfLines = lines.count();

		}
		return numOfLines;
	}

	/**
	 * 
	 * @param percent - Array preserves the desired distribution of the number of rows per file in percentages.
	 * @param numOfSkipHeaderLine - Number of title rows that we need to skip and don't read.
	 * @param noise - Percent of noise to do in the data. (The range is 0 to 100)
	 * @return - Array of ArrayList<String>. each ArrayList<String> holds rows according to the percentages given to the function.
	 * @throws Exception The noise is out of range 0 - 100
	 */
	public ArrayList<Example>[] splitFile(int[] percent,int numOfSkipHeaderLine,int noise) throws Exception
	{
		long[] linesForThePercent = null;
		
		if (noise < 0 || noise > maxRangeOfNoisePercent) {
		    throw new Exception("The noise is out of range 0 - 100");
		}

		try {//Calculate how many actual rows fit for each percentage
			linesForThePercent = CalculateLines(percent,numOfLinesInFile(),numOfSkipHeaderLine);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//The array is returned
		ArrayList<Example>[] testAndTry = new ArrayList[linesForThePercent.length];


		try(FileReader fr = new FileReader(file);)
		{
			PrintWriter writer = null;

			BufferedReader br = new BufferedReader(fr);
			for (int i = 0; i < numOfSkipHeaderLine; i++) {
				br.readLine();//skip on the title lines
			}

			for (int i = 0; i < linesForThePercent.length; i++) {

				//Create a file that contain the rows for a specified percent
//				writer = new PrintWriter(getFilePathWithoutName() + "/" + getFileNameWhitoutType() + "_" + i + "_With_noise_of_" + noise + "." + getFileType(), "UTF-8");
				
				testAndTry[i] = new ArrayList<>();

				for (int j = 0; j < linesForThePercent[i]; j++) {
					String s = br.readLine();
					Example ex;
					try {
						ex = new Example(s);
						if (i == 1) {//noise only 80%
							imageNoise(ex, noise, ",");
						}
						testAndTry[i].add(ex);
//						writer.println(ex.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
//				writer.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return testAndTry;
	}

	//For example: for folder/somefile.csv return .csv
	private String getFileType() {
		return file.getName().substring(file.getName().indexOf(".") + 1);
	}

	//For example: for folder/somefile.csv return somefile
	private String getFileNameWhitoutType() {
		return file.getName().substring(0,file.getName().indexOf("."));
	}

	//For example: for folder/somefile.csv return folder
	private String getFilePathWithoutName() {
		return file.getParent();
	}

	/**
	 * Calculate how many actual rows fit for each percentage
	 * 
	 * @param percent - Array preserves the desired distribution of the number of rows per file in percentages. 
	 * @param numOfLinesInFile - Numbers of rows in this file
	 * @param numOfSkipHeaderLine - Number of title rows that we need to skip and don't read.
	 * @return The array that contain how many actual rows fit for each percentage.
	 */
	private long[] CalculateLines(int[] percent, long numOfLinesInFile, int numOfSkipHeaderLine) {

		long[] retArr = new long[percent.length];

		long linesWithoutTitles = numOfLinesInFile - numOfSkipHeaderLine;

		int countOfLines = 0;
		int i = 0;
		for (i = 0; i < percent.length - 1; i++) {
			retArr[i] = (long)percent[i] * linesWithoutTitles / 100;
			countOfLines += retArr[i];
		}
		retArr[i] = linesWithoutTitles - countOfLines;

		return retArr;
	}
	
	/**
	 * The function takes an object of type Example And "stirring" the array of bits in it according to the required percentage.
	 * For example, if the number is 10 then there will be 10% of noise,
	 * meaning that each digit will be reversed (0 -> 1 or 1 -> 0) with a probability of 0.1.
	 * 
	 * @param ex - An object that contains information about this type of letter and also the bit array representing the signal
	 * @param percentageOfNoise - A number that represents how many percent of "noise" should be in this array. (The range is 0 to 100)
	 * @param separator - The separator between '0' and '1'. 
	 */
	public void imageNoise(Example ex, int percentageOfNoise, String separator) {
		
		Random rand = new Random(10);
		
		int[] temp = ex.getBits();
				
		//Start from index 1 (not 0) because at index 0 ...
		for(int i = 0; i < temp.length; i++)
		{
			if(rand.nextInt(maxRangeOfNoisePercent) <= percentageOfNoise)
			{//Do noise in this digit
				temp[i] = (temp[i] == 1)? 0 : 1;
			}
		}
		
		ex.setBits(temp);
	}

}

