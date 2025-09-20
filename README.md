# 貨幣比較系統 (Currency Comparison System)

## 專案簡介

這是一個基於 Spring Boot 開發的貨幣比較系統，主要功能包括：
- 從 CoinDesk API 獲取即時比特幣價格
- 管理貨幣比較資料（增刪改查）
- 提供 RESTful API 接口
- 支援多幣別價格顯示與中文名稱對應

## 技術棧

- **Java**: 17
- **Spring Boot**: 3.5.5
- **Spring Data JPA**: 資料持久化
- **H2 Database**: 內存資料庫
- **Lombok**: 減少冗餘代碼
- **Apache HttpClient**: HTTP 客戶端
- **Maven**: 專案管理工具

## 專案結構

```
src/main/java/com/example/demo/
├── Demo1Application.java              # 主啟動類
├── controller/                        # 控制器層
│   ├── CurrencyComparisonController.java  # 貨幣比較控制器
│   ├── CommonResponse.java               # 統一回應格式
│   ├── ResponseEnum.java                 # 回應狀態枚舉
│   ├── request/                          # 請求物件
│   │   └── CurrencyComparisonUpdateRequest.java
│   └── response/                         # 回應物件
│       ├── CurrencyComparisonResponse.java
│       ├── CurrencyPriceApiResponse.java
│       └── CurrencyPriceResponse.java
├── database/                          # 資料庫層
│   ├── CurrencyComparisonEntity.java     # 貨幣比較實體
│   └── CurrencyComparisonRepository.java # 資料庫存取介面
├── service/                           # 服務層
│   ├── CurrencyComparisonService.java    # 貨幣比較服務
│   ├── CurrencyPriceService.java         # 貨幣價格服務
│   ├── DemoMockingData.java              # 模擬資料
│   ├── cache/                            # 緩存服務
│   │   └── CurrencyComparisonCacheService.java
│   ├── exception/                        # 異常處理
│   │   └── BusinessException.java
│   └── handler/                          # 全局異常處理器
│       └── GlobalExceptionHandler.java
```

## 功能特色

### 1. 即時貨幣價格查詢
- 整合 CoinDesk API 獲取比特幣即時價格
- 支援 USD、GBP、EUR 等多種貨幣
- 提供更新時間資訊
- 具備容錯機制，API 失敗時使用模擬資料

### 2. 貨幣比較資料管理
- **新增貨幣**: 加入新的貨幣比較資料
- **查詢貨幣**: 獲取所有貨幣比較列表
- **更新貨幣**: 修改現有貨幣資訊
- **刪除貨幣**: 移除貨幣比較資料
- **重複檢查**: 防止新增重複的貨幣代碼

### 3. 資料映射與本地化
- 將英文貨幣描述映射為中文名稱
- 支援自定義貨幣符號
- 提供友善的中文介面

## API 接口

### 貨幣價格查詢
```http
GET /api/currency/comparison
```
**回應範例**:
```json
{
  "status": true,
  "code": 200,
  "msg": "成功",
  "data": {
    "time": "2024/09/20 14:30:00",
    "bpis": [
      {
        "code": "USD",
        "symbol": "$",
        "rate": 63251.25,
        "description": "美元"
      }
    ]
  }
}
```

### 貨幣比較管理

#### 查詢所有貨幣比較
```http
GET /api/currency/getComparisons
```

#### 新增貨幣比較
```http
POST /api/currency/addComparison
Content-Type: application/json

{
  "currency": "TWD",
  "symbol": "NT$",
  "chinaName": "台幣"
}
```

#### 更新貨幣比較
```http
PUT /api/currency/updateComparison
Content-Type: application/json

{
  "id": 1,
  "currency": "TWD",
  "symbol": "NT$",
  "chinaName": "新台幣"
}
```

#### 刪除貨幣比較
```http
DELETE /api/currency/delComparison?id=1
```

## 快速開始

### 環境要求
- Java 17 或以上版本
- Maven 3.6 或以上版本

### 啟動步驟

1. **複製專案**
```bash
git clone <repository-url>
cd demo1
```

2. **編譯專案**
```bash
mvn clean compile
```

3. **執行測試**
```bash
mvn test
```

4. **啟動應用程式**
```bash
mvn spring-boot:run
```

5. **驗證啟動**
   - 應用程式將在 http://localhost:8080 啟動
   - H2 資料庫控制台: http://localhost:8080/h2-console
   - 資料庫連線資訊：
     - JDBC URL: `jdbc:h2:mem:testdb`
     - 使用者名稱: `sa`
     - 密碼: (空白)

## 資料庫設計

### CurrencyComparison 表格結構

| 欄位名稱 | 資料型別 | 說明 | 約束 |
|---------|---------|------|------|
| id | BIGINT | 主鍵 | AUTO_INCREMENT, PRIMARY KEY |
| currency | VARCHAR | 貨幣代碼 | NOT NULL, INDEX |
| symbol | VARCHAR | 貨幣符號 | |
| china_name | VARCHAR | 中文名稱 | |

## 異常處理

系統實作了完整的異常處理機制：

- **BusinessException**: 業務邏輯異常
- **MethodArgumentNotValidException**: 參數驗證異常
- **BindException**: 參數綁定異常
- **Exception**: 系統異常

所有異常都會回傳統一格式的錯誤回應。

## 快取機制

專案預留了快取擴展點：
- `CurrencyComparisonCacheService` 類別中標記了 TODO 項目
- 可以輕鬆整合 Redis 或其他快取解決方案

## 測試

專案包含了完整的單元測試：
- `CurrencyComparisonServiceTest`: 貨幣比較服務測試
- `CurrencyPriceServiceTest`: 貨幣價格服務測試

執行測試：
```bash
mvn test
```

## 注意事項

1. **網路連線**: 系統需要網路連線以存取 CoinDesk API
2. **資料持久化**: 使用 H2 內存資料庫，重啟後資料會消失
3. **生產環境**: 建議更換為 MySQL 或 PostgreSQL 等持久化資料庫

## 未來擴展

- [ ] 整合 Redis 快取
- [ ] 支援更多外部 API
- [ ] 新增前端界面
- [ ] 實作資料庫遷移
- [ ] 新增 API 文檔（Swagger）
- [ ] 容器化部署（Docker）

## 聯絡資訊

如有任何問題或建議，請聯絡開發團隊。
