package com.demo.vendingmachine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VendingMachine {
    // Stores the history of all completed transactions
    private final List<Transaction> transactionHistory;
    // Manages the inventory of products in the vending machine
    private final InventoryManager inventoryManager;
    // Handles all payment-related operations
    private final PaymentProcessor paymentProcessor;

    // Tracks the current ongoing transaction
    private Transaction currentTransaction;
    // Represents the current state of the vending machine
    private VendingMachineState currentState;
    // Tracks the current balance in the machine
    private double balance;
    // Stores the currently selected product code
    private String selectedProduct;

    public VendingMachine() {
        transactionHistory = new ArrayList<>();
        currentTransaction = new Transaction();
        inventoryManager = new InventoryManager();
        paymentProcessor = new PaymentProcessor();
        this.currentState = new NoMoneyInsertedState();
        this.balance = 0.0;
        this.selectedProduct = null;
    }

    // Updates the rack configuration with new product racks
    void setRack(Map<String, Rack> rack) {
        inventoryManager.updateRack(rack);
    }

    // Adds money to the payment processor
    void insertMoney(final BigDecimal amount) {
        paymentProcessor.addBalance(amount);
    }

    // Selects a product from a specific rack
    void chooseProduct(String rackId) {
        final Product product = inventoryManager.getProductInRack(rackId);
        currentTransaction.setRack(inventoryManager.getRack(rackId));
        currentTransaction.setProduct(product);
    }

    // Processes and completes the current transaction
    Transaction confirmTransaction() throws Exception {
        // Step 1: Validate the transaction before processing
        validateTransaction();
        // Step 2: Charge the customer for the product
        paymentProcessor.charge(currentTransaction.getProduct().getUnitPrice());
        // Step 3: Dispense the product from the rack
        inventoryManager.dispenseProductFromRack(currentTransaction.getRack());
        // Step 4: Return the change to the customer
        currentTransaction.setTotalAmount(paymentProcessor.returnChange());
        // Step 5: Add the completed transaction to the history
        transactionHistory.add(currentTransaction);
        Transaction completedTransaction = currentTransaction;
        // Reset the current transaction for the next purchase.
        currentTransaction = new Transaction();
        return completedTransaction;
    }

    // Validates the current transaction for product availability and sufficient funds
    private void validateTransaction() throws Exception {
        if (currentTransaction.getProduct() == null) {
            throw new Exception("Invalid product selection");
        } else if (currentTransaction.getRack().getProductCount() == 0) {
            throw new Exception("Insufficient inventory for product.");
        } else if (paymentProcessor
                .getCurrentBalance()
                .compareTo(currentTransaction.getProduct().getUnitPrice())
                < 0) {
            throw new Exception("Insufficient fund");
        }
    }

    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    // Cancels the current transaction and returns any inserted money
    public void cancelTransaction() {
        paymentProcessor.returnChange();
        currentTransaction =
            new Transaction(); // Reset the current transaction for the next purchase.
    }

    // Returns the inventory manager instance
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public void addBalance(double amount) {
        this.balance = amount;
    }

    public void setState(VendingMachineState currentState) {
        this.currentState = currentState;
    }
}
