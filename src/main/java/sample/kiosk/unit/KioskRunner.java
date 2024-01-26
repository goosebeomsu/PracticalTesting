package sample.kiosk.unit;

import sample.kiosk.unit.beverage.Americano;
import sample.kiosk.unit.beverage.Latte;

public class KioskRunner {

    public static void main(String[] args) {
        Kiosk kiosk = new Kiosk();
        kiosk.add(new Americano());
        System.out.println("아메리카노 추가");

        kiosk.add(new Latte());
        System.out.println("라떼 추가");

        int totalPrice = kiosk.calculateTotalPrice();
        System.out.println("총 주문가격:" + totalPrice);
    }
}
