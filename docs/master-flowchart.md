# Master Flowchart - Browser History System

Dưới đây là flowchart tổng thể (Master Flowchart) kết hợp tất cả các chức năng của chương trình (`visit`, `back`, `forward`, `showHistory`, `searchByTitle`, `generateDummyData`) thành một luồng hoạt động thống nhất xuất phát từ Main Menu.

```mermaid
flowchart TD
    %% Khởi tạo
    Start([Bắt đầu chương trình]) --> Menu[Hiển thị Main Menu]
    
    Menu --> Input[/Nhập lựa chọn (0-6)/]
    Input --> Switch{Lựa chọn?}

    %% 1. Visit
    Switch -- "1. Thêm trang mới\n(Visit)" --> V1[/Nhập newUrl, newTitle/]
    V1 --> V2{History rỗng?}
    V2 -- Có --> V3[head = tail = current = newNode]
    V2 -- Không --> V4{current != tail?}
    V4 -- Có --> V5[Cắt nhánh tương lai\ncurrent.next.prev = null\ncurrent.next = null\ntail = current]
    V4 -- Không --> V6[Giữ nguyên chuỗi hiện tại]
    V3 --> V7
    V5 --> V7
    V6 --> V7[Nối newNode vào cuối\ntail = newNode\ncurrent = newNode]
    V7 --> EndAction((Xong))

    %% 2. Back
    Switch -- "2. Quay lại\n(Back)" --> B1{current == null\nhoặc current.prev == null?}
    B1 -- Có --> B2[Báo lỗi: Đang ở đầu lịch sử]
    B1 -- Không --> B3[current = current.prev\nforwardCount++]
    B2 --> EndAction
    B3 --> EndAction

    %% 3. Forward
    Switch -- "3. Tiến tới\n(Forward)" --> F1{current == null\nhoặc current.next == null?}
    F1 -- Có --> F2[Báo lỗi: Đang ở cuối lịch sử]
    F1 -- Không --> F3[current = current.next\nforwardCount--]
    F2 --> EndAction
    F3 --> EndAction

    %% 4. Show History
    Switch -- "4. Xem lịch sử\n(Show History)" --> H1{History rỗng?}
    H1 -- Có --> H2[In thông báo: Lịch sử trống]
    H1 -- Không --> H3[Duyệt temp = head đến tail]
    H3 --> H4[In ra temp.url, temp.title\nĐánh dấu [*] tại node current]
    H2 --> EndAction
    H4 --> EndAction

    %% 5. Search
    Switch -- "5. Tìm kiếm\n(Search By Title)" --> S1[/Nhập keyword/]
    S1 --> S2{keyword rỗng hoặc\nhistory rỗng?}
    S2 -- Có --> S3[Trả về danh sách rỗng]
    S2 -- Không --> S4[Duyệt temp = head đến tail]
    S4 --> S5{temp.title chứa\nkeyword?}
    S5 -- Có --> S6[Thêm temp vào\ndanh sách kết quả]
    S5 -- Không --> S7[Tiếp tục duyệt node kế]
    S6 --> S7
    S7 -. Lặp cho đến tail .-> S4
    S7 -- Hết danh sách --> S8[In ra danh sách kết quả]
    S3 --> EndAction
    S8 --> EndAction

    %% 6. Generate Dummy
    Switch -- "6. Tạo dữ liệu mẫu\n(Generate Dummy)" --> G1[/Nhập số lượng n/]
    G1 --> G2{n > 0?}
    G2 -- Không --> G3[Báo lỗi: n không hợp lệ]
    G2 -- Có --> G4[Lặp i từ 0 đến n-1]
    G4 --> G5[Tạo ngẫu nhiên url, title\ntính toán timestamp]
    G5 --> G6[Gọi hàm visit(url, title)]
    G6 -. Lặp lại cho các node .-> G4
    G4 -- Hết n vòng lặp --> G7[Thông báo thành công]
    G3 --> EndAction
    G7 --> EndAction

    %% 0. Exit
    Switch -- "0. Thoát\n(Exit)" --> Exit([Kết thúc chương trình])
    
    %% Quay lại Menu
    EndAction -. Quay lại .-> Menu

    %% Styling
    classDef menu fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#000;
    classDef action fill:#fff3e0,stroke:#e65100,stroke-width:1px,color:#000;
    classDef endnode fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#000;
    
    class Menu menu;
    class V7,B3,F3,H4,S8,G7 action;
    class EndAction endnode;
```
