# Playwright Java Test Framework

This is a modular, scalable, and cloud-ready test automation framework built using **Java**, **Playwright**, **TestNG**, and **Google Guice** for dependency injection. It supports UI, API, database validations, YAML/CSV/XLSX test data, screenshot logging, CI/CD (Jenkins/Azure), and advanced reporting with ExtentReports.

## ✨ Features

- ✅ Java 21 with Maven
- ✅ Playwright for cross-browser automation
- ✅ TestNG for parallel execution & test configuration
- ✅ Dependency Injection with Guice
- ✅ Configurable environments using YAML
- ✅ ExtentReports with screenshots
- ✅ Support for CSV, JSON, XLSX test data
- ✅ REST Assured for API testing
- ✅ PostgreSQL & Oracle DB integration
- ✅ CI/CD ready for Jenkins & Azure DevOps
- ✅ Notifications via Email, Slack, MS Teams

## 📁 Project Structure

src/ ├── main/ │ ├── java/com/prabhu/myapp/ # Framework code │ ├── resources/ │ │ ├── application.yml # Global config │ │ └── environment/qa.yml # Env-specific config ├── test/ │ ├── java/com/prabhu/myapp/ # Test classes


## 🚀 Getting Started

### Prerequisites

- Java 21
- Maven 3.8+
- Git
- (Optional) Docker, Jenkins, Azure DevOps

### Run Tests



```bash
mvn clean test

Or with a specific TestNG suite:

mvn test -DsuiteXmlFile=testng.xml

Run Headless or in CI
Set in YAML config or pass via CLI:

mvn test -Dheadless=true -Denv=qa

🛠️ Customize
Add new tests in src/test/java/...

Configure application.yml and environment/qa.yml

Use helper utilities in com.prabhu.myapp.helpers

🧪 CI/CD Integration
Azure Pipelines and Jenkins support

Supports environment triggers and notifications

📬 Notifications
Email via Jakarta Mail

Slack & Microsoft Teams via Webhooks

📜 License
MIT

👤 Author
Prabhu Kumar P
📧 LinkedIn
💻 GitHub

