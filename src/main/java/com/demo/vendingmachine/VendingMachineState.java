package com.demo.vendingmachine;

public interface VendingMachineState {
    // Handles money insertion in the current state
    void insertMoney(VendingMachine VM, double amount);

    // Handles product selection in the current state
    void selectProductByCode(VendingMachine VM, String productCode) throws Exception;

    // Handles product dispensing in the current state
    void dispenseProduct(VendingMachine VM) throws Exception;

    // Returns a description of the current state
    String getStateDescription();
}
