# Sử dụng image MySQL chính thức
FROM mysql:8.0

# Đặt biến môi trường mặc định
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=workoutplanner
ENV MYSQL_USER=lamiee
ENV MYSQL_PASSWORD=lamiee

# Copy file schema.sql vào thư mục init của MySQL
COPY src/main/resources/schema.sql /docker-entrypoint-initdb.d/

# Port mặc định của MySQL
EXPOSE 3306
