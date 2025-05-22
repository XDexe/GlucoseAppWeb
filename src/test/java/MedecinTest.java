import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class MedecinTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  WebDriverWait wait;

  @BeforeEach
  public void setUp() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<>();
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @AfterEach
  public void tearDown() {
    if (driver != null) driver.quit();
  }

  @Test
  public void login(){
    driver.get("http://localhost:8080/GlucoseApplication-1.0-SNAPSHOT/authentification.html");
    driver.manage().window().setSize(new Dimension(1200, 800));

    driver.findElement(By.id("formId:identifiant")).clear();
    driver.findElement(By.id("formId:identifiant")).sendKeys("admin");
    driver.findElement(By.id("formId:mdp")).clear();
    driver.findElement(By.id("formId:mdp")).sendKeys("admin");
    driver.findElement(By.cssSelector(".ui-button-text")).click();
  }

  @Test
  public void ajoutMedecin() {
    login();

    // Attente après login
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#formGestionMedecin\\3A buttonMedecin")));

    // Click sur bouton gestion médecin
    WebElement btnMedecin = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#formGestionMedecin\\3A buttonMedecin > .ui-button-text")));
    btnMedecin.click();

    // Click bouton ajouter
    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#addMedecin > .ui-button-text"))).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formAddMedecin:medecinNom")));


    // Remplir les champs
    driver.findElement(By.id("formAddMedecin:medecinNom")).sendKeys("medecin");
    driver.findElement(By.id("formAddMedecin:medecinPrenom")).sendKeys("medecin");
    driver.findElement(By.id("formAddMedecin:medecinIdentifiant")).sendKeys("medecin");
    driver.findElement(By.id("formAddMedecin:medecinMdp")).sendKeys("medecin");

    // Click bouton sauvegarder
    WebElement btnSave = driver.findElement(By.cssSelector("#formAddMedecin\\3AmedecinSave > .ui-button-text"));
    btnSave.click();
  }
}
