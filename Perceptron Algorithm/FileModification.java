import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FileModification {

	private File file;
	private long numOfLines;

	/**
	 * 
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

	public long numOfLinesInFile() throws IOException
	{
		if(numOfLines == -1){

			Stream<String> lines = Files.lines(file.toPath(), Charset.defaultCharset());
			numOfLines = lines.count();

		}
		return numOfLines;
	}

	/*
	 * 
	 */
	public ArrayList<String>[] splitRandomly(int[] percent,int numOfSkipHeaderLine)
	{
		long[] linesForThePercent = null;

		try {
			linesForThePercent = CalculateLines(percent,numOfLinesInFile(),numOfSkipHeaderLine);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<String>[] testAndTry = new ArrayList[linesForThePercent.length];


		try(FileReader fr = new FileReader(file);)
		{
			PrintWriter writer = null;

			BufferedReader br = new BufferedReader(fr);
			for (int i = 0; i < numOfSkipHeaderLine; i++) {
				br.readLine();//skip on the title lines
			}

			for (int i = 0; i < linesForThePercent.length; i++) {

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
		finally
		{
		}
		return testAndTry;
	}

	private String getFileType() {
		return file.getName().substring(file.getName().indexOf(".") + 1);
	}

	private String getFileNameWhitoutType() {
		return file.getName().substring(0,file.getName().indexOf("."));
	}

	private String getFilePathWithoutName() {
		return file.getParent();
	}

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
