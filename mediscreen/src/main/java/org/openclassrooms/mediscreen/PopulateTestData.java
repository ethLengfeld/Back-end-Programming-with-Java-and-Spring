//package org.openclassrooms.mediscreen;
//
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//import org.apache.commons.csv.CSVRecord;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//
//public class PopulateTestData {
//
//    private static final String PATIENT_REQUEST_BODY = "family=%s&given=%s&dob=%s&sex=%c&address=%s&phone=%s";
//    private static final String NOTE_REQUEST_BODY = "patId=%d&note=%s";
//
//    public static void main(String[] args) {
//        HttpClient mediscreenHttpClient = HttpClient.newHttpClient();
//        //patients
//        Map<String, Long> patientIdMap = parseAndLoadPatients(mediscreenHttpClient);
//        //notes
//        parseAndLoadNotes(mediscreenHttpClient, patientIdMap);
//    }
//
//    /**
//     * parse & load patients
//     *
//     * @param httpClient
//     */
//    private static Map<String, Long> parseAndLoadPatients(HttpClient httpClient) {
//        Map<String, Long> patientIdMap = new HashMap<>();
//        File patientDataFile = new File(
//                "./mediscreen/src/main/resources/data/Patient+Demographics+Sample.csv");
//
//        try (CSVParser csvParser = CSVParser.builder()
//                .setFile(patientDataFile)
//                .setFormat(CSVFormat.DEFAULT.builder().setHeader().get())
//                .get()) {
//
//            for (CSVRecord record : csvParser) {
//                String family = record.get("Last Name");
//                String given = record.get("First Name");
//                String dob = record.get("Date of Birth");
//                char sex = record.get("Sex").charAt(0);
//                String homeAddress = record.get("Home Address");
//                String phoneNumber = record.get("Phone Number");
//                String body = String.format(PATIENT_REQUEST_BODY, family, given, dob, sex, homeAddress, phoneNumber);
//                HttpRequest request = HttpRequest.newBuilder()
//                        .uri(URI.create("http://localhost:8081/patient/add")) // Replace with your URL
//                        .header("Content-Type", "application/x-www-form-urlencoded") // Form data type
//                        .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
//                        .build();
//                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//                request = HttpRequest.newBuilder()
//                        .uri(URI.create("http://localhost:8081/load/patient/info?family=" + family)) // Replace with your URL
//                        .header("Content-Type", "application/x-www-form-urlencoded") // Form data type
//                        .GET()
//                        .build();
//                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//                patientIdMap.put(family, Long.parseLong(response.body()));
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        return patientIdMap;
//    }
//
//    private static void parseAndLoadNotes(HttpClient httpClient, Map<String, Long> patientIdMap) {
//        File patientDataFile = new File(
//                "./mediscreen/src/main/resources/data/Practitioner+Notes+Sample.csv");
//
//        try (CSVParser csvParser = CSVParser.builder()
//                .setFile(patientDataFile)
//                .setFormat(CSVFormat.DEFAULT.builder().setHeader().get())
//                .get()) {
//
//            for (CSVRecord record : csvParser) {
//                String lastName = record.get("Patient");
//                String note = record.get("Notes");
//                Long id = patientIdMap.get(lastName);
//                String body = String.format(NOTE_REQUEST_BODY, id, note);
//                HttpRequest request = HttpRequest.newBuilder()
//                        .uri(URI.create("http://localhost:8081/patHistory/add")) // Replace with your URL
//                        .header("Content-Type", "application/x-www-form-urlencoded") // Form data type
//                        .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
//                        .build();
//                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
