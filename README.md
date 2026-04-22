# Java Chat Application

A real-time, two-way desktop chat application built with **Java Socket Programming** and a **Swing GUI**. Demonstrates client-server architecture, TCP communication, and event-driven UI design.

---

# Demo

| Server (User 1)                        | Client (User 2) |
| Listens on port 6001                   | Connects to server on port 6001 |
| Sends & receives messages in real time | Sends & receives messages in real time |

Messages appear on the **right** for the sender and **left** for the receiver — mirroring standard chat application UX. Each message is timestamped using `SimpleDateFormat`.

---

##  Architecture

```
┌─────────────────────┐         TCP Socket (Port 6001)        ┌─────────────────────┐
│      Server.java    │ ◄────────────────────────────────────► │      Client.java    │
│   (User 1 / Host)   │    DataInputStream / DataOutputStream  │  (User 2 / Guest)   │
└─────────────────────┘                                        └─────────────────────┘
```

- **Transport Layer:** TCP via `java.net.ServerSocket` and `java.net.Socket`
- **Data Protocol:** UTF-8 encoded strings via `DataInputStream` / `DataOutputStream`
- **UI Layer:** Java Swing (`JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton`)
- **Event Handling:** `ActionListener` interface for send button interactions

---

## Features

- Real-time bidirectional messaging over TCP
- Timestamped messages (`HH:mm` format)
- Distinct left/right message alignment per sender
- Custom GUI with profile headers and active status indicator
- Lightweight — no external dependencies, pure Java SE

---

## Tech Stack

| Technology         | Usage |
| Java SE            | Core language |
| `java.net`         | TCP socket communication |
| `java.io`          | Data stream I/O |
| Java Swing / AWT   | Desktop GUI |
| `SimpleDateFormat` | Message timestamps |

---

## How to Run

### Prerequisites
- Java JDK 8 or above installed
- Terminal / Command Prompt

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/ShefaliSingh001/Chatting-Application-Java-.git
cd Chatting-Application-Java-
```

**2. Compile both files**
```bash
javac Server.java
javac Client.java
```

**3. Run the Server first** (opens User 1 window)
```bash
java Server
```

**4. Run the Client in a separate terminal** (opens User 2 window)
```bash
java Client
```

> Always start the Server before the Client. The client connects to `localhost:6001` by default.

---

## Project Structure

```
Chatting-Application-Java-/
├── Server.java       # Server-side socket logic + GUI (User 1)
├── Client.java       # Client-side socket logic + GUI (User 2)
├── icons/            # UI assets (profile pictures, logo, buttons)
└── README.md
```

---

## 🔍 Key Concepts Demonstrated

- **TCP Socket Programming** — establishing and managing persistent connections using `ServerSocket.accept()`
- **Bidirectional I/O Streams** — simultaneous read/write using `DataInputStream` and `DataOutputStream`
- **Event-Driven Programming** — UI interactions handled via `ActionListener` and `MouseAdapter`
- **Swing Layout Management** — dynamic message rendering using `BoxLayout` and `BorderLayout`
- **OOP Design** — encapsulated UI and networking logic within class constructors and methods

---

## 🔧 Known Limitations & Future Improvements

- Move socket I/O to a background thread to prevent UI blocking
- Support multiple clients via thread-per-connection model
- Add message encryption (e.g., TLS over SSL sockets)
- Replace hardcoded IP/port with a config file or CLI arguments
- Persist chat history to a local database (SQLite/H2)

---

## Author

**Shefali Singh**  
Master's in Information Technology — UNSW Sydney  
[LinkedIn](https://www.linkedin.com/in/shefalisingh8/) • [GitHub](https://github.com/ShefaliSingh001)
