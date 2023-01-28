package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
JpaRepository<엔티티 타입 클래스, 기본키 타입>
    JpaRepository는 기본적인 CRUD 및 페이징 처리를 위한 메소드가 정의되어 있다.
    예) 엔티티 저장, 수정, 삭제, 엔티티 개수 출력, 모든 엔티티 조회 등

쿼리메소드:
    스프링 데이터 JPA에서 제공하는 핵심 기능 중 하나로 Repository 인터페이스에 간단한 네이밍 룰을 이용하여
    메소드를 작성하면 원하는 쿼리를 실행할 수 있다.

쿼리 메소드를 이용할 때 가장 많이 사용하는 문법: find
    엔티티의 이름은 생략이 가능하며, By 뒤에 검색할 때 사용할 변수의 이름을 적어준다.
    예) find + (엔티티 이름) + By + 변수이름
 */

public interface ItemRepository extends JpaRepository<Item, Long> {

    /*
    itemNm(상품명)으로 데이터를 조회하기 위해 By 뒤에 필드명인 itemNm을 메소드의 이름에 붙인다.
    엔티티명은 생략 가능하므로 findByItemNm 대신에 findByItemNm으로 메소드명을 만들고
    매개변수로 검색할 때 사용할 상품명 변수를 넘겨준다.
     */
    List<Item> findByItemNm(String itemNm);

    // 상품을 상품명과 상품 상세 설명을 OR 조건을 이용하여 조회하는 쿼리 메소드
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    // 파라미터로 넘어온 price 변수보다 값이 작은 상품 데이터를 조회하는 쿼리 메소드
    List<Item> findByPriceLessThan(Integer price);

    /*
    OrderBy 키워드를 이용한다면 데이터의 순서를 오름차순 또는 내림차순으로 조회할 수 있다.
    오름차순의 경우 OrderBy + 속성명 + Asc 키워드
    내림차순의 경우 OrderBy + 속성명 + Desc 키워드

    아래는 상품의 가격이 높은 순으로 조회하는 쿼리 메소드
     */
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    /*
    Spring Data JPA에서 제공하는 @Query 어노테이션을 이용하면 SQL과 유사한 JPQL(Java Persistence Query Language)이라는
    객체지향 쿼리 언어를 통해 복잡한 쿼리도 처리가 가능하다(SQL과 문법 자체가 유사).
    SQL의 경우 DB의 테이블을 대상으로 쿼리를 수행하고, JPQL은 엔티티 객체를 대상으로 쿼리를 수행하는 객체지향 쿼리이다.
    JPQL로 작성했다면 DB가 변경되어도 애플리케이션이 영향을 받지 않는다.

    @Query 어노테이션을 이용하여 상품 데이터를 조회
    상품 상세 설명을 파라미터로 받아 해당 내용을 상품 상세 설명에 포함하고 있는 데이터를 조회하며,
    정렬 순서는 가격이 높은 순으로 조회한다.

    @Query 어노테이션 안에 JPQL로 작성한 쿼리문을 넣어주고 from 뒤에는 엔티티 클래스로 작성한 Item을 지정해준다.
    Item으로부터 데이터를 SELECT하겠다는 것을 의미한다.
     */
    @Query("SELECT i FROM Item i WHERE i.itemDetail LIKE %:itemDetail% ORDER BY i.price DESC")

    /*
    파라미터에 @Param 어노테이션을 이용하여 파라미터로 넘어온 값을 JPQL에 들어갈 변수로 지정해 줄 수 있고
    변수를 JPQL에 전달하는 대신 파라미터의 순서를 이용해 전달해줄 수도 있다.
    그럴 경우, ':itemDetail' 대신 첫 번째 파라미터를 전달하겠다는 '?1'이라는 표현을 사용하면 된다.
    하지만 파라미터의 순서로 달라지면 해당 쿼리문이 제대로 동작하지 않을 수 있기 때문에 좀 더 명시적인 방법인
    @Param 어노테이션을 이용하는 방법을 추천한다.

    현재는 itemDetail 변수를 "LIKE % %" 사이에 ":itemDetail"로 값이 들어가도록 작성
     */
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    /*
    복잡한 쿼리의 경우 @Query 어노테이션을 사용해서 조회
    만약, 기존의 DB에서 사용하던 쿼리를 그대로 사용해야 할 때는 @Query의 nativeQuery속성을 사용하면
    기존 쿼리를 그대로 활용할 수 있다.
    하지만 특정 DB에 종속되는 쿼리문을 사용하기 때문에 DB에 대해 독립적이라는 장점을 잃어버린다.
    기존에 작성한 통계용 쿼리처럼 복잡한 쿼리를 그대로 사용해야 하는 경우 활용할 수 있다.

    value 안에 네이티브 쿼리문을 작성하고 nativeQuery=true를 지정한다.
     */
    @Query(value = "SELECT * FROM item i WHERE i.item_detail LIKE %:itemDetail% ORDER BY i.price DESC", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);

} // end interface
