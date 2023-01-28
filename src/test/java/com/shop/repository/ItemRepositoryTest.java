package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

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

        /*
        테스트 코드 실행 시 DB에 상품 데이터가 없으므로
        테스트 데이터 생성을 위해 10개의 상품을 저장하는 메소드를 작성하여
        findByItemNmTest()에서 실행한다.
         */
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();

            item.setItemNm("테스트 상품 " + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명 " + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item saveItem = itemRepository.save(item);
        } // for
    } // createItemTest

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.createItemTest();

        /*
        상품명으로 상품을 검색하는 예제:
            ItemRepository 인터페이스에서 작성했던 findByItemNm 메소드 호출
            파라미터로 "테스트 상품 1"이라는 상품명 전달
         */

        List<Item> itemList = itemRepository.findByItemNm("테스트 상품 1");

        for (Item item : itemList) {
            System.out.println(item.toString()); // 조회 결과로 얻은 item 객체들을 출력
        } // enhanced for
    } // findByItemNmTest

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest() {
        // 테스트 상품을 만드는 메소드 실행, 조회할 대상 만들기용
        this.createItemTest();

        /*
        상품명이 "테스트 상품 1" 또는 상품 상세 설명이 "테스트 상품 상세 설명 5"이면
        해당 상품을 itemList에 할당한다.
         */
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품 1", "테스트 상품 상세 설명 5");

        for (Item item : itemList) {
            System.out.println(item.toString());
        } // enhanced for

    } // findByItemNmOrItemDetailTest

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        this.createItemTest();

        /*
        현재 DB에 저장된 가격은 10001~10010이다.
        테스트 코드 실행 시 10개의 상품을 저장하는 로그가 콘솔에 나타나고
        맨 마지막에 가격이 10005보다 작은 4개의 상품을 출력해주는 것을 확인 할 수 있다.
         */
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);

        for (Item item : itemList) {
            System.out.println(item.toString());
        } // enhanced for
    } // findByPriceLessThanTest

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc() {
        this.createItemTest();

        // 상품 가격이 10005미만인 상품을 가격을 기준으로 내림차순으로 정렬
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);

        for (Item item : itemList) {
            System.out.println(item.toString());
        } // enhanced for
    } // findByPriceLessThanOrderByPriceDesc

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        this.createItemTest();

        // 상품 상세 설명에 '테스트 상품 상세 설명'을 포함하고 있는 상품 데이터 10개가 가격이 높은 순부터 조회
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");

        for (Item item : itemList) {
            System.out.println(item.toString());
        } // enhanced for
    } // findByItemDetailTest

    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailByNative() {
        this.createItemTest();

        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");

        for (Item item : itemList) {
            System.out.println(item.toString());
        } // enhanced for
    } // findByItemDetailByNative
} // end class