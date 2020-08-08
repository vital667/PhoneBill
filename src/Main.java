import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String billFile = "Bill.txt";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        List<Bill> bills = new LinkedList<>();

        int shortCallPrice = 3;
        int longCallPrice = 150;
        int priceToPay = 0;

        try {

            br = new BufferedReader(new FileReader(billFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] phone = line.split(cvsSplitBy);

                if (phone[0].startsWith("„")) phone[0] = phone[0].substring(1);
                if (phone[1].endsWith("”")) phone[1] = phone[1].substring(0, phone[1].length() - 1);
                //System.out.println("time=" + phone[0] + ", phone=" + phone[1] + "]");
                bills.add(new Bill(phone[0], phone[1]));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //System.out.println(bills);

        Map<String, Integer> map = new HashMap<>();

        //calculate time in Seconds for every Phone Number and put data to map
        for (Bill i : bills)
            if (!map.containsKey(i.getPhone())) map.put(i.getPhone(), calculateTimeInSecunds(i.getTime()));
            else map.put(i.getPhone(), map.get(i.getPhone()) + calculateTimeInSecunds(i.getTime()));


        int max = 0;
        String temp = "";

        //find phone number with the biggest amount of seconds
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            //System.out.println("phone = " + entry.getKey() + ";   overall time = " + entry.getValue());
            if (entry.getValue() > max) {
                max = entry.getValue();
                temp = entry.getKey();
            }
        }

        //remove phone number with the biggest amount of seconds
        map.remove(temp);
        //System.out.println("Map to calculate:");

        //calculate PriceToPay
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            // System.out.println("phone = " + entry.getKey() + ";   overall time = " + entry.getValue());
            if (entry.getValue() > 300) priceToPay +=
                    entry.getValue() % 60 == 0
                            ? (entry.getValue() / 60) * longCallPrice
                            : (entry.getValue() / 60 + 1) * longCallPrice;
            else priceToPay += entry.getValue() * shortCallPrice;
        }

        System.out.println("You should pay: " + priceToPay / 100 + "$ " + (priceToPay % 100) + " cents");
    }


    public static int calculateTimeInSecunds(String s) {
        LocalTime time = LocalTime.parse(s);
        return time.getSecond() + time.getMinute() * 60 + time.getHour() * 60 * 60;
    }
}
