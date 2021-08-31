package week4.Day2;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.*;
import java.io.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import kotlin.collections.ArrayDeque;

public class Project1Myntra {

	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriverManager.chromedriver().setup();
		
		//To handle browser notifications
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
    	ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		
        //Launch the myntra site
        driver.get("https:www.myntra.com");
        driver.manage().window().maximize();
        Thread.sleep(2000);
        
        //To click Jackets options on Men Mouseover
        WebElement menoption = driver.findElement(By.xpath("(//a[text()='Men'])[1]"));
        Actions builder = new Actions(driver);
        builder.moveToElement(menoption).perform();
        Thread.sleep(1000);
        driver.findElement(By.linkText("Jackets")).click();
       
        //To get the Total Item count & compare with sum of items returned from all categories
        String itemcount = driver.findElement(By.xpath("//span[@class='title-count']")).getText();
        System.out.println("Number of Items returned for Jackets Search : " + itemcount);
        itemcount=itemcount.replaceAll("\\D", "");
        System.out.println(driver.findElement(By.xpath("//input[@value='Jackets']/following-sibling::span")).getText());
        String category1 = driver.findElement(By.xpath("//input[@value='Jackets']/following-sibling::span")).getText();
        String category2 = driver.findElement(By.xpath("//input[@value='Rain Jacket']/following-sibling::span")).getText();
        String str1  = category1.replaceAll("\\D", "");
        int catcount1 = Integer.parseInt(str1);
        String str2  = category2.replaceAll("\\D", "");
        int catcount2 = Integer.parseInt(str2);
        int totalcatcount = catcount1+catcount2;
        System.out.println("Total items returned from all categories : "+itemcount);
        if(Integer.parseInt(itemcount)==totalcatcount)
        	System.out.println("Both Total item count & Total items returned from all categories are equal : "+totalcatcount);
        else
        	System.out.println("Mismatch between Total item count & Total items returned from all categories");
        
        //Click Jackets check box, goto More options and search for Duke brand
        driver.findElement(By.xpath("(//div[@class='common-checkboxIndicator'])[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@class='brand-more']")).click();
        driver.findElement(By.xpath("//input[@placeholder='Search brand']")).sendKeys("Duke");
        Thread.sleep(1000);      
        driver.findElement(By.xpath("//input[@value='Duke']/following-sibling::div")).click();
        driver.findElement(By.xpath("//span[@class='myntraweb-sprite FilterDirectory-close sprites-remove']")).click(); 
      
        //Get all the brand names & verify all are Duke
        List<WebElement> brands = driver.findElements(By.xpath("//h3[@class='product-brand']"));
        int count=0;
        for(WebElement eachrow : brands) {
        	String name=eachrow.getText();
        	if(! name.equals("Duke")) {
        	System.out.println("Brand name is different: "+ name);
        	count++;
        	}	
        }	
        if(count>0)
        	System.out.println("All Brands are not Duke");
        else
        	System.out.println("All displayed Brands are Duke");
        
        //To Select the 'Sort by better discount' option & get the price of the first item & click it
        WebElement sortoptions = driver.findElement(By.xpath("//div[@class='sort-sortBy']"));
        builder.moveToElement(sortoptions).perform();
        driver.findElement(By.xpath("//label[text()='Better Discount']")).click();
        Thread.sleep(1000);
        List<WebElement> returnprods = driver.findElements(By.xpath("//ul[@class='results-base']/li"));
        String firstprodPrice = driver.findElement(By.xpath("//span[@class='product-discountedPrice']")).getText();
        System.out.println("Price of the first product displayed : "+firstprodPrice);
        returnprods.get(0).click();
        Thread.sleep(3000);
        
        //To bring the control to the clicked page to take the screen shot
        Set<String> wins = driver.getWindowHandles();
        List<String> wins1 = new ArrayList<String>();
        wins1.addAll(wins);
        driver.switchTo().window(wins1.get(1));
        
        // To Take Screen shot         
        File src1 = driver.getScreenshotAs(OutputType.FILE);
    	File dst = new File("./folder/product1.png");
    	FileUtils.copyFile(src1, dst);        
        
        //Click on Wishlist
        driver.findElement(By.xpath("//span[@class='myntraweb-sprite desktop-iconWishlist sprites-headerWishlist']")).click(); 
        System.out.println(driver.getTitle());
        
        driver.close();
        driver.switchTo().window(wins1.get(0));
        driver.close();
	}

}
