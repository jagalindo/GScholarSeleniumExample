import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;


public class BiBTexDownloader {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		PrintWriter out = new PrintWriter("listofpapers.bib");
		FirefoxProfile profile = new FirefoxProfile(new File("C:\\Users\\jagalindo\\AppData\\Local\\Mozilla\\Firefox\\Profiles\\d75bq9ju.default"));
    	WebDriver driver = new FirefoxDriver(profile);
    	
		 driver.get("https://scholar.google.fr/scholar?cites=8673778080292292169&as_sdt=2005&sciodt=0,5&hl=en");
//       WebElement sigin = driver.findElement(By.linkText("Sign In"));
//       sigin.click();
       
       waitForEnter();

		try (BufferedReader br = new BufferedReader(new FileReader("listofurls.list"))) {
		    String line;
		    while ((line = br.readLine()) != null) {

		    	
		    	driver.get(line);
		    	String bibtex = driver.getPageSource();
		    	out.println(bibtex);
		    	out.flush();
		    	
		    }
		}
		out.close();
	}
	public static void waitForEnter() {
		try {
			System.out.println("Press a key to continue");
	        System.in.read();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
}