import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MetOffice extends JFrame {

    public static void main(String args[]) {

        String city = "Reading";
        String url = "https://www.metoffice.gov.uk/";
        // Chrome Driver Path on local machine
        System.setProperty("webdriver.chrome.driver", "C:/Users/KhanM51/Documents/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get(url);
        driver.manage().window().maximize();
        try {
            driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[1]/button")).click();
            driver.findElement(By.xpath("//*[@id=\"location-search-input\"]")).sendKeys(city);
            driver.findElement(By.xpath("//*[@id=\"location-search-submit\"]/span")).click();
            driver.findElement(By.id("btnDetailedView")).click();
        } catch (Exception e) {
            System.out.println("Exception in handling the Web Elements");
        }

        //current date storage
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        //storage of today's humidity value'
        String todayHumidity = driver.findElement(By.xpath("//*[@id=\"" + dateFormat.format(currentDate) + "\"]/table/tbody/tr[15]/td[1]/span")).getAttribute("data-value");
        System.out.println("Todate is : " + (dateFormat.format(currentDate)));
        System.out.println("Today's Humidity Percentage in " + city + " is = " + todayHumidity);

        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        // manipulate date
        c.add(Calendar.DATE, 1); //same with c.add(Calendar.DAY_OF_MONTH, 1);
        // convert calendar to date
        Date nextDayDate = c.getTime();
        driver.findElement(By.xpath("//*[@id=\"dayLink" + dateFormat.format(nextDayDate) + "\"]/div[2]/div[1]/h3/time")).click();

        //storage of tomorrow's humidity level
        String tomorrowHumidity = driver.findElement(By.xpath("//*[@id=\"" + dateFormat.format(nextDayDate) + "\"]/table/tbody/tr[15]/td[1]/span")).getAttribute("data-value");
        System.out.println("Tomrrow date is : " + (dateFormat.format(nextDayDate)));
        System.out.println("Tomorrow Humidity Percentage in " + city + " is = " + tomorrowHumidity);

        //conversion of string values to integer
        int todayValue = Integer.parseInt(todayHumidity);
        int tomorrowValue = Integer.parseInt(tomorrowHumidity);

        //difference of humidity level between today and tomorrow
        System.out.print("The Humidity difference for " + city + " between " + currentDate + " and " + nextDayDate + " is = ");
        System.out.print(tomorrowValue - todayValue);
        int difference = tomorrowValue - todayValue;
        JOptionPane.showMessageDialog(null, "The Humidity difference for " + city + " between " + currentDate + " and " + nextDayDate + " is = " + difference);
        driver.quit();
        {


        }
    }
}
