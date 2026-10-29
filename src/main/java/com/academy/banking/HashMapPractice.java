
package com.academy.banking;

import java.util.HashMap;
import java.util.Map;

public class HashMapPractice {

    public static void main(String[] args) {

        Map<String, String> customers = new HashMap<>();

        customers.put("TR001", "Ali");
        customers.put("TR002", "Ayşe");
        customers.put("TR003", "Mehmet");
        customers.put("TR004", "Zeynep");

        System.out.println(
                "Toplam müşteri: " + customers.size()
        );

        customers.put("TR001", "Ahmet");

        System.out.println(customers.get("TR001"));

        System.out.println(customers.get("TR999"));
    }
}
