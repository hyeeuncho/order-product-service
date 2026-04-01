### 주문 목록 조회
**구현 내용**
- 페이지네이션 방식으로 주문 목록 조회 API 구현
- 응답 데이터에 상품 이름 포함
- N+1 문제 해결

**N+1 해결 방법**

fetch join으로 orders와 product를 단일 쿼리로 조회하고, 페이지네이션 시 count 쿼리 비효율을 방지하기 위해 countQuery를 별도로 분리하여 N+1 문제를 해결했다.

<img width="2048" height="1193" alt="image" src="https://github.com/user-attachments/assets/c29b80ea-4078-42e6-83f8-c2e6bfe3cfbb" />

```sql
Hibernate: 
    select
        o1_0.id,
        o1_0.created_at,
        o1_0.ordered_price,
        o1_0.product_id,
        p1_0.id,
        p1_0.deleted,
        p1_0.description,
        p1_0.name,
        p1_0.price 
    from
        orders o1_0 
    join
        product p1_0 
            on p1_0.id=o1_0.product_id 
    order by
        o1_0.created_at desc 
    limit
        ?
```
fetch join으로 orders와 product를 단일 쿼리로 조회하고, 페이지네이션 시 count 쿼리 비효율을 방지하기 위해 countQuery를 별도로 분리하여 N+1 문제를 해결했다.

### 상품 재고 차감
구현 내용
- 상품에 stock 필드 추가
- 주문 생성 시 수량만큼 재고 차감
- 재고가 0일 때 주문 생성 불가

<img width="500" height="798" alt="image" src="https://github.com/user-attachments/assets/7442ca6d-bfb5-4ed9-8ba7-5d60acaab519" />

재고가 1인 메로나 등록

<img width="500" height="938" alt="image" src="https://github.com/user-attachments/assets/c7123208-060d-4431-a1ee-4e728c3ad7c3" />

첫 주문 성공

<img width="500" height="1426" alt="image" src="https://github.com/user-attachments/assets/56e453a2-cc22-435b-b233-465e8aba52e4" />

한번 더 주문하면 재고가 0이라 주문 실패

**동시성 처리 - 원자적 UPDATE 방식 선택**

동시에 여러 요청이 들어왔을 때 재고가 정확히 차감되도록 동시성 처리가 필요했다. 낙관적 락, 비관적 락, 원자적 UPDATE 세 가지 방식을 비교했다.

|  | 낙관적 락 | 비관적 락 | 원자적 UPDATE |
| --- | --- | --- | --- |
| 방식 | 커밋 시점 충돌 감지 | 조회 시점 행 잠금 | 쿼리 1번으로 처리 |
| 충돌 많을 때 | 재시도 반복 → 비효율 | 대기 후 순서 처리 | 안정적 |
| 성능 | 충돌 적을 때 빠름 | 대기 발생 | 빠름 |
| 구현 복잡도 | 재시도 로직 필요 | 단순 | 가장 단순 |

락 없이 쿼리 자체에서 조회와 차감을 한 번에 처리하는 원자적 UPDATE 방식을 선택했다. 락을 걸지 않아 성능이 좋고, 쿼리 한 번으로 차감과 재고 확인이 동시에 처리된다.
