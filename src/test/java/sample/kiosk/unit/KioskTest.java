package sample.kiosk.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.kiosk.unit.beverage.Americano;
import sample.kiosk.unit.beverage.Latte;
import sample.kiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class KioskTest {


    @Test
    void add() {

        // given
        Kiosk kiosk = new Kiosk();
        Americano americano = new Americano();

        // when
        kiosk.add(americano);

        // then
        int resultSize = kiosk.getBeverages().size();
        assertThat(resultSize).isEqualTo(1);

        String beverageName = kiosk.getBeverages().get(0).getName();
        assertThat(beverageName).isEqualTo("아메리카노");
    }

    @Test
    void addSeveralBeverages() {

        // given
        Kiosk kiosk = new Kiosk();
        Americano americano = new Americano();

        // when
        kiosk.add(americano, 2);

        // then
        assertThat(kiosk.getBeverages().size()).isEqualTo(2);

        assertThat(kiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(kiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @Test
    void addZeroBeverages() {

        Kiosk kiosk = new Kiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> kiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void remove() {
        // given
        Kiosk kiosk = new Kiosk();
        Americano americano = new Americano();
        kiosk.add(americano);

        // when
        kiosk.remove(americano);

        // then
        assertThat(kiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear() {
        Kiosk kiosk = new Kiosk();
        kiosk.add(new Americano());
        kiosk.add(new Latte());
        assertThat(kiosk.getBeverages()).hasSize(2);

        kiosk.clear();

        assertThat(kiosk.getBeverages()).isEmpty();
    }

    @Test
    void createOrderWithCurrentTime() {

        Kiosk kiosk = new Kiosk();
        Americano americano = new Americano();
        kiosk.add(americano);

        Order order = kiosk.createOrder(LocalDateTime.of(2024, 1, 19, 10, 0));

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0)).isEqualTo(americano);
    }

    @Test
    void createOrderAtNotOpenTime() {

        Kiosk kiosk = new Kiosk();
        Americano americano = new Americano();
        kiosk.add(americano);

        assertThatThrownBy(() -> {
            LocalDateTime nonOpenTime = LocalDateTime.of(2024, 1, 19, 9, 59);
            kiosk.createOrder(nonOpenTime);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문시간이 아닙니다. 관리자에게 문의해주세요");

    }

    @Test
    @DisplayName("주문목록에 담긴 음료들의 총 금액을 계산할 수 있다.")
    void calculateTotalPrice() {

        // given
        Kiosk kiosk = new Kiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        kiosk.add(americano);
        kiosk.add(latte);

        // when
        int totalPrice = kiosk.calculateTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(8500);
    }




}