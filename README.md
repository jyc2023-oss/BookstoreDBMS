# Online Bookstore Management System

This is a comprehensive database application system for an online bookstore, developed using **Java**, **JavaFX**, and **MySQL**. The system supports full CRUD (Create, Read, Update, Delete) operations for modules including books, orders, customers, suppliers, and more.

------

## 📁 Project Structure

The workspace includes the following folders:

- `src/`: The folder to maintain Java source code
- `lib/`: The folder to maintain external dependencies (e.g., JDBC driver, JavaFX libraries)
- `bin/`: The default folder where compiled `.class` files are generated

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings.

------

## 🛠️ Getting Started with VS Code (Java)

Welcome to the VS Code Java world. Here is a guideline to help you get started writing Java code in Visual Studio Code.

- Use the `JAVA PROJECTS` panel in VS Code to manage dependencies.
- More info available [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

------

## ⚙️ Environment Setup

### 1. System Requirements

- **Operating System:** Windows / macOS / Linux
- **Java:** JDK 11 or higher
- **Database:** MySQL 8.x
- **Libraries Required:**
  - MySQL Connector/J (JDBC Driver)
  - JavaFX SDK

------

### 2. MySQL Configuration

#### ✅ Download MySQL:

Go to [MySQL Community Downloads](https://dev.mysql.com/downloads/mysql/) to download MySQL Community Server.

#### ✅ Set Root Password:

During installation, set the `root` user password (e.g., `123456`).

#### ✅ Create Database:

Use the SQL statements provided in the file `数据库初始化需要的sql语句.txt` to create the database and its tables. Just copy and run the content in a MySQL client like MySQL Workbench.

#### ✅ Configure Database Connection in Code:

Open the file `DatabaseUtil.java` and update the following lines as needed:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/BookStoreDB"; // Database URL
private static final String DB_USER = "root"; // Database username
private static final String DB_PASSWORD = "123456"; // Database password
```

> 🔁 If your MySQL is running on a non-default port, change `localhost:3306` to the correct one.
>  🔐 Replace `"123456"` with your actual MySQL password.

------

### 3. JDBC Driver Setup

1. Download the latest **MySQL Connector/J** from [here](https://dev.mysql.com/downloads/connector/j/).
2. Unzip the archive and locate the `mysql-connector-java-x.x.x.jar` file.
3. Place the `.jar` file in your project’s `lib/` folder or add it via your IDE's library manager.

------

### 4. JavaFX Configuration

1. Download **JavaFX SDK** from [GluonHQ JavaFX Downloads](https://gluonhq.com/products/javafx/).
2. Extract the downloaded SDK.
3. Add all `.jar` files from the SDK's `lib/` directory to your project’s `lib/` folder or project libraries.
4. Make sure to configure the launch settings:
   - `launch.json`
   - `settings.json`

These configuration files should specify the JavaFX runtime modules and paths.

------

### 5. Environment Variables

#### ✅ Java JDK

- Set `JAVA_HOME` to your JDK path, for example:

  ```
  JAVA_HOME=C:\Program Files\Java\jdk-17
  ```

- Add `%JAVA_HOME%\bin` to the system `Path`.

#### ✅ MySQL

- Add MySQL's bin directory to your system `Path`, for example:

  ```
  C:\Program Files\MySQL\MySQL Server 8.0\bin
  ```

------

## ⚠️ Notes & Troubleshooting

- Ensure **JDK**, **MySQL**, **JDBC Driver**, and **JavaFX SDK** versions are compatible.
- Keep libraries up to date to prevent runtime issues.
- If the GUI doesn’t display properly, double-check your JavaFX configuration and VM arguments in your launch settings.

------

## ✅ Feature Summary

- 📚 Book management (add, delete, update, search)
- 🛒 Order processing
- 👤 Customer management
- 🚚 Supplier records
- 🔍 MySQL-based persistent data storage
- 💻 JavaFX graphical user interface

------

Happy coding and enjoy building your bookstore system! 🎉
 If you have any questions, feel free to open an issue or contribute to this repository.
