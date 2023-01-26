package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

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

} // end interface
