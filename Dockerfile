# Etapa 1: Construção do aplicativo
FROM eclipse-temurin:17-jdk as build

# Diretório de trabalho para a build
WORKDIR /app

# Copiar o arquivo Maven ou Gradle e o código fonte para o container
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src ./src

# Fazer o download das dependências e construir o aplicativo
RUN ./mvnw package -DskipTests

# Etapa 2: Imagem final para execução
FROM eclipse-temurin:17-jre

# Diretório de trabalho para a aplicação
WORKDIR /app

# Copiar o JAR gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expor a porta usada pela aplicação (ajuste conforme necessário)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
