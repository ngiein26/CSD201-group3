# Đào Cao Duy: Thuật toán Thêm mới & Rẽ nhánh (Visit & Branching)

## Mã giả `visit(newUrl, newTitle)`

```text
visit(newUrl, newTitle):
    newNode ← tạo Node mới với newUrl, newTitle, currentTime

    if head = null then
        head ← newNode
        tail ← newNode
        current ← newNode
        size ← 1
        return

    if current ≠ tail then
        current.next ← null
        tail ← current

    tail.next ← newNode
    newNode.prev ← tail
    tail ← newNode
    current ← newNode
    size ← size + 1
```

## Giải thích ngắn

- Nếu lịch sử rỗng thì node mới sẽ trở thành `head`, `tail` và `current`.
- Nếu người dùng đang ở giữa lịch sử thì xảy ra branching.
- Khi đó chỉ cần cắt liên kết `current.next ← null`, không cần lặp để xóa từng node phía sau.
- Sau đó nối node mới vào cuối danh sách và cập nhật lại `tail`, `current`.

## AI Reflection

AI thường gợi ý xóa từng node phía sau bằng một vòng lặp. Tuy nhiên, cách đó là dư thừa với Doubly Linked List. Nhóm phản biện rằng chỉ cần bẻ gãy liên kết `current.next = null` là toàn bộ nhánh lịch sử cũ phía sau bị cô lập ngay. Các node bị cô lập sẽ được garbage collector giải phóng tự động. Cách này giữ cho thao tác `visit()` khi branching đơn giản, đúng bản chất của cấu trúc dữ liệu, và không làm hệ thống rườm rà.