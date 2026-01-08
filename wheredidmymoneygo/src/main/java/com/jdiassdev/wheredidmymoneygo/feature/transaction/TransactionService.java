package com.jdiassdev.wheredidmymoneygo.feature.transaction;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jdiassdev.wheredidmymoneygo.dto.TransactionTotals;
import com.jdiassdev.wheredidmymoneygo.entity.Category;
import com.jdiassdev.wheredidmymoneygo.entity.Transaction;
import com.jdiassdev.wheredidmymoneygo.entity.User;
import com.jdiassdev.wheredidmymoneygo.feature.category.CategoryRepository;
import com.jdiassdev.wheredidmymoneygo.feature.user.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TransactionService {

        private final TransactionRepository transactionRepository;
        private final UserRepository userRepository;
        private final CategoryRepository categoryRepository;

        private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

        public TransactionService(TransactionRepository transactionRepository,
                        UserRepository userRepository,
                        CategoryRepository categoryRepository) {
                this.transactionRepository = transactionRepository;
                this.userRepository = userRepository;
                this.categoryRepository = categoryRepository;
        }

        public TransactionDTO.CreateResponse create(
                        String email,
                        TransactionDTO.CreateRequest dto) {

                User user = userRepository.findByEmail(email) // precisa existir no banco
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                Category category = categoryRepository.findById(dto.category_id())
                                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

                Transaction transaction = new Transaction();
                transaction.setUser(user);
                transaction.setCategory(category);
                transaction.setAmount(dto.amount());
                transaction.setDescription(dto.description());

                transaction.updateType();
                transactionRepository.save(transaction);

                return new TransactionDTO.CreateResponse(
                                transaction.getId(),
                                transaction.getDescription(),
                                transaction.getCategory().getName(),
                                transaction.getAmount(),
                                transaction.getCreatedAt(),
                                "Gasto registrado com sucesso");
        }

        public List<TransactionDTO.ListUserTransactionsResponse> list(
                        String email,
                        TransactionDTO.ListUserTransactionsRequest dto) {

                log.info("Chamando list de transações para user: {}", email);
                log.info("Filtro de categoria: {}", dto.category());
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                List<Transaction> transactions;

                if (dto.category() != null) {
                        transactions = transactionRepository
                                        .findByUserIdAndCategory_Id(user.getId(), dto.category());
                } else {
                        transactions = transactionRepository
                                        .findByUserId(user.getId());
                }

                return transactions.stream()
                                .map(t -> new TransactionDTO.ListUserTransactionsResponse(
                                                t.getId(),
                                                t.getDescription(),
                                                t.getAmount(),
                                                t.getCategory().getName(),
                                                t.getCreatedAt()))
                                .toList();
        }

        public TransactionDTO.TotalResumeTransactionsResponse totalResume(
                        String email,
                        TransactionDTO.TotalResumeTransactionsRequest dto) {

                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                TransactionTotals totals = transactionRepository.getTotals(
                                user.getId(),
                                dto.category_id());

                return new TransactionDTO.TotalResumeTransactionsResponse(
                                totals.totalAmount(),
                                totals.totalItems(),
                                totals.minAmount(),
                                totals.maxAmount());

        }
}
