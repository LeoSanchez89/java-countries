package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;

@FunctionalInterface
public interface CheckCountries {
    boolean test(Country country);
}
