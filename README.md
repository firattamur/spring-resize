# 🌿 SpringResize

SpringResize is a Spring Boot REST API project that allows you to easily resize images. The application is designed to be highly scalable and leverages various AWS services and Terraform for infrastructure management. It also includes detailed API documentation using Springdoc, as well as separate main and development stages with workflows for smooth deployment.

## 🌟 Features

- 🖼️ Image resizing through a simple REST API
- 📚 API documentation using Springdoc
- ⚡ AWS Lambda for serverless function execution
- 🛡️ AWS API Gateway for managing and securing the API
- 🗄️ AWS S3 for storing and retrieving images
- 📦 AWS DynamoDB for storing image metadata
- 🏗️ Infrastructure setup and management with Terraform
- 🚦 Separate main and development stages
- 🔄 Automated deployment workflows
- 🐳 Docker containerization for consistent environment management

## 🔧 Prerequisites

Before using SpringResize, ensure that you have the following installed on your system:

- Java 11 or higher
- Maven 3.6 or higher
- AWS CLI
- Terraform
- Docker

## 💻 Installation

1. Clone the repository:

```bash
git clone https://github.com/yourusername/SpringResize.git
```

2. Navigate to the project directory:

```bash
cd SpringResize
```

3. Compile and package the application:

```bash
mvn clean package
```

## 🚀 Usage

1. Set up the AWS infrastructure by navigating to the `terraform` directory and executing:

```bash
terraform init
terraform apply
```

2. Build the Docker image:

```bash
docker build -t your-image-name .
```

3. Push the Docker image to Amazon Elastic Container Registry (ECR) or another container registry:

```bash
docker push your-image-name
```

4. Deploy the application to AWS Lambda by running:

```bash
./deploy.sh
```

5. Access the API documentation at the following URL:

```bash
https://your-api-gateway-url/swagger-ui.html
```

Replace `your-api-gateway-url` with the API Gateway URL generated after deployment.

## 🌱 Development

For development, use the `develop` branch:

```bash
git checkout develop
```

## 🤝 Contributing

We welcome contributions to SpringResize. Please follow these steps to contribute:

1. Fork the repository
2. Create a new branch for your feature
3. Commit your changes
4. Create a pull request targeting the `develop` branch

## 📄 License

SpringResize is released under the [MIT License](LICENSE).
