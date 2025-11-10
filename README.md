# Online Bookstore Management System

This is a comprehensive database application system for an online bookstore, developed using **Java**, **JavaFX**, and **openGauss**. The system supports full CRUD (Create, Read, Update, Delete) operations for modules including books, orders, customers, suppliers, and more.

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
- **Database:** openGauss 5.x or compatible
- **Libraries Required:**
  - openGauss JDBC Driver
  - JavaFX SDK

------

### 2. openGauss Configuration

#### ✅ Install openGauss:

Download the community edition from the [openGauss official site](https://opengauss.org/en/download/) and follow the installation guide for your operating system.

#### ✅ Create Database Role & Schema:

During installation you can create a dedicated role (for example `gaussdb`) and set a secure password.

Use the SQL statements provided in the file `数据库初始化需要的sql语句.txt` to create the database and its tables. Execute them with the `gsql` client or any PostgreSQL-compatible GUI.

#### ✅ Configure Database Connection in Code:

Open the file `DatabaseUtil.java` and update the following lines as needed:

```java
private static final String DB_URL = "jdbc:opengauss://localhost:5432/BookStoreDB"; // Database URL
private static final String DB_USER = "gaussdb"; // Database username
private static final String DB_PASSWORD = "your_password"; // Database password
```

> 🔁 If your openGauss instance is running on a non-default host or port, adjust `localhost:5432` accordingly.
>  🔐 Replace `"your_password"` with the password assigned to the role you created above.

------

### 3. JDBC Driver Setup

1. Download the latest **openGauss JDBC Driver** from the [openGauss release page](https://opengauss.org/en/download/).
2. Unzip the archive and locate the `opengauss-jdbc-x.x.x.jar` (or similarly named) file.
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

#### ✅ openGauss

- Add the openGauss `bin` directory (where `gsql` resides) to your system `Path`, for example:

  ```
  C:\openGauss\bin
  ```

------

## ⚠️ Notes & Troubleshooting

- Ensure **JDK**, **openGauss**, **JDBC Driver**, and **JavaFX SDK** versions are compatible.
- Keep libraries up to date to prevent runtime issues.
- If the GUI doesn’t display properly, double-check your JavaFX configuration and VM arguments in your launch settings.

------

## ✅ Feature Summary

- 📚 Book management (add, delete, update, search)
- 🛒 Order processing
- 👤 Customer management
- 🚚 Supplier records
- 🔍 openGauss-based persistent data storage
- 💻 JavaFX graphical user interface

------

Happy coding and enjoy building your bookstore system! 🎉
 If you have any questions, feel free to open an issue or contribute to this repository.
