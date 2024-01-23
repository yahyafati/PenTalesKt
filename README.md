# Reading Realm

> A Community Hub for Bookworms to Share and Discover Stories.

ReadingRealm is a community hub for bookworms to share and discover stories. It is a platform where users can share
their favorite books, write reviews, and discover new books to read. The platform also allows users to create reading
lists and share them with others. Users can also follow other users to see what they are reading and what they are up
to.

## Table of Contents

1. [Introduction](#reading-realm)
2. [Table of Contents](#table-of-contents)
3. [Prerequisites](#prerequisites)
    1. [Common Requirements](#common-requirements)
    2. [Docker Only Requirements](#docker-only-requirements)
    3. [Local Environment Only Requirements](#local-environment-only-requirements)
4. [Project Setup](#project-setup)
5. [Configuration](#configuration)
    1. [Database Configuration](#database-configuration)
        1. [Using pgAdmin](#using-pgadmin)
        2. [Using Terminal](#using-terminal)
    2. [Application Configuration](#application-configuration)
        1. [Docker](#docker)
        2. [Local Environment](#local-environment)
6. [Usage](#usage)
7. [License](#license)
8. [Team](#team)
9. [Demo](#demo)

## Prerequisites

There are two ways to run the application:

1. Using Docker _(Recommended)_
2. Using Local Environment

### Common Requirements

1. **Integrated Development Environment (IDE):**
    - Use an IDE that supports Kotlin development.
    - **IntelliJ IDEA** is the **highly** recommended IDE for Kotlin development.
    - Other IDEs that support Kotlin development include **Eclipse**, **NetBeans**, and **Visual Studio Code**.

   Download IntelliJ IDEA: [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)

2. **(Optional) Git:**
    - While not mandatory, using Git for version control is highly recommended, especially if you plan to contribute to
      the project or collaborate with others.

   Download Git: [https://git-scm.com/](https://git-scm.com/)

### Docker Only Requirements

If you want to run the application using Docker, you need to have the following software and tools installed on your
development environment:

1. **Docker:**
    - This project uses **Docker** to run the application.
    - Install Docker from [https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/).

### Local Environment Only Requirements

If you want to run the application using your **local** environment, you need to have the following software and tools
installed on your development environment:

1. **Java Development Kit (JDK):**
    - Spring Boot applications require the Java Development Kit (JDK).
    - If you are using **IntelliJ IDEA**, you can also install the JDK from the IDE.
    - You can install a compatible version of the JDK (preferably JDK 17 or later)
      from [https://www.oracle.com/java/](https://www.oracle.com/java/).
    - If you prefer to install using terminal, you can use the following commands:

        ```bash
        # For Ubuntu:
        sudo apt install openjdk-17-jdk
       
        # For Fedora:
        sudo dnf install java-latest-openjdk-devel
       
        # For Arch Linux:
        sudo pacman -S jdk-openjdk
       
        # For macOS:
        brew install openjdk@17
       
        # For Windows:
        choco install openjdk17
        ```
        * For Windows, make sure Chocolatey is installed. If not, you can install it from
          [https://chocolatey.org/install](https://chocolatey.org/install).
    - Once you have installed the JDK, you can verify the installation by running the following command in your
      terminal:

      ```bash
      # To check if Java is installed, run the following command in your terminal:
      java -version
      ```

2. **Kotlin Programming Language:**
    - Kotlin is the programming language used for this Spring Boot application. You can include Kotlin support in your
      project by using the Kotlin plugin for your preferred IDE or by configuring it in your build tool.
    - **Note:** If you are using **IntelliJ IDEA**, the Kotlin plugin is already included by default. That means you
      don't need to install it separately.

   ```bash
   # To check if Kotlin is installed, run the following command in your terminal:
   kotlin -version
   ```

3. **Gradle:**
    - This project uses either **Gradle** as a build tool.
    - Install Gradle from [https://gradle.org/install/](https://gradle.org/install/).
    - **Note:** If you are using **IntelliJ IDEA**, Gradle is already included by default. That means you don't need
      to
      install it separately.
    - If you prefer installing using the terminal you can run the following command:

      ```bash
      # For Ubuntu:
      sudo apt install gradle
        
      # For Fedora:
      sudo dnf install gradle
        
      # For Arch Linux:
      sudo pacman -S gradle
        
      # For macOS:
      brew install gradle
        
      # For Windows:
      choco install gradle
      ```

    - Once you have installed Gradle, you can verify the installation by running the following command in your
      terminal:

      ```bash
      # To check if Gradle is installed, run the following command in your terminal:
      gradle -v
      ```

4. **PostgreSQL**
    - This project uses **PostgreSQL** as the database.
    - Install PostgreSQL from [https://www.postgresql.org/download/](https://www.postgresql.org/download/).

5. **(Optional) pgAdmin**
    - This project uses **pgAdmin** as the database management tool.
    - Install pgAdmin from [https://www.pgadmin.org/download/](https://www.pgadmin.org/download/).
    - **Note:** The Relational Database Management System (RDBMS) specifically doesn't matter. You can use any RDBMS of
      your choice. **DBeaver** is another popular database management tool that you can use.

## Project Setup

Once you have the prerequisites installed, follow these steps to set up the Spring Boot application:

1. Clone the repository:

   ```bash
   git clone https://github.com/yahyafati/PenTalesKt.git
   ```

2. Navigate to the project directory:

   ```bash
    cd PenTalesKt
   ```

## Configuration

Once you have the project set up, you need to configure the application to connect to the database. Follow these steps
to configure the application:

### Database Configuration

> **Note:** If you are using Docker, you can skip this step. The database will be automatically configured for you. You
> can skip to the [Application Configuration](#application-configuration) step.

First we need to create a database in PostgreSQL named `reading_realm`. To do that, follow these steps:

#### Using pgAdmin

1. Open pgAdmin.
2. Right-click on **Databases** and select **Create** > **Database...**.
3. Enter **reading_realm** as the database name and click **Save**.

#### Using Terminal

1. Open your terminal.
2. Run the following command:

   ```bash
   createdb -U <POSTGRES USERNAME> reading_realm
   ```
    * Replace `<POSTGRES USERNAME>` with your PostgreSQL username.
    * For example, if your PostgreSQL username is `postgres`, you can run the following command:

      ```bash
      createdb -U postgres reading_realm
      ```
3. Enter your password when prompted.
4. Once the database is created, you can **verify** it by running the following command:

   ```bash
   psql -U <POSTGRES USERNAME> -l
   ```
    * Replace `<POSTGRES USERNAME>` with your PostgreSQL username.
    * For example, if your PostgreSQL username is `postgres`, you can run the following command:

      ```bash
      psql -U postgres -l
      ```

### Application Configuration

Once you have created the database, follow these steps to configure the application for local development:

1. Open the project in your preferred IDE.
2. Navigate to the `src/main/resources` directory.
3. Copy the `application-local.yml` file and paste it in the **same** directory.
4. **Rename** the copied file to `application-local.yml`.
5. Open the `application-local.yml` file.
6. Replace the following values with your own values:

   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/reading_realm
       username: <POSTGRES USERNAME>
       password: <POSTGRES PASSWORD>
   ```

    * Replace `<POSTGRES USERNAME>` with your PostgreSQL username.
    * Replace `<POSTGRES PASSWORD>` with your PostgreSQL password.

7. Save the file.
8. Copy `flyway.properties.default` and paste it in the **same** directory.
9. Rename the copied file to `flyway.properties`.
10. Open the `flyway.properties` file.
11. Replace the following values with your own values:

    ```properties
    flyway.url=jdbc:postgresql://localhost:5432/reading_realm
    flyway.user=<POSTGRES USERNAME>
    flyway.password=<POSTGRES PASSWORD>
    ```

    * Replace `<POSTGRES USERNAME>` with your PostgreSQL username.
    * Replace `<POSTGRES PASSWORD>` with your PostgreSQL password.

12. (Docker Only) Copy `.env.default` and paste it in the **same** directory.
13. (Docker Only) Rename the copied file to `.env`.
14. (Docker Only) Open the `.env` file.
15. (Docker Only) Replace the following values with your own values:

    ```properties
    PGADMIN_DEFAULT_EMAIL=<PGADMIN EMAIL>
    PGADMIN_DEFAULT_PASSWORD=<PGADMIN PASSWORD>
    ```

    * Replace `<PGADMIN EMAIL>` with your pgAdmin email. (You can use any email address)
    * Replace `<PGADMIN PASSWORD>` with your pgAdmin password. (You can use any password)

16. Save the file.

## Usage

Once you have the application configured, you can run the application by running application.

### Docker

If you are using **Docker**, you can run the application by running the following command in your terminal:

```bash
docker compose up --build
```

### Local Environment

* If you are using **IntelliJ IDEA**, you can just run the application by clicking the **Run** button in the gutter.
* If you are using **Gradle**, you can run the application by running the following command in your terminal:

  ```bash
    # This
    gradle bootRun --args='--spring.profiles.active=local'
  
    # or
    ./gradlew bootRun --args='--spring.profiles.active=local'
  ```

    * The `--spring.profiles.active=local` argument tells the application to use the `application-local.yml` file for
      configuration.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Team

- __Yahya HAJI__ - [@yahyafati](https://github.com/yahyafati)
- __Melis Buse Elif TORUN__ - [@melisbuse](https://github.com/melisbuse)
- __Mohammad Jawad Kadhim AL-JUHAISHI__ - [@MohammedJawad01](https://github.com/MohammedJawad01)

## Demo

> TODO: Add demo link