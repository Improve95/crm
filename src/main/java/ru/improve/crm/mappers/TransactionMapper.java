package ru.improve.crm.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.improve.crm.dto.transaction.TransactionDataResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;
import ru.improve.crm.models.transaction.Transaction;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    Transaction toTransaction(TransactionPostRequest transactionPostRequest);

    TransactionPostResponse toTransactionPostResponse(Transaction transaction);

    TransactionDataResponse toTransactionDataResponse(Transaction transaction);

}
