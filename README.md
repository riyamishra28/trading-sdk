# Trading SDK - Wrapper API for Trading Platforms

A production-ready Spring Boot application simulating core trading workflows including order management, trade execution, and portfolio tracking.

## 🚀 Tech Stack

- **Backend**: Java 25 + Spring Boot 3.5.9
- **Database**: H2 (In-Memory)
- **Build Tool**: Maven
- **API Documentation**: Postman
- **Validation**: Bean Validation (Hibernate Validator)
- **Logging**: SLF4J + Logback

## 📦 Features

- ✅ View available financial instruments (Equity, Futures, Options, Commodities)
- ✅ Place BUY/SELL orders (MARKET/LIMIT)
- ✅ Real-time order status tracking
- ✅ Automated trade execution for MARKET orders
- ✅ Portfolio management with average price calculation
- ✅ Trade history tracking
- ✅ Centralized exception handling
- ✅ Input validation
- ✅ RESTful API design

## 🛠️ Prerequisites

- Java 25
- Maven 3.5.9
- IntelliJ

## 🔄 Order Execution Flow
### MARKET Orders:
- Order placed → Executes immediately at current price → Creates trade → Updates portfolio
### LIMIT Orders: 
- Order placed → Status remains PLACED (manual execution required)

## 🎯 Key Assumptions
- Single user (no authentication)
- MARKET orders execute immediately
- LIMIT orders placed but not auto-executed
- In-memory database (data lost on restart)
- No short selling (SELL requires holdings)

## 📚 API Testing

### Using Postman

Import the provided Postman collection (`Trading-SDK.postman_collection.json`) or create requests manually.

**Base URL**: `http://localhost:8080`

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/instruments` | Get all available instruments |
| POST | `/api/v1/orders` | Place a new order |
| GET | `/api/v1/orders/{id}` | Get order status by ID |
| GET | `/api/v1/trades` | Get all executed trades |
| GET | `/api/v1/portfolio` | Get portfolio holdings |


## 🔐 HTTP Status Codes
- 200 - Success 
- 201 - Order Created
- 400 - Validation Error
- 404 - Resource Not Found
- 500 - Server Error
