# Iced Latte — MoProCo Demo Project

A production-grade Spring Boot coffee marketplace backend, generated entirely with the **MoProCo** model-driven toolchain. This project is a demonstrator equivalent to the publicly known [Iced Latte]([(https://github.com/Sunagatov/Iced-Latte)]) reference application.

---

## Table of Contents

- [Overview](#overview)
- [Repository Structure](#repository-structure)
- [Technology Stack](#technology-stack)
- [Features](#features)
- [Running the Backend](#running-the-backend)
- [Reproducing from the Spec](#reproducing-from-the-spec)
- [MoProCo Toolchain](#moproco-toolchain)
- [LLM Details](#llm-details)

---

## Overview

Iced Latte (MoProCo edition) demonstrates how a complete, real-world Spring Boot backend can be specified using a concise class-diagram DSL (`.cdiag`) and then fully generated — controllers, services, domain entities, repositories, security configuration, and OpenAPI documentation — without writing Java by hand.

---

## Repository Structure

```
iced-latte-moproco/
├── iced-latte/               # Specification
│   └── iced-latte.cdiag      # Class-diagram spec (input to MoProCo toolchain)
└── icedlatte/                # Generated Spring Boot backend
    ├── src/
    │   └── main/
    │       ├── java/dev/moproco/icedlatte/
    │       │   ├── controller/   # REST controllers
    │       │   ├── service/      # Service interfaces & implementations
    │       │   ├── domain/       # JPA entities, enums, DTOs
    │       │   └── ...
    │       └── resources/
    │           └── application.properties
    ├── pom.xml
    └── mvnw / mvnw.cmd
```

---

## Technology Stack

| Component | Technology |
|---|---|
| Framework | Spring Boot 4.1.0 |
| Language | Java 21 |
| Database | H2 (in-memory) |
| Persistence | Spring Data JPA / Hibernate |
| Web | Spring MVC |
| Security | Spring Security |
| API Docs | SpringDoc OpenAPI / Swagger UI |
| Payments | Stripe Java SDK |
| Build tool | Apache Maven (wrapper included) |

---

## Features

The backend covers the full domain of a coffee marketplace:

- **Product catalog** — browse and search products with images and pricing
- **Product reviews** — submit, like, and manage customer reviews
- **Shopping cart** — add/remove items, view cart totals
- **Orders** — place orders, track status history, admin order management
- **Payments** — Stripe-based checkout with webhook event handling
- **Authentication** — session-based login, email verification, password reset
- **OAuth** — social login via Google and GitHub
- **User profiles** — view and update profile information
- **User avatars** — upload via backend or pre-signed URL (S3-style)
- **Delivery addresses** — manage multiple saved addresses
- **Favorites** — maintain a personal wishlist
- **Support chat** — customer/owner messaging with delivery status tracking

---

## Running the Backend

**Prerequisites:** Java 21+, Git

```bash
# 1. Clone the repository
git clone https://github.com/tbuchmann/iced-latte-moproco.git
cd iced-latte-moproco

# 2. Enter the backend project
cd icedlatte

# 3. Start the application
./mvnw spring-boot:run
```

The server starts on `http://localhost:8080`.  
Swagger UI is available at `http://localhost:8080/swagger-ui.html`.

The application uses an H2 in-memory database; no external database setup is required.

---

## Reproducing from the Spec

All steps can be carried out entirely from **VS Code** using the MoProCo extensions.

1. **Scaffold a new Spring Boot project**  
   Use the official [Spring Boot extension](https://marketplace.visualstudio.com/items?itemName=vmware.vscode-boot-dev-pack) to generate a new project skeleton.

2. **Open the spec file**  
   Open `iced-latte/iced-latte.cdiag` in VS Code.

3. **Generate Spring code from the spec**  
   Right-click the `.cdiag` file in the Explorer and select **"Generate Spring Code"**.

4. **Select the target directory**  
   When prompted, specify the folder of the Spring Boot project you scaffolded in step 1.

5. **Set the base package**  
   Use the same base package you chose during the project scaffold.

6. **Run the LLM post-processor**  
   After code generation completes, right-click the Java source folder and select **"Process Spring Boot Folder"**.  
   This step uses the configured LLM to flesh out method bodies, add validation, and refine the generated code.

---

## MoProCo Toolchain

This project was built entirely with the **MoProCo** (Model-Driven Programming with Code Generation) toolchain, provided as two VS Code extensions:

| Extension | Purpose |
|---|---|
| **class-diag language extension** | DSL editor for `.cdiag` files — syntax highlighting, validation, and code generation trigger *(link to be added)* |
| **mdellm processor extension** | LLM-based post-processor that refines and completes the generated Java source code *(link to be added)* |

The `.cdiag` DSL lets you describe your domain model — entities, enumerations, DTOs, service interfaces, and their relationships — in a compact, readable notation. The toolchain then generates a fully structured Spring Boot project from that description and uses an LLM to fill in the implementation details.

---

## LLM Details

The LLM post-processing step (`"Process Spring Boot Folder"`) was performed using:

| Parameter | Value |
|---|---|
| Model | `deepseek-v4-flash` |
| Hosting | Self-hosted on 8× RTX Pro 6000 |
| Context window | 1,000,000 tokens |
