package com.academy.banking;

import com.academy.banking.service.BankService;

import java.math.BigDecimal;
public class Main {

    public static void main(String[] args) {

        BankService bankService = new BankService();

        bankService.createAccount("TR001", "Ali");
        bankService.createAccount("TR002", "Ayşe");

        bankService.deposit(
                "TR001",
                new BigDecimal("1000.00")
        );

        bankService.deposit(
                "TR002",
                new BigDecimal("2000.00")
        );

        System.out.println("TRANSFER ÖNCESİ");

        System.out.println(
                "Ali bakiye: " +
                        bankService.findAccount("TR001").getBalance()
        );

        System.out.println(
                "Ayşe bakiye: " +
                        bankService.findAccount("TR002").getBalance()
        );

       try {  bankService.transfer(
               "TR001",
               "TR002",
               new BigDecimal("300.00"));

       } catch (IllegalArgumentException e) {
           System.out.println("Transfer hatası: " + e.getMessage());
       }

        System.out.println("TRANSFER SONRASI");

        System.out.println(
                "Ali: " +
                        bankService.findAccount("TR001").getBalance()
        );

        System.out.println(
                "Ayşe: " +
                        bankService.findAccount("TR002").getBalance()
        );
    }
}
