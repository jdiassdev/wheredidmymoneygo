package com.jdiassdev.wheredidmymoneygo.feature.transaction;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jdiassdev.wheredidmymoneygo.entity.Category;
import com.jdiassdev.wheredidmymoneygo.entity.Transaction;
import com.jdiassdev.wheredidmymoneygo.entity.User;
import com.jdiassdev.wheredidmymoneygo.feature.category.CategoryRepository;
import com.jdiassdev.wheredidmymoneygo.feature.user.UserRepository;

@Service
public class TransactionService {

        private final TransactionRepository transactionRepository;
        private final UserRepository userRepository;
        private final CategoryRepository categoryRepository;

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
                                transaction.getDescription(),
                                transaction.getCategory().getName(),
                                transaction.getAmount(),
                                "Gasto registrado com sucesso");
        }

        public List<TransactionDTO.ListUserTransactionsResponse> list(
                        String email,
                        TransactionDTO.ListUserTransactionsRequest dto) {
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                List<Transaction> transactions;

                if (dto.category_id() != null) {
                        transactions = transactionRepository
                                        .findByUserIdAndCategoryId(user.getId(), dto.category_id());
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
                                                t.getCreatedAt()
                                        )
                                )
                                .toList();
        }

}
