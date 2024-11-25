# PASTES

`PASTES` is a project for creating and viewing paste (posts), which can be small articles, training tutorials
or just notes, you can attach files to all this, which is always nice.

The server part of the project is presented in this repository. This is a REST API application that is implemented
using:

* `Java 21`
* `Spring 3`
* `PostgreSQL`
* `S3`
* `Docker`
* `OpenAPI`

<img src="https://www.animatedimages.org/data/media/562/animated-line-image-0184.gif" width="1920" />

## Contents

* [**Getting started**](#getting-started)
* [**Running with Docker**](#running-with-docker)
* [**License**](#license)

## Getting started

### Step 1: Set Up Environment Variables

1. Fill in the environment variables in the [setenv.sh](setenv.sh) file.

2. Activate environment variables by running [setenv.sh](setenv.sh) file:

```bash
source setenv.sh
```

### Step 2: Build Application

```bash
mvn clean package
```

### Step 3: Run Application

```bash
mvn spring-boot:run
```

If you have selected port 8080, the application will be available at http://localhost:8080.

The OpenAPI documentation will be available at http://localhost:8080/swagger-ui/index.html.

## Running with Docker

### Step 1: Build Application

```bash
mvn clean package
```

### Step 2: Start with Docker Compose

```bash
docker-compose up --build
```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
