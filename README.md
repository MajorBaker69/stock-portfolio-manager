# 📈 Stock Portfolio Manager

A menu-driven command-line brokerage application built in Java that simulates real stock portfolio management — including cash deposits, withdrawals, stock purchases, sales, transaction history, and live portfolio display.

---

## 🗂️ Project Overview

This project was built as part of my Computer Science coursework at **Arizona State University**, applying core Object-Oriented Programming principles in Java. The application models a simplified brokerage account and demonstrates clean architecture, input validation, and data management using Java's standard library.

---

## ✨ Features

- 💵 **Deposit & Withdraw Cash** — Manage your account balance with validated transactions
- 📊 **Buy & Sell Stocks** — Purchase and liquidate stock positions with automatic cash balance updates
- 🧾 **Transaction History** — View a formatted ledger of all account activity
- 📁 **Portfolio Display** — See current holdings with live timestamps using `LocalDateTime`
- ✅ **Input Validation** — Every user input is validated including date format (MM/DD/YYYY), leap year detection, numeric ranges, and cash/share balance checks
- 🛡️ **Error Handling** — Graceful handling of invalid menu selections, insufficient funds, and oversell attempts

---

## 🏗️ Architecture

The application is built with two classes following the **Single Responsibility Principle**:

| Class | Responsibility |
|---|---|
| `PortfolioManager.java` | Application engine — menu, user input, business logic, display |
| `TransactionHistory.java` | Data model — stores individual transaction records with getters/setters |

### Data Structures Used
- `ArrayList<TransactionHistory>` — stores the complete transaction ledger
- `LinkedHashMap<String, Double>` — maintains insertion-ordered portfolio holdings
- `LocalDateTime` — timestamps portfolio snapshots

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 11 or higher
- Any Java IDE (VS Code with Java Extension Pack, IntelliJ IDEA, Eclipse)

### Run the Application

```bash
# Clone the repository
git clone https://github.com/ryanbaker18/stock-portfolio-manager.git

# Navigate to the project directory
cd stock-portfolio-manager

# Compile
javac src/PortfolioManager.java src/TransactionHistory.java -d bin

# Run
java -cp bin PortfolioManager
```

---

## 🖥️ Application Menu

```
══════════════════════════════════════════════════════════════════════
                    Ryan Baker Brokerage Account
══════════════════════════════════════════════════════════════════════

  0 - Exit
  1 - Deposit Cash
  2 - Withdraw Cash
  3 - Buy Stock
  4 - Sell Stock
  5 - Display Transaction History
  6 - Display Portfolio

Enter option (0 to 6):
```

---

## 📋 Sample Workflow

```
1. Deposit $10,000 cash
2. Buy 10 shares of AAPL @ $175.00  → Auto-deducts $1,750.00 from cash
3. Buy 5 shares of NVDA @ $450.00   → Auto-deducts $2,250.00 from cash
4. Sell 5 shares of AAPL @ $190.00  → Auto-deposits $950.00 to cash
5. View Transaction History          → Full formatted ledger
6. View Portfolio                    → Current holdings with timestamp
```

---

## 🔍 Input Validation Highlights

The `getValidatedDate()` method enforces strict date validation:
- Format enforcement using regex: `MM/DD/YYYY`
- Month range validation (01–12)
- Day range validation per month (e.g., February max 28 or 29)
- Leap year detection via `isLeapYear()` method
- Year range enforcement (1900–2100)

---

## 🔗 Cybersecurity Relevance

This project reinforced concepts directly applicable to cybersecurity:
- **Transaction ledger design** mirrors log management in SIEM systems
- **Input validation** prevents injection-style attacks at the data entry layer
- **Balance verification** mirrors integrity checks used in financial fraud detection

---

## 👤 Author

**Ryan Baker**
- 🎓 B.S. Information Technology — Cybersecurity Concentration, Arizona State University
- 🪖 U.S. Marine Corps Veteran
- 🔗 [LinkedIn](https://www.linkedin.com/in/ryanbaker18)
- 🐙 [GitHub](https://github.com/majorbaker69)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
