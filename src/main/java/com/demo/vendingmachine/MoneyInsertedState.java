package com.demo.vendingmachine;

public class MoneyInsertedState implements VendingMachineState {
    @Override
    public void insertMoney(VendingMachine VM, double amount) {

    }

    @Override
    public void selectProductByCode(VendingMachine VM, String productCode) throws Exception {

    }

    @Override
    public void dispenseProduct(VendingMachine VM) throws Exception {

    }

    @Override
    public String getStateDescription() {
        return "";
    }
}
