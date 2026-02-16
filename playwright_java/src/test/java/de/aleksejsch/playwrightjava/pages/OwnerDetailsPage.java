package de.aleksejsch.playwrightjava.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import de.aleksejsch.playwrightjava.dto.Owner;
import de.aleksejsch.playwrightjava.dto.Visit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OwnerDetailsPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(OwnerDetailsPage.class);

    private final Locator nameLocator = page.locator("xpath=//th[text()='Name']/following-sibling::td/b");
    private final Locator addressLocator = page.locator("xpath=//th[text()='Address']/following-sibling::td");
    private final Locator cityLocator = page.locator("xpath=//th[text()='City']/following-sibling::td");
    private final Locator telephoneLocator = page.locator("xpath=//th[text()='Telephone']/following-sibling::td");

    private final Locator successMessage = page.locator("#success-message");
    private final Locator editOwnerButton = page.locator("a[href*='/edit']");  // Robust
    private final Locator addNewPetButton = page.locator("a[href*='/pets/new']");

    private final Locator petsTable = page.locator("xpath=//h2[contains(text(),'Pets')]/following::table[1]");
    private final Locator visitsTable = page.locator("xpath=//h2[contains(text(),'Pets')]/following::table[contains(@class,'table-condensed')]");

    public OwnerDetailsPage(Page page) {
        super(page);
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public String getOwnerName() { return getText(nameLocator); }
    public String getAddress() { return getText(addressLocator); }
    public String getCity() { return getText(cityLocator); }
    public String getTelephone() { return getText(telephoneLocator); }
    public String getSuccessMessage() { return successMessage.isVisible() ? successMessage.textContent() : ""; }


    public int getId() {
        String href = editOwnerButton.getAttribute("href");
        if (href == null) throw new AssertionError("Edit button href not found");

        String[] parts = href.split("/");
        return Integer.parseInt(parts[parts.length - 2]);  // Vorher: length - 2
    }


    public Owner getOwnerDetails() {
        String fullName = getOwnerName();
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new AssertionError("Owner name not found");
        }

        String[] names = fullName.trim().split("\\s+");

        return Owner.builder()
                .firstName(names[0])
                .lastName(names.length > 1 ? names[1] : "")
                .address(getAddress())
                .city(getCity())
                .telephone(getTelephone())
                .id(getId())
                .build();
    }


    public Map<String, Locator> getEditPetAndAddVisitLinks() {
        Map<String, Locator> links = new LinkedHashMap<>();
        Locator firstRow = petsTable.locator("tbody tr").first();
        List<Locator> rowLinks = firstRow.locator("a").all();

        for (Locator link : rowLinks) {
            String text = link.textContent();
            if (text != null && !text.trim().isEmpty()) {
                links.put(text.trim(), link);
            }
        }
        logger.debug("Found links: {}", links.keySet());
        return links;
    }


    public Map<String, String> getPetData() {

        Locator firstRow = petsTable.locator("tbody tr:has(dl)").first();
        if (firstRow.count() == 0) {
            return Collections.emptyMap();
        }

        Map<String, String> petData = new LinkedHashMap<>();

        // dt/dd Paare direkt finden
        List<Locator> dtElements = firstRow.locator("dl dt").all();
        List<Locator> ddElements = firstRow.locator("dl dd").all();

        for (int i = 0; i < Math.min(dtElements.size(), ddElements.size()); i++) {
            String key = dtElements.get(i).textContent().trim();
            String value = ddElements.get(i).textContent().trim();
            petData.put(key, value);
        }

        // Pet ID aus Link
        Locator firstLink = firstRow.locator("a").first();
        if (firstLink.isVisible()) {
            String href = firstLink.getAttribute("href");
            if (href != null) {
                String petId = extractPetId(href);
                if (petId != null) petData.put("id", petId);
            }
        }

        return petData;
    }


    /*
    public void waitForVisitsData() {
        visitsTable.locator(":has-text('Add Visit')")
                .waitFor(new Locator.WaitForOptions().setTimeout(2000));
    }
*/
    public List<Visit> getVisitData() {
        //waitForVisitsData();

        // Daten-Rows (skip header + footer)
        List<Locator> dataRows = visitsTable
                .locator("tbody tr:not(:has(a))")
                .all();

        return dataRows.stream()
                .map(this::parseVisitRow)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Visit parseVisitRow(Locator row) {
        try {

            Locator dateCell = row.locator("td:nth-child(1)").first();
            Locator descCell = row.locator("td:nth-child(2)").first();

            if (!dateCell.isVisible() || !descCell.isVisible()) {
                logger.debug("Skipping invalid row");
                return null;
            }

            String dateText = dateCell.textContent().trim();
            String description = descCell.textContent().trim();

            if (dateText.isBlank() || description.isBlank()) {
                return null;
            }

            LocalDate date = LocalDate.parse(dateText);
            return new Visit(description, date);

        } catch (Exception e) {
            logger.warn("Failed to parse visit row: {}", row.textContent(), e);
            return null;
        }
    }


    private String extractPetId(String href) {
        String[] parts = href.split("/");
        for (int i = 0; i < parts.length - 1; i++) {
            if ("pets".equals(parts[i])) {
                return parts[i + 1];
            }
        }
        return null;
    }

    public void clickEditOwner() {
        editOwnerButton.click(new Locator.ClickOptions().setForce(true));
    }

    public void clickAddNewPet() {
        addNewPetButton.click();
    }
}
