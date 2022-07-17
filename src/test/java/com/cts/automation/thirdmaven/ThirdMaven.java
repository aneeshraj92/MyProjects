package com.cts.automation.thirdmaven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ThirdMaven 
{
	String userName,pass,firstName,lastName;
	String checkinDate,checkoutDate;
	String address,cellValue;
	long ccNo;
	int cvvNo;
	String ccNo1,cvvNo1;
	static WebDriver driver;
	File f;
	FileInputStream fis;
	Workbook w;
	Sheet s,newSheet;
	Row r;
	Cell c;
	FileOutputStream fos;
	Select s1,s2,s3,s4,s5,s6,s7,s8;
	WebElement location,hotel,roomType,roomNo,adult,child,ccType,exMonth,exYear,allRows;
	
	
@BeforeClass
public static void browserOpen()
{
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	driver.get("https://adactinhotelapp.com/HotelAppBuild2/");
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
}

@Before
public void getDetail() throws IOException
{
	f = new File("C:\\Users\\Welcome\\eclipse-workspace\\com.cts.automation.thirdmaven\\src\\test\\resources\\testdata\\Login.xlsx");
	fis = new FileInputStream(f);
	w = new XSSFWorkbook(fis);
	s = w.getSheet("Login");
	
	userName = s.getRow(0).getCell(0).getStringCellValue();
	pass = s.getRow(1).getCell(0).getStringCellValue();
	//checkinDate = String.valueOf(s.getRow(2).getCell(0).getDateCellValue());
	//checkoutDate = String.valueOf(s.getRow(3).getCell(0).getStringCellValue());
	firstName = s.getRow(4).getCell(0).getStringCellValue();
	lastName = s.getRow(5).getCell(0).getStringCellValue();
	address = s.getRow(6).getCell(0).getStringCellValue();
	ccNo = (long) s.getRow(7).getCell(0).getNumericCellValue();
	ccNo1 = String.valueOf(ccNo);
	cvvNo = (int) s.getRow(8).getCell(0).getNumericCellValue();
	cvvNo1 = String.valueOf(cvvNo);
	
}
@Test
public void login()
{
	//Login
	driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userName);
	driver.findElement(By.xpath("//input[@id='password']")).sendKeys(pass);
	driver.findElement(By.xpath("//input[@id='login']")).click();
	
	//Detail enter
	location = driver.findElement(By.xpath("//select[@name='location']"));
	s1 = new Select(location);
	s1.selectByValue("Los Angeles");
	hotel = driver.findElement(By.xpath("//select[@name='hotels']"));
	s2 = new Select(hotel);
	s2.selectByValue("Hotel Sunshine");
	roomType = driver.findElement(By.xpath("//select[@name='room_type']"));
	s3 = new Select(roomType);
	s3.selectByValue("Super Deluxe");
	roomNo = driver.findElement(By.xpath("//select[@name='room_nos']"));
	s4 = new Select(roomNo);
	s4.selectByValue("1");
	adult = driver.findElement(By.xpath("//select[@name='adult_room']"));
	s5 = new Select(adult);
	s5.selectByValue("1");
	
	//Click search
	driver.findElement(By.xpath("//input[@name='Submit']")).click();
	
	//Select Radio button
	driver.findElement(By.xpath("//input[@type='radio']")).click();
	
	//Select Continue
	driver.findElement(By.xpath("//input[@id='continue']")).click();
	
	//Payment detail
	driver.findElement(By.xpath("//input[@id='first_name']")).sendKeys(firstName);
	driver.findElement(By.xpath("//input[@id='last_name']")).sendKeys(lastName);

	driver.findElement(By.xpath("//textarea[@id='address']")).sendKeys(address);
	driver.findElement(By.xpath("//input[@id='cc_num']")).sendKeys(ccNo1);
	
	ccType = driver.findElement(By.xpath("//select[@id='cc_type']"));
	s6 = new Select(ccType);
	s6.selectByValue("AMEX");
	
	exMonth = driver.findElement(By.xpath("//select[@id='cc_exp_month']"));
	s7 = new Select(exMonth);
	s7.selectByValue("8");
	
	exYear = driver.findElement(By.xpath("//select[@id='cc_exp_year']"));
	s8 = new Select(exYear);
	s8.selectByValue("2022");
	
	driver.findElement(By.xpath("//input[@id='cc_cvv']")).sendKeys(cvvNo1);
	
	//Click Book Now
	driver.findElement(By.xpath("//input[@id='book_now']")).click();
	driver.findElement(By.xpath("//input[@id='my_itinerary']")).click();
}

@After
public void importtable() throws IOException
{
	f = new File("C:\\Users\\Welcome\\eclipse-workspace\\com.cts.automation.thirdmaven\\src\\test\\resources\\testdata\\Login.xlsx");
	fos = new FileOutputStream(f);
	newSheet = w.getSheet("Detail");
	//List<WebElement> allRows = driver.findElements(By.xpath("(//tbody)[5]//tr"));
	for(int i=1; i<4; i++)
	{
		r = newSheet.createRow(i);
		for(int j=1; j<14; j++)
		{
			c = r.createCell(j);
			cellValue = driver.findElement(By.xpath("(//tbody)[5]//tr["+(i+1)+"]//td["+(j+1)+"]//input")).getAttribute("value");
			c.setCellValue(cellValue);
			
			
		}
	}
	
	w.write(fos);
	
	
}
@AfterClass
public static void finalMessage()
{
	System.out.println("Finall page data Export to the EXCEL Sheet");
}
}
