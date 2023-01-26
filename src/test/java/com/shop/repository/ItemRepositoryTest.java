package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

/*
@SpringBootTest:
    통합 테스트를 위해 스트링 부트에서 제공하는 어노테이션으로
    실제 애플리케이션을 구동할 때처럼 모든 Bean을 IoC 컨테이너에 등록한다.
    (애플리케이션 구모가 크면 속도가 느려질 수 있다.)

@TestPropertySource(locations = "classpath:application-test.properties"):
    테스트 코드 실행 시 application.properties와 application-test.properties에 같은 설정이 있다면
    test에 더 높은 우선순위를 부여하므로 테스트 코드 실행 시에는 H2 DB를 사용하게 된다.

테스트 코드가 실행되면 hibernate_sequence라는 키 생성 전용 테이블로부터
저장할 상품의 기본키(PK)를 가져와서 item 테이블의 기본키 값으로 넣어준다.

Spring Data JPA는 인터페이스만 작성하면 런타임 시점에 자바의 Dynamic Proxy를 이용해서 객체를 동적으로 생성해주므로
따로 DAO(Data Access Object)와 xml 파일에 쿼리문을 작성하지 않아도 된다.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository; // ItemRepository를 사용하기 위해 @Autowired 어노테이션을 이용해서 Bean 주입

    @Test // 해당 메소드를 테스트 대상으로 지정
    @DisplayName("상품 저장 테스트") // Junit5에 추가된 어노테이션, 테스트 코드 실행 시 지정한 테스트명 노출
    public void createItemTest() {
        Item item = new Item();

        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item saveItem = itemRepository.save(item);
        System.out.println(saveItem.toString());

    } // createItemTest

} // end class