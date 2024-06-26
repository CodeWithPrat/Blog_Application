# Spring Boot Blogging Application

This is a blogging application developed using Spring Boot, implementing JWT authentication for secure user access. Users can register, sign in, and manage their blog posts within specified categories. There are two types of users: normal users and admin users, each with different permissions.

## Features

- **Authentication**: JWT-based authentication system for secure user login and registration.
- **User Management**: Supports registration and login for normal users and admin users.
- **Category Management**: CRUD operations for managing categories to organize blog posts.
- **Blog Management**: Users can perform CRUD operations (Create, Read, Update, Delete) on their blog posts.
- **Comment System**: Users can add comments to specific blog posts.
- **Image Management**: Ability to add, update, and delete images within blog posts.
- **API Documentation**: Implemented Swagger for API documentation to easily explore and test APIs.
- **Exception Handling**: Comprehensive handling of exceptions to ensure robust application behavior.
- **Security**: Fully secured application environment ensuring data privacy and integrity.

## User Management
- User Registration and Sign In functionality.
- Two types of users: Normal Users and Admin Users.
- Role-based access control using JWT for authentication.

## Content Management
- CRUD operations for Categories to organize blogs.
- CRUD operations for Blog Posts, including adding, updating, and deleting posts.
- Ability to add, update, and delete images within blog posts.
- Ability for users to add comments to blog posts.

## Technologies Used

- Java
- Maven
- Spring Boot
- JWT (JSON Web Tokens)
- Swagger
- Hibernate/JPA
- MySQL
- Spring Security

## Security

The application uses JWT to secure API endpoints. Users must register and sign in to obtain a JWT token, which is then used to authenticate and authorize API requests.

## Roles

There are two types of users in the application:
- **Normal User**: Can perform CRUD operations on blog posts and categories, and add comments to blog posts.
- **Admin User**: Has all the privileges of a normal user, plus the ability to manage users and perform administrative tasks.

## Exception Handling
The application handles all kinds of exceptions and returns meaningful error responses to the client.

## License
This application is licensed under the MIT License. See the LICENSE file for details.

## Contributing
Contributions are welcome! If you'd like to contribute to the application, please fork the repository and submit a pull request.

