package de.aleksejsch.selenium.pages;

import de.aleksejsch.selenium.dto.Visit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import de.aleksejsch.selenium.dto.Owner;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.LocalDate;
import java.util.*;


public class OwnerDetailsPage extends BasePage {

    private final By nameLocator = By.xpath("//th[text()='Name']/following-sibling::td/b");
    private final By addressLocator = By.xpath("//th[text()='Address']/following-sibling::td");
    private final By cityLocator = By.xpath("//th[text()='City']/following-sibling::td");
    private final By telephoneLocator = By.xpath("//th[text()='Telephone']/following-sibling::td");
    private final By successMessageLocator = By.id("success-message");
    private final By editOwnerButton = By.cssSelector("a[href$='/edit']");
    private final By addNewPetButton = By.cssSelector("a[href$='/pets/new']");

    public OwnerDetailsPage(WebDriver driver) {
        super(driver);
    }

    public By getPetsTable() {
        return By.xpath("//h2[normalize-space()='Pets and Visits']/following-sibling::table[1]");
    }

    public By getVisitsTable() {
        return By.xpath("//h2[normalize-space()='Pets and Visits']/following-sibling::table[1]//table[contains(@class,'table-condensed')]");
    }

    public String geOwnerName() {
        return getText(nameLocator);
    }

    public String getAddress() {
        return getText(addressLocator);
    }

    public String getCity() {
        return getText(cityLocator);
    }

    public String getTelephone() {
        return getText(telephoneLocator);
    }

    public String getSuccesMessage() {
        return getText(successMessageLocator);
    }

    public int getID() {
        // extract ID from the edit button)
        String href = waitForElement(editOwnerButton).getAttribute("href");
        assert href != null;
        String id = href.split("/")[href.split("/").length - 2];
        return Integer.parseInt(id);
    }

    public Owner getOwnerDetails() {
        String fullName = geOwnerName();
        String[] names = fullName.split(" ");
        String firstName = names[0];
        String lastName = names.length > 1 ? names[1] : "";
        int id = getID();

        return new Owner(
                firstName,
                lastName,
                getAddress(),
                getCity(),
                getTelephone(),
                id
        );
    }

    public Map<String, WebElement> getEditPetAndAddVisitLinks(){
        Map<String, WebElement> linksData = new HashMap<>();
        WebElement table = waitForElement(getPetsTable());
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        List<WebElement> links = rows.getFirst().findElements(By.tagName("a"));

        for (WebElement link : links){
            linksData.put(link.getText(),link);
        }

        return linksData;
    }

    public Map<String, String> getPetData() {
        WebElement table = waitForElement(getPetsTable());
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        List<WebElement> elements = rows.getFirst().findElements(By.xpath(".//dl/*"));
        Map<String, String> petData = new HashMap<>();

        for (int i = 0; i < elements.size(); i += 2) {
            String key = elements.get(i).getText();     // <dt>
            String value = elements.get(i + 1).getText(); // <dd>
            petData.put(key, value);
        }


        List<WebElement> links = rows.getFirst().findElements(By.tagName("a"));


        String href = links.getFirst().getAttribute("href");
        logger.debug("Href: {}", href);

        // Pet-ID extrahieren
        assert href != null;
        String[] parts = href.split("/");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("pets") && i + 1 < parts.length) {
                String petId = parts[i + 1];
                petData.put("id", petId);
                logger.info("Found Pet-Id: " + petId);
            }
        }

        return petData;
    }

    public void waitForVisitsData(){
        wait.until(ExpectedConditions.textToBePresentInElementLocated(getVisitsTable(), "Add Visit"));
    }
    public List<Visit> getVisitData() {
        WebElement visitsTable = waitForElement(getVisitsTable());
        List<WebElement> visitElements = visitsTable.findElements(By.tagName("tr"));

        for (WebElement row : visitElements) {
            System.out.println("ROW: " + row.getText());
        }

        List<Visit> visitList = new LinkedList<>();
        //first and last rows are containing header and links, they will be not taken
        for (int i = 1; i < visitElements.size()-1; i++){
            String visitDataAsText = visitElements.get(i).getText();
            if (!visitDataAsText.isBlank()){
                String[] visitData = visitDataAsText.split(" ");
                String date = visitData[0];
                String description = visitData[1];
                Visit visit = new Visit(description, LocalDate.parse(date));
                visitList.add(visit);
            }
        }
        return visitList;
    }


    public void clickEditOwnerButton(){
        click(editOwnerButton);
    }

    public void clickAddNewPetButton(){
        click(addNewPetButton);
    }

}
