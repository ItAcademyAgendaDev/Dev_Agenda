<div align="center">

# 📘 CLI Agenda - Java Backend Team Project

**Console-based agenda application to manage events, tasks, and notes with persistent storage in MySQL using Docker, Flyway, and JDBC.**

<br>

![Java](https://img.shields.io/badge/Java-21-orange)
![Maven](https://img.shields.io/badge/Maven-Project-red)
![MySQL](https://img.shields.io/badge/MySQL-8.4-blue)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED)
![Flyway](https://img.shields.io/badge/Flyway-Migrations-CC0200)
![Tests](https://img.shields.io/badge/Tests-JUnit%205%20%2B%20Mockito-success)

</div>

---

## 📑 Table of Contents

- [📖 Overview](#-overview)
- [✨ Main Features](#-main-features)
- [🛠️ Technologies Used](#️-technologies-used)
- [📁 Project Structure](#-project-structure)
- [⚙️ Installation and Setup](#️-installation-and-setup)
- [▶️ Running the Application](#️-running-the-application)
- [🧪 Testing](#-testing)
- [🗃️ Database Management](#️-database-management)
- [🤝 Team Workflow](#-team-workflow)
- [🎯 Project Goal](#-project-goal)
- [👥 Authors](#-authors)

---

## 📖 Overview

**CLI Agenda** is a collaborative Java backend project developed using an agile workflow, **Git Flow**, and **GitHub Kanban**.

The application allows users to manage three main domains directly from the console:

- **Events**
- **Tasks**
- **Notes**

All data is stored persistently in a **MySQL database** running inside **Docker**, while database schema versioning is managed with **Flyway**.

---

## ✨ Main Features

### 📅 Events
- Create events
- List all events
- Update events
- Delete events

### ✅ Tasks
- Create tasks
- Link tasks to events
- List all tasks
- Mark tasks as completed
- List completed tasks
- Delete tasks

### 📝 Notes
- Create notes linked to tasks
- List all notes
- Update notes
- Delete notes

---

## 🛠️ Technologies Used

- **Java 21**
- **Maven**
- **MySQL 8**
- **Docker Compose**
- **Flyway**
- **JDBC**
- **JUnit 5**
- **Mockito**
- **GitHub Projects / Kanban**
- **Git Flow**

---

## 📁 Project Structure

```text
src
├── main
│   ├── java
│   │   └── org.itacademy
│   │       ├── domain
│   │       │   ├── event
│   │       │   │   ├── model
│   │       │   │   ├── repository
│   │       │   │   └── service
│   │       │   ├── task
│   │       │   │   ├── controller
│   │       │   │   ├── dto
│   │       │   │   ├── mapper
│   │       │   │   ├── model
│   │       │   │   ├── repository
│   │       │   │   └── service
│   │       │   ├── note
│   │       │   │   ├── controller
│   │       │   │   ├── dto
│   │       │   │   ├── mapper
│   │       │   │   ├── model
│   │       │   │   ├── repository
│   │       │   │   └── service
│   │       │   └── exception
│   │       ├── input
│   │       ├── menu
│   │       ├── repository
│   │       │   └── config
│   │       └── Main.java
│   └── resources
│       └── db.migration
│           ├── V1__Create_EventTable.sql
│           ├── V2__Create_TaskTable.sql
│           └── V3__Create_NoteTable.sql
└── test
    └── java
        ├── EventServiceTest.java
        ├── TaskServiceTest.java
        └── NoteServiceTest.java
⚙️ Installation and Setup
1. Clone the repository
git clone https://github.com/ItAcademyAgendaDev/Dev_Agenda.git
cd Dev_Agenda
2. Start the database with Docker

Make sure Docker is installed and running, then execute:

docker compose up -d

This will start the MySQL database container required by the application.

3. Verify database configuration

Ensure your database connection settings match the project configuration, including:

database URL

username

password

port

4. Run Flyway migrations

When the application starts, Flyway will apply the migration scripts and create the necessary tables automatically.

▶️ Running the Application

You can run the application from your IDE or using Maven.

Run from IntelliJ IDEA

Open the project

Wait for Maven dependencies to load

Run the Main.java class

Run from terminal
mvn clean compile
mvn exec:java

Depending on your Maven configuration, you may need to specify the main class explicitly.

🧪 Testing

The project includes unit tests for the main service layer components:

EventServiceTest

TaskServiceTest

NoteServiceTest

Testing is implemented using:

JUnit 5

Mockito

Run tests
mvn test

These tests validate business logic and improve the reliability and maintainability of the application.

🗃️ Database Management

The application uses MySQL as the relational database engine and runs it inside a Docker container for easier setup and consistency across environments.

Database version control is handled with Flyway, using migration scripts located in:

src/main/resources/db.migration
Current migrations

V1__Create_EventTable.sql

V2__Create_TaskTable.sql

V3__Create_NoteTable.sql

This approach ensures that the database schema evolves in a controlled and versioned way.

🤝 Team Workflow

This project was developed collaboratively following good teamwork and version control practices:

Agile workflow

Git Flow branching strategy

GitHub Projects / Kanban board

Feature-based task distribution

Collaborative development and integration

Each team member worked on assigned tasks and contributed to the project using structured branching, shared repository coordination, and collaborative review processes.

🎯 Project Goal

The main goal of this project is to build a console-based agenda manager with a clean backend structure, persistent storage, and collaborative team organization.

It also serves as a practical exercise to strengthen knowledge in:

Java backend development

Layered architecture

JDBC persistence

Database migrations

Testing with JUnit and Mockito

Team collaboration with GitHub

👥 Authors

Cristhian - @cristhianchulca49

Javier - @JavierCame98

Ana Ruth - @anaruth

<div align="center">
🚀 Java Backend Team Project

Built with collaboration, persistence, and clean architecture in mind.

</div> ```