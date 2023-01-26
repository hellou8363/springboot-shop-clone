package com.shop.entity;

/*
DB의 테이블에 대응하는 클래스
@Entity가 붙은 클래스는 JPA에서 관리하며 엔티티라고 부름
상품 엔티티를 만들기 위해상품 테이블에 어떤 데이터가 저장되어야 할지 설계
 */

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
@Entity: Item 클래스를 entity로 선언
@Table(name = value): 어떤 테이블(value)과 매핑될지 지정
 */
@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item {

    /*
    @Id: 멤버 변수를 기본키로 지정
    @Column(name = value): 매핑될 컬럼의 이름 설정
    @GeneratedValue(strategy = GenerationType.AUTO): 기본키 생성 전략
     */
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 상품 코드

    /*
    nullable: 항상 값이 있어야 하는 필드로 설정
    length: 255가 default, 필요한 길이 설정
     */
    @Column(nullable = false, length = 50)
    private String itemNm; // 상품명

    @Column(name = "price", nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int stockNumber; // 재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    /*
    판매 상태의 경우 재고가 없거나,
    상품을 미리 등록해 놓고 나중에 '판매 중' 상태로 바꾸거나
    재고가 없을 때 프론트에 노출시키지 않기 위해 판매 상태 코드를 갖고 있음
     */
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    private LocalDateTime regTime; // 등록 시간

    private LocalDateTime updateTime; // 수정 시간
} // end class
