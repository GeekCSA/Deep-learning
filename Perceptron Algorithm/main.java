import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File folder = new File("src/Latters/");
		File[] listOfFiles = folder.listFiles();
		
		ArrayList<String> shmonim = new ArrayList<>();//80%
		ArrayList<String> esrim = new ArrayList<>();//20%
		
		int[] arr = {20,80};
		
		FileModification fm;
		
		
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				fm = new FileModification(listOfFiles[i].getAbsolutePath());
				System.out.println(fm.numOfLinesInFile());
				ArrayList<String>[] temp = fm.splitRandomly(arr, 1);
				//			for (int i = 0; i < temp.length; i++) {
				shmonim.addAll(temp[1]);
				esrim.addAll(temp[0]);
				//			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
        Collections.shuffle(esrim);
        Collections.shuffle(shmonim);
		
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//		try(FileReader fr = new FileReader("src/Latters/Bet.csv"))
//		{
//			BufferedReader br = new BufferedReader(fr);		
//			System.out.println(br.readLine());
//			
//		}
//		catch (Exception e) {
//			System.out.println(e);
//		}
	}

}
