package org.example;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class MainApp {
        static void scriere(List<Angajat>lista) {
            try {
                File file=new File("src/main/resources/angajati.json");
                ObjectMapper mapper=new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.writeValue(file,lista);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static List<Angajat> citire() {
            try {
                File file=new File("src/main/resources/angajati.json");
                ObjectMapper mapper=new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                List <Angajat> = mapper
                        .readValue(file, new TypeReference<List<Angajat>>(){});
                return angajati;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static void main(String[] args) {
            List <Angajat> angajati=new ArrayList<>();
            angajati.add(new Angajat("Maria","manager", LocalDate.of(2022, 4, 12),2500));
            angajati.add(new Angajat("Ion","IT", LocalDate.of(2023, 3, 11),3000));
            angajati.add(new Angajat("Mihai","HR", LocalDate.of(2021, 2, 20),2000));
            angajati.add(new Angajat("Ioana","sef departament", LocalDate.of(2020, 5, 21),5000));
            angajati.add(new Angajat("Ionela","contabil", LocalDate.of(2019, 1, 10),3000));

            scriere(angajati);

        try {
            ObjectCodec mapper;
            List<Angajat> angajati = mapper.readValue(new File("src/main/resources/angajati.json"), new TypeReference<List<Angajat>>() {});

            angajati.forEach(System.out::println);


            System.out.println("\nAngajatii cu salariu peste 2500 RON:");
            angajati.stream()
                    .filter(angajat -> angajat.getSalariu() > 2500)
                    .forEach(System.out::println);


            Year currentYear = Year.now();
            List<Angajat> aprilLeaders = angajati.stream()
                    .filter(angajat -> angajat.getDataAngajarii().getMonth() == Month.APRIL &&
                            angajat.getDataAngajarii().getYear() == currentYear.minusYears(1).getValue() &&
                            (angajat.getPost().toLowerCase().contains("sef") || angajat.getPost().toLowerCase().contains("director")))
                    .collect(Collectors.toList());
            System.out.println("\nAngajati in Aprilie anul trecut pe pozitii de lider:");
            aprilLeaders.forEach(System.out::println);


            System.out.println("\nEmployees without leadership positions sorted by salary (descending):");
            angajati.stream()
                    .filter(angajat -> !angajat.getPost().toLowerCase().contains("director") &&
                            !angajat.getPost().toLowerCase().contains("sef"))
                    .sorted(Comparator.comparingDouble(Angajat::getSalariu).reversed())
                    .forEach(System.out::println);


            List<String> uppercaseNames = angajati.stream()
                    .map(angajat -> angajat.getNume().toUpperCase())
                    .collect(Collectors.toList());
            System.out.println("\nUppercase names of employees:");
            uppercaseNames.forEach(System.out::println);


            System.out.println("\nSalaries below 3000 RON:");
            angajati.stream()
                    .filter(angajat -> angajat.getSalariu() < 3000)
                    .map(Angajat::getSalariu)
                    .forEach(System.out::println);

            System.out.println("\nData of the first employee:");
            Optional<Angajat> firstEmployee = angajati.stream()
                    .min(Comparator.comparing(Angajat::getDataAngajarii));
            firstEmployee.ifPresentOrElse(System.out::println, () -> System.out.println("No employee found"));


            System.out.println("\nStatistics about employee salaries:");
            DoubleSummaryStatistics stats = angajati.stream()
                    .collect(Collectors.summarizingDouble(Angajat::getSalariu));
            System.out.println("Average Salary: " + stats.getAverage());
            System.out.println("Minimum Salary: " + stats.getMin());
            System.out.println("Maximum Salary: " + stats.getMax());


            long summerHiresCount = angajati.stream()
                    .filter(angajat -> angajat.getDataAngajarii().getMonth().getValue() >= 6 &&
                            angajat.getDataAngajarii().getMonth().getValue() <= 8 &&
                            angajat.getDataAngajarii().getYear() == currentYear.minusYears(1).getValue())
                    .count();
            System.out.println("\nNumber of employees hired in the summer of the previous year: " + summerHiresCount);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
