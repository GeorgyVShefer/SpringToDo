# Используем официальный образ JDK
FROM eclipse-temurin:21-jdk

# Указываем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем собранный jar-файл
COPY target/spring-todo-app-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт 8080
EXPOSE 8080

# Команда запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]