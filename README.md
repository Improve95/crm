# ”CRM” система
"Crm" - сервис для управлением информацией о продавцах и их 
транзакциях. Система должна включает возможности для создания, чтения, обновления и
удаления данных о продавцах и транзакциях. Система также включает функции аналитики
для обработки и анализа данных.

# Продавец

## Описание

Основные поля продавца:
- Идентификационный номер продавца
- Имя
- Контактные данные
- Дата и время регистрации

1. Идентификационный номер продавца - номер в int4
2. Имя - не пустая строка
3. Контактные данные - не пустая уникальная строка, не меньше 5 символов
4. Дата и время регистрации - дата и время регистрации в iso формате

## Операции с продавцом

запрос/ответ в форме application/json 

sellers

GET /sellers - получение всех продавцов

Response

HttpStatus: 200

ResponseBody
```
{
    "id": number,
    "name": "string", 
    "contactInfo": "string",
    "registrationDate": "string"
}
```

1. id - идентификационный номер под которым сохранен продавец
2. name - имя продавца
3. contactInfo - контактный данные продавца
4. registrationDate - дата регистрации продавца в iso формате

GET /sellers/{id} - получение продавца с идентификатором {id}

Response

HttpStatus: 200

ResponseBody
```
{
    "id": number,
    "name": "string", 
    "contactInfo": "string",
    "registrationDate": "string"
}
```

HttpStatus: 404

ResponseBody
```
{
    "message": "string",
    "fieldsWithError": [
        "string"
    ],
    "time": "string"
}
```

1. message - сообщение ошибки
2. fieldsWithError - поля запроса, которые вызвали ошибку
3. time - время возникновения ошибки в iso формате

POST /sellers - сохранение нового продавца

RequestBody
```
{
    "name": "string"
    "contactInfo": "string"
}
```

1. name - имя пользователя, от 2 до 50 символов
2. contactInfo - контакты пользователя, от 5 до 100 символов, уникальное поле

Response

HttpStatus: 200

ResponseBody
```
{
    "id": number,
    "registrationDate": "string"
}
```

1. id - идентификационный номер под которым сохранен продавец
2. registrationDate - дата регистрации продавца в iso формате

HttpStatus: 400

ResponseBody
```
{
    "message": "string",
    "fieldsWithError": [
        "string"
    ],
    "time": "string"
}
```

PATCH /sellers/{id} - изменение данных продавца с идентификатором {id}

RequestBody
```
{
    "name": "string"
    "contactInfo": "string"
}
```

Response

HttpStatus: 200

ResponseBody
```
{
    "id": number,
    "name": "string", 
    "contactInfo": "string",
    "registrationDate": "string"
}
```

HttpStatus: 400

ResponseBody
```
{
    "message": "string",
    "fieldsWithError": [
        "string"
    ],
    "time": "string"
}
```

HttpStatus: 404

ResponseBody
```
{
    "message": "string",
    "fieldsWithError": 
    [
        "string",
    ],
    "time": "string"
}
```

DELETE /sellers/{id} - удаление пользователя с идентификатором {id}

Response

HttpStatus: 200

ResponseBody
```
```

HttpStatus 404

ResponseBody
```
{
    "message": "string",
    "fieldsWithError": 
    [
        "string",
    ],
    "time": "string"
}
```

# Транзакции

## Описание

Основные поля транзакции: 
- Идентификационный номер продавца, совершившего операцию
- Сумма транзакции
- Тип оплаты
- Даты и время транзакции

## Операции с транзакцией

transactions

запрос/ответ в форме application/json

GET /transactions - получение всех транзакций

Response

HttpStatus: 200

ResponseBody
```
{
    "sellerId": int
    "amount": int, 
    "paymentType": "string",
    "transactionDate": "string"
}
```

1. sellerId- идентификатор продавца, совершившего транзакцию
2. amount - сумма транзакции в int4
3. paymentType - тип оплаты транзакции в enum('CASH', 'CARD', 'TRANSFER')
4. transactionDate - дата совершения транзакции

GET /transactions/{id} - получение транзакции c идентификатором {id}

Response

HttpStatus: 200

ResponseBody
```
{
    "sellerId": int
    "amount": int, 
    "paymentType": "string",
    "transactionDate": "string"
}
```

