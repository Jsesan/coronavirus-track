package com.jsesan.coronavirustrack.servIces;

import com.jsesan.coronavirustrack.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CoronavirusDataService {

    Map<String, Integer> allStats = new HashMap<String, Integer>();

    public Map<String, Integer> getAllStats() {
        return allStats;
    }

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {

        Map<String, Integer> newStats = new HashMap<String, Integer>();

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {

            LocationStats locationStats = new LocationStats();

            locationStats.setCountry(record.get("Country/Region"));
            locationStats.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));

            System.out.println(locationStats);

            Integer prev = newStats.get(locationStats.getCountry());
            Integer newValue = locationStats.getLatestTotalCases();
            if(prev != null)
                newValue += prev.intValue();

            newStats.put(locationStats.getCountry(), newValue);

        }

        this.allStats = newStats;
    }
}
