package ru.improve.crm.models.transaction;

/* не знаю почему в тз указана строка как тип данных,
логичнее было бы использовать перечисления для типа перевода */
public enum PaymentType {
    CASH, CARD, TRANSFER
}
