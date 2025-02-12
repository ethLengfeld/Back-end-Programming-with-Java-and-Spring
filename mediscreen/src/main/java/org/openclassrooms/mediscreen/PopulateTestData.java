package org.openclassrooms.mediscreen;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openclassrooms.mediscreen.model.Patient;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PopulateTestData {

    private static final String PATIENT_REQUEST_BODY = "family=%s&given=%s&dob=%s&sex=%c&address=%s&phone=%s";
    private static final String NOTE_REQUEST_BODY = "patId=%d&note=%s";

    public static void main(String[] args) {
        HttpClient mediscreenHttpClient = HttpClient.newHttpClient();
        //patients
//        parseAndLoadPatients(mediscreenHttpClient);
        //notes
        //TODO
        // read through 'Practitioner+Notes+Sample.csv'
        // call http://localhost:8081/patHistory/add?patId=x&note=x
//        parseAndLoadNotes(mediscreenHttpClient);
    }

    /**
     * parse & load patients
     *
     * @param httpClient
     */
    private static void parseAndLoadPatients(HttpClient httpClient) {
        File patientDataFile = new File(
                "./mediscreen/src/main/resources/data/Patient+Demographics+Sample.csv");

        try (CSVParser csvParser = CSVParser.builder()
                .setFile(patientDataFile)
                .setFormat(CSVFormat.DEFAULT.builder().setHeader().get())
                .get()) {

            for (CSVRecord record : csvParser) {
                String family = record.get("Last Name");
                String given = record.get("First Name");
                String dob = record.get("Date of Birth");
                char sex = record.get("Sex").charAt(0);
                String homeAddress = record.get("Home Address");
                String phoneNumber = record.get("Phone Number");
                String body = String.format(PATIENT_REQUEST_BODY, family, given, dob, sex, homeAddress, phoneNumber);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8081/patient/add")) // Replace with your URL
                        .header("Content-Type", "application/x-www-form-urlencoded") // Form data type
                        .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                        .build();
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void parseAndLoadNotes(HttpClient httpClient) {
        File patientDataFile = new File(
                "./mediscreen/src/main/resources/data/Practitioner+Notes+Sample.csv");

        try (CSVParser csvParser = CSVParser.builder()
                .setFile(patientDataFile)
                .setFormat(CSVFormat.DEFAULT.builder().setHeader().get())
                .get()) {

            for (CSVRecord record : csvParser) {
                String lastName = record.get("Patient");
                String note = record.get("Notes");
                //TODO get patient id
                int id = 1;
                String body = String.format(NOTE_REQUEST_BODY, id, note);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8081/patHistory/add")) // Replace with your URL
                        .header("Content-Type", "application/x-www-form-urlencoded") // Form data type
                        .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                        .build();
                //TODO send request
                httpClient.send(null, HttpResponse.BodyHandlers.ofString());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
