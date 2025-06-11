import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SuppressionMedecinTest {
  private WebDriver driver;
  private JavascriptExecutor js;
  private WebDriverWait wait;

  @BeforeEach
  public void setUp() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @AfterEach
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  public void suppressionMedecin() {
    driver.get("http://localhost:8080/GlucoseApplication-1.0-SNAPSHOT/authentification.html");
    driver.manage().window().maximize();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginForm:identifiant"))).sendKeys("f.wheel");
    driver.findElement(By.id("loginForm:mdp")).sendKeys("f.wheel");
    driver.findElement(By.id("loginForm:authButton")).click();
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(.,'Gestion des médecins')]"))).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formTableMedecins:listMedecin")));
    WebElement filterInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#formTableMedecins\\:listMedecin_head .ui-column-filter")));
    filterInput.click();
    filterInput.sendKeys("TestDemo");
    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btnDelete"))).click();
    WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ui-confirm-dialog .ui-confirmdialog-yes")));
    confirmButton.click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ui-messages-info-summary")));

    System.out.println("Test de suppression terminé avec succès !");
  }
}