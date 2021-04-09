package com.lambdaschool.countries.controllers;


import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryRepository countryRepository;

//    /names/all
    @GetMapping(value = "/names/all", produces = "application/json")
    public ResponseEntity<?> findAllCountries(){
        List<Country> countries = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(e -> countries.add(e));
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

//    /names/start/{letter}
    @GetMapping(value = "/names/start/{letter}", produces = "application/json")
    public ResponseEntity<?> findByLetter(@PathVariable char letter) {
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countryList::add);

        List<Country> filteredList = filterCountries(countryList,
                (e) ->Character.toLowerCase(e.getName().charAt(0)) == Character.toLowerCase(letter));
        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }

//    /population/total
    @GetMapping(value = "/population/total", produces = "application/json")
    public ResponseEntity<?> getPopTotal(){
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countryList::add);

        long total = 0;
        for (Country c : countryList) {
            total += c.getPopulation();
        }

        System.out.println("The total pop is: " + total);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

//    /population/min
    @GetMapping(value = "/population/min", produces = "application/json")
    public ResponseEntity<?> getPopMin(){
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((c1, c2) -> c1.getPopulation() > c2.getPopulation() ? 1 : -1);

        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);
    }

//    /population/max
    @GetMapping(value = "/population/max", produces = "application/json")
    public ResponseEntity<?> getPopMax(){
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((c1, c2) -> c1.getPopulation() < c2.getPopulation() ? 1 : -1);

        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);
    }

//    /population/median
    @GetMapping(value = "/population/median", produces = "application/json")
    public ResponseEntity<?> getMedianPop(){
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((c1, c2) -> c1.getPopulation() < c2.getPopulation() ? 1 : -1);

        double median = countryList.size()/2 - 1.00;
        int idx = (int)median;

        return new ResponseEntity<>(countryList.get(idx), HttpStatus.OK);
    }


//    PUT AT BOTTOM EVERY TIME
    private List<Country> filterCountries(List<Country> countryList, CheckCountries tester) {
        List<Country> rtnList = new ArrayList<>();
        for(Country c : countryList){
            if (tester.test(c)){
                rtnList.add(c);
            }
        }
        return rtnList;
    }

}
