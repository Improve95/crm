package ru.improve.crm.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.improve.crm.dto.transaction.TransactionDataResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;
import ru.improve.crm.models.transaction.Transaction;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-17T11:20:13+0700",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.40.0.v20240919-1711, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction toTransaction(TransactionPostRequest transactionPostRequest) {
        if ( transactionPostRequest == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setAmount( transactionPostRequest.getAmount() );
        transaction.setPaymentType( transactionPostRequest.getPaymentType() );

        return transaction;
    }

    @Override
    public TransactionPostResponse toTransactionPostResponse(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionPostResponse transactionPostResponse = new TransactionPostResponse();

        transactionPostResponse.setId( transaction.getId() );
        transactionPostResponse.setTransactionDate( transaction.getTransactionDate() );

        return transactionPostResponse;
    }

    @Override
    public TransactionDataResponse toTransactionGetResponse(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionDataResponse.TransactionDataResponseBuilder transactionDataResponse = TransactionDataResponse.builder();

        transactionDataResponse.amount( transaction.getAmount() );
        transactionDataResponse.id( transaction.getId() );
        transactionDataResponse.paymentType( transaction.getPaymentType() );
        transactionDataResponse.seller( transaction.getSeller() );
        transactionDataResponse.transactionDate( transaction.getTransactionDate() );

        return transactionDataResponse.build();
    }
}
