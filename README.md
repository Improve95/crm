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
    "id", number,
    "name", "string", 
    "contactInfo", "string",
    "registrationDate", "string"
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
    "id", number,
    "name", "string", 
    "contactInfo", "string",
    "registrationDate", "string"
```

HttpStatus: 400

ResponseBody
```
    {
        "message": "not found seller",
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
    "name": "string"
    "contactInfo": "string"
```

1. name - имя пользователя, от 2 до 50 символов
2. contactInfo - контакты пользователя, от 5 до 100 символов

Response

HttpStatus: 200

ResponseBody
```
    "id", number,
    "registrationDate", "string"
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
    "name": "string"
    "contactInfo": "string"
```

Response

HttpStatus: 200

ResponseBody
```
    "id", number,
    "name", "string", 
    "contactInfo", "string",
    "registrationDate", "string"
```

HttpStatus: 400

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

HttpStatus 400

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

GET transactions

