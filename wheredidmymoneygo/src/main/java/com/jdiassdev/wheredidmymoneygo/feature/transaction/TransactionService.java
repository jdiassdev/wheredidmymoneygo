package com.jdiassdev.wheredidmymoneygo.feature.transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.jdiassdev.wheredidmymoneygo.entity.Transaction;
import com.jdiassdev.wheredidmymoneygo.entity.User;
import com.jdiassdev.wheredidmymoneygo.feature.user.UserRepository;

@Service
public class TransactionService {

    private static final Log logger = LogFactory.getLog(TransactionService.class);
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository,
            UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public TransactionDTO.CreateResponse create(
            String email,
            TransactionDTO.CreateRequest dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(dto.amount());
        transaction.setCategory(dto.category());
        transaction.setDescription(dto.description());

        transaction.updateType();

        transactionRepository.save(transaction);

        return new TransactionDTO.CreateResponse(
                transaction.getAmount(),
                "Gasto registrado com sucesso");
    }

}
