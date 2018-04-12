import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * @author Moshe
 * @since 4/4/2018
 */
public class FileModification {

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
	 * @return - Array of ArrayList<String>. each ArrayList<String> holds rows according to the percentages given to the function.
	 */
	public ArrayList<String>[] splitFile(int[] percent,int numOfSkipHeaderLine)
	{
		long[] linesForThePercent = null;

		try {//Calculate how many actual rows fit for each percentage
			linesForThePercent = CalculateLines(percent,numOfLinesInFile(),numOfSkipHeaderLine);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//The array is returned
		ArrayList<String>[] testAndTry = new ArrayList[linesForThePercent.length];


		try(FileReader fr = new FileReader(file);)
		{
			PrintWriter writer = null;

			BufferedReader br = new BufferedReader(fr);
			for (int i = 0; i < numOfSkipHeaderLine; i++) {
				br.readLine();//skip on the title lines
			}

			for (int i = 0; i < linesForThePercent.length; i++) {

				//Create a file that contain the rows for a specified percent
				writer = new PrintWriter(getFilePathWithoutName() + "/" + getFileNameWhitoutType() + "_" + i + "." + getFileType(), "UTF-8");
				
				testAndTry[i] = new ArrayList<>();

				for (int j = 0; j < linesForThePercent[i]; j++) {
					String s = br.readLine();
					testAndTry[i].add(s);
					writer.println(s);
				}
				writer.close();
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

}
