ARG PLAYWRIGHT_TAG=v1.51.0-noble
FROM mcr.microsoft.com/playwright:${PLAYWRIGHT_TAG}

# Install JDK 21 and Maven
RUN apt-get update && \
    apt-get install -y wget unzip gnupg2 curl && \
    apt-get install -y openjdk-21-jdk && \
    apt-get install -y maven && \
    apt-get clean

# Set Java environment variables
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Set working directory
WORKDIR /usr/src/app

# Copy project files
COPY . .

# Build the Maven project
RUN mvn clean install -DskipTests

# Default command to run tests (can be changed)
CMD ["mvn", "test"]
