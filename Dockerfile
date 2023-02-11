FROM gradle:7.6 AS build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk
WORKDIR /app
EXPOSE 80
COPY --from=build /app/build/libs/shopee-0.0.1.jar .
CMD java -jar shopee-0.0.1.jar