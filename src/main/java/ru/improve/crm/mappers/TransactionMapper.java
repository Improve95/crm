package ru.improve.crm.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.improve.crm.dto.transaction.TransactionGetResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;
import ru.improve.crm.models.Transaction;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    TransactionGetResponse toTransactionGetResponse(Transaction transaction);

    Transaction toTransaction(TransactionPostRequest transactionPostRequest);

    TransactionPostResponse toTransactionPostResponse(Transaction transaction);
}
