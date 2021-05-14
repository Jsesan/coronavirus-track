package com.jsesan.coronavirustrack.controllers;

import com.jsesan.coronavirustrack.models.LocationStats;
import com.jsesan.coronavirustrack.servIces.CoronavirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    CoronavirusDataService coronavirusDataService;

    @GetMapping("/")
    public String home(Model model){
        Map<String, Integer> stats = coronavirusDataService.getAllStats();
        List<LocationStats> locationStats = new ArrayList<LocationStats>();
        for(Map.Entry<String, Integer> stat : stats.entrySet()){
            LocationStats locationStat = new LocationStats();
            locationStat.setCountry(stat.getKey());
            locationStat.setLatestTotalCases(stat.getValue());
            locationStats.add(locationStat);
        }
        locationStats.sort(LocationStats::compareTo);

        int totalCases = locationStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();

        model.addAttribute("total", totalCases);
        model.addAttribute("locationStats",locationStats);
        return "home";
    }

}
