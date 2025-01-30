# Smart Delivery Management System
 
The **Smart Delivery Management System (SDMS)** is an innovative, fully integrated solution designed to simplify and streamline the entire delivery process for e-commerce businesses. This system integrates order management, delivery tracking, and notification services in a seamless, scalable architecture. Built with modern technologies, it enables efficient handling of customer orders, real-time delivery updates, and customer notifications.
 
## Key Features:
 
### 🛒 **Order Management**
- Create, update, and manage customer orders efficiently.
- Track orders in real time, from order creation to delivery.
- Integration with a delivery management service for real-time status updates.
 
### 🚚 **Delivery Management**
- Manage and track deliveries seamlessly.
- Ensure real-time updates on delivery status, with third-party service integrations.
- Easily monitor the status of multiple deliveries.
 
### 📲 **Notification Service**
- Automated notifications for order updates, delivery status, and more.
- Supports **Email** and **SMS** notifications.
- Keeps customers informed at every step of their order journey.
 
### 🔐 **Security**
- Role-based access control using **Spring Security**.
- Authentication and authorization to protect sensitive endpoints.
 
### 💡 **Microservices Architecture**
- Built with **Spring Boot** for modular and scalable services.
- **Spring Cloud** ensures efficient communication, fault tolerance, and load balancing across microservices.
- API Gateway for routing requests to appropriate microservices.
 
### 💾 **Database**
- Development: **H2 Database** for fast in-memory storage.
- Production: **MySQL** for reliable, persistent data storage.
 
## Technologies Used:
- **Backend Framework:** Spring Boot, Spring Cloud
- **Database:** H2 (Development), MySQL (Production)
- **Notification Service:** Twilio (SMS), Email Service (SMTP or other)
- **Security:** Spring Security
- **Version Control:** Git, GitHub for source code management
 
## How It Works:
1. **Order Creation:** Customers create orders via the order service, which generates an order ID and stores the order details in the database.
2. **Delivery Tracking:** The delivery service tracks and updates the delivery status, allowing customers to view their delivery progress.
3. **Customer Notifications:** The notification service sends email/SMS notifications to customers about order and delivery status.
4. **Microservice Communication:** All services communicate through REST APIs and are orchestrated via Spring Cloud and the API Gateway.
 
## Setup Instructions:
 
### Prerequisites:
1. **Java 11+** installed on your machine.
2. **Maven** for building the project.
3. **Git** for version control.
 
### To run the project locally:
1. Clone the repository:
   ```bash  git clone https://github.com/MihirMehar/Smart-Delivery-Management-System.git
2. Navigate to the root directory of the project. 
3. Build and run the services:
 mvn clean install
 mvn spring-boot:run
4. Access the API Gateway at http://localhost:9090.

   
**Future Enhancements:**
 
1.Integration with more notification services like Push Notifications.
2.Adding User Authentication for customers to manage their orders. 
3.Implementing Advanced Delivery Analytics.

**License:**
 
This project is licensed under the MIT License - see the LICENSE file for details.

🔗 Related Links:
 
[API Documentation] (https://spring.io/guides/gs/testing-restdocs)
Twilio API Documentation
Spring Boot Documentation


