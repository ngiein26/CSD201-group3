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