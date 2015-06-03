import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class Crawler {
	// static FirefoxProfile profile = new FirefoxProfile(new
	// File("C:\\Users\\jagalindo\\AppData\\Local\\Mozilla\\Firefox\\Profiles\\d75bq9ju.default"));
	static WebDriver driver = new FirefoxDriver();

	static Collection<String> referencesToDownload = new LinkedList<String>();
	static PrintWriter out;
	static Random r;
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		out = new PrintWriter("bibtex.bib");
		r= new Random();
		driver.get("https://scholar.google.fr/scholar?cites=8673778080292292169&as_sdt=2005&sciodt=0,5&hl=en");
		// WebElement sigin = driver.findElement(By.linkText("Sign In"));
		// sigin.click();

		waitForEnter();

		boolean hasMore = false;
		do {
			getCitations();
			Thread.sleep(10000+r.nextInt(20000));
			try {
				WebElement findElement = driver.findElement(By.linkText("Next"));
				findElement.click();
				hasMore = true;
			} catch (NoSuchElementException e) {
				hasMore = false;
				waitForEnter();
				try {
					WebElement findElement = driver.findElement(By.linkText("Next"));
					findElement.click();
					hasMore = true;
				} catch (NoSuchElementException e2s) {
					hasMore = false;
					
				}
			}
		} while (hasMore);

		printbibtexs();
		out.close();
		// Close the browser
		driver.quit();
	}

	private static void printbibtexs() throws InterruptedException {
		// TODO Auto-generated method stub
		for (String url : referencesToDownload) {
			Thread.sleep(10000+r.nextInt(20000));
			driver.get(url);
			String bibtex = driver.getPageSource();
			out.println(bibtex);
			out.flush();
		}
	}

	public static void getCitations() {
		// Find the text input element by its name
		List<WebElement> list = driver.findElements(By
				.linkText("Import into BibTeX"));

		for (WebElement elem : list) {
			String reference = elem.getAttribute("href");
			if (reference.contains("output=citation")) {
				referencesToDownload.add(reference);

			}
		}
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
