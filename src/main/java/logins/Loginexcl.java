package logins;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Loginexcl {
	private WebDriver d;
 
	@BeforeTest
	public void setup() throws Exception {
		WebDriverManager.chromedriver().setup();
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		d = new ChromeDriver();
		d.manage().window().maximize();
	
	}

	@Test(dataProvider = "getData")
	public void loginTest(String username, String password) throws Exception {

		d.get("https://demozposmanabad.mahamining.com/dashboard-student-details");
		WebElement username1 = d.findElement(By.xpath("//input [@formcontrolname='userName'][1]"));
		WebElement  pwd1     = d.findElement(By.xpath("//input[@formcontrolname='password'][1]"));
		WebElement submit    =d.findElement(By.xpath("//span[@class='mdc-button__label'][1]"));
		username1.sendKeys(username);
		pwd1.sendKeys(password);
		Thread.sleep(1000);
		submit.click();
		 	}
	@DataProvider
	public Object[][] getData() throws IOException {
		String excelPath = "C:\\Users\\niting\\eclipse-workspace\\OsmanabadZp\\target\\excel\\logins.xlsx";
		FileInputStream fileInputStream = new FileInputStream(excelPath);
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheet("Sheet1");

		int rowCount = sheet.getPhysicalNumberOfRows();
		int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

		Object[][] data = new Object[rowCount - 1][colCount];

		for (int i = 1; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				Cell cell = sheet.getRow(i).getCell(j);
				DataFormatter formatter = new DataFormatter();
				String cellData = formatter.formatCellValue(cell);
				data[i - 1][j] = cellData;
			}
		}
		fileInputStream.close();
		workbook.close();
		return data;
	}

	@AfterTest
	public void tearDown() {
		// Close the browser
		{
			d.quit();
		}
	}
}

