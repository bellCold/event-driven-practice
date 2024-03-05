package event.orderservice.domain.order

enum class OrderStatus(val description: String) {
    ORDER_PLACED("주문 접수됨"),
    PROCESSING("처리 중"),
    SHIPPED("배송 중"),
    DELIVERED("배송 완료"),
    CANCELLED("주문 취소됨"),
    REFUND_PROCESSING("환불 처리 중");
}