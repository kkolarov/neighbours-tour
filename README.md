# Neighbours Tour
[![Build Status](https://travis-ci.com/kkolarov/neighbours-tour.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

*Angel likes to travel around neighbor countries. To help him plan his next trip you need to create a REST API accepting the following request parameters: Starting country Budget per country (equal for all neighbor countries) Total budget Input Currency*

-  *The API should calculate how many exact times can Angel go through all neighbor countries within his total budget.*
- *The API should calculate the budget for each country in their respected currencies. If the exchange rate is missing it should return the amount in the original currency. The potential leftover amount from the total budget should also be returned in the original currency.*

*Example: Angel fills in the following request values: • Starting country: Bulgaria (BG) • Budget per country: 100 • Total budget: 1200 • Currency: EUR*

*Example Result: Bulgaria has 5 neighbor countries (TR, GR, MK, SR, RO) and Angel can travel around them 2 times. He will have 200 EUR leftover. For Turkey he will need to buy 1325.30 TL, for North Macedonia he will need to buy 12232.51 MKD, and so on.*

## Requisite
  - Java 8+
  - npm
## Installation

`Step 1` - clone the repository

```bash
$ git clone https://github.com/k.kolarov/neighbours-tour
```

`Step 2` - cd to the project

```bash
$ cd ./neighbours-tour
```

`Step 3` - install

```bash
$ ./mvnw install
```

## Configuration

Neighbour countries are configured in the neighbours.properties located in the resources folder.

What is the structure of neighbours.properties?

neighbours.[COUNTRY_CODE]=[NEIGHBOUR_COUNTRY_CODE_1],[NEIGHBOUR_COUNTRY_CODE_2]...[NEIGHBOUR_COUNTRY_CODE__N]

Example:

neighbours.BG=TR,GR,MK,SR,RO


## Usage

`Step 1` - Start the web app

```bash
$ ./mvnw spring-boot:run
```
The web app is listening on 8000 port by default.

`Step 2` - Login by using Google OAuth 2.0

```bash
 http://localhost:[PORT]/login
```
`Step 3` - REST API

There are two available endpoints:
- http://localhost:[PORT]/api/v1/tours/calculate_counties_maximum_visit
- http://localhost:[PORT]/api/v1/tours/calculate_local_budget

Query parameters:

| Name | Type | Description |
| ------ | ------ | ------ |
| tourTotalBudget | double | Total budget
| tourCostPerCountry | double | Budget per country
| tourStartingCountry | string | Starting country
| tourOriginalCurrency | string | Original currency

Example: 
1. http://localhost:8000/api/v1/tours/calculate_local_budget?tourStartingCountry=BG&tourCostPerCountry=100&tourTotalBudget=1200&tourOriginalCurrency=EUR
2. http://localhost:8000/api/v1/tours/calculate_counties_maximum_visit?tourStartingCountry=BG&tourCostPerCountry=100&tourTotalBudget=1200&tourOriginalCurrency=EUR