1. sellerId - номер продавца совешающего транзакцию в int4
2. amount - сумма транзакции в int4, больше нуля 
3. paymentType - тип оплаты в enum('CASH', 'CARD', 'TRANSFER')
4. transactionDate - дата совершения транзакции в iso формате

HttpStatus: 404

ResponseBody
```
{
    "message": "string",
    "fieldsWithError": [
        "string"
    ],
    "time": "string"
}
```

1. message - сообщение ошибки
2. fieldsWithError - поля запроса, которые вызвали ошибку
3. time - время возникновения ошибки в iso формате


GET /transactions/seller/{id} - получение всех транзакций, выполненных продавцом в идентификатором {id}

Response

HttpStatus: 200

Возвращается список транзакций
ResponseBody
```
    [
        {
            "sellerId": int
            "amount": int, 
            "paymentType": "string",
            "transactionDate": "string"
        },
    ]
```

HttpStatus: 404

ResponseBody
```
{
    "message": "string",
    "fieldsWithError": [
        "string"
    ],
    "time": "string"
}
```

POST /transactons

RequestBody
```
{    
    "sellerId": int,
    "amount": int,
    "paymentType": "string"
}
```

1. sellerId - номер продавца совешающего транзакцию в int4
2. amount - сумма транзакции в int4, больше нуля
3. paymentType - тип оплаты в enum('CASH', 'CARD', 'TRANSFER')

Response

HttpStatus: 200

ResponseBody
```
{
    "id": int,
    "transactionDate": "string"
}
```

1. id - идентификатор транзакции
2. transactionData - дата совершения транзакции

HttpStatus: 404

ResponseBody
```
{
    "message": "string",
    "fieldsWithError": [
        "string"
    ],
    "time": "string"
}
```

# Аналитика

## Операции

запрос/ответ в форме application/json


GET /sellers/mostProductivity - возвращает самого продуктивного продавца за промежуток времени 
по сумме соверенных транзкций

RequestBody
```
{
    "startPeriod": "string",
    "endPeriod": "string"
}
```

1. startPeriod - начало периода для выборки транзакций в iso формате
2. endPeriod - конец периода для выборки транзакций в iso формате

Response

HttpStatus: 200

ResponseBody
```
{
    "id": number,
    "name": "string", 
    "contactInfo": "string",
    "registrationDate": "string"
}
```

HttpStatus: 400

ResponseBody
```
{
    "message": "string",
    "fieldsWithError": [
        "string"
    ],
    "time": "string"
}
```

GET /sellers/withLessAmount - возвращает продавцов с суммой транзакций меньше чем maxAmount за период времени

RequestBody
```
{
    "maxAmount": int,
    "startPeriod": "string"
    "endPeriod": "string"
}
```

HttpStatus: 400

ResponseBody
```
{
    "message": "string",
    "fieldsWithError": [
        "string"
    ],
    "time": "string"
}
```

## Общие ошибки:

HttpStatus: 500

ResponseBody
```
{
    "message": "string",
    "time": "string"
}
```

## Описание кодов ошибок
1. 404 - Сущность не была найдена по переданному атрибуту
2. 400 - Неверный запрос, не соответствующий api
3. 500 - Ошибка на стороне сервиса

## Форматы
1. iso формат времени - "2000-10-15T21:00:00.000"

# Техническая документация

### Используемые зависимости:
#### Приложение:
```
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-validation'
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.slf4j:slf4j-api:2.0.16'
runtimeOnly 'org.postgresql:postgresql'
compileOnly 'org.projectlombok:lombok:1.18.34'
annotationProcessor 'org.projectlombok:lombok:1.18.34'
implementation 'org.mapstruct:mapstruct:1.6.2'
annotationProcessor 'org.mapstruct:mapstruct-processor:1.6.2'
```
#### Тесты: 
```
testImplementation 'org.springframework.boot:spring-boot-testcontainers'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'org.springframework.boot:spring-boot-starter-data-jpa'
testImplementation 'org.testcontainers:postgresql'
testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
```

### Сборка и запуск проекта:
#### Создание скриптов для сборки:
```
./gradle wrapper
```

#### Создание исполяемого JAR архива приложения:
```
./gradlew 
```

#### Запуск приложения:
```
./gradlew
```