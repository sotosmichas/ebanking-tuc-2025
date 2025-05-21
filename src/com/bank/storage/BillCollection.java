    package com.bank.storage;

    import com.bank.model.Bill;
    import com.bank.storage.Storable;

    import java.util.ArrayList;
    import java.util.List;

    public class BillCollection implements Storable {
        private List<Bill> bills;

        public BillCollection() {
            this.bills = new ArrayList<>();
        }

        public List<Bill> getBills() {
            return bills;
        }

        public void addBill(Bill bill) {
            bills.add(bill);
        }

        public boolean removeBill(Bill bill) {
            return bills.remove(bill);
        }

        @Override
        public String marshal() {
            StringBuilder sb = new StringBuilder();
            for (Bill bill : bills) {
                sb.append(bill.marshal()).append("\n");
            }
            return sb.toString();
        }

        @Override
        public void unmarshal(String data) {
            String[] lines = data.split("\n");
            for (String line : lines) {

                if (line.trim().isEmpty()) continue;
                Bill bill = new Bill("", "", "", "", 0.0, null, null); // temporary empty object
                bill.unmarshal(line);
                addBill(bill);
            }
        }
    }

