package com.example.batch.part4.batch;

import com.example.batch.part5.Orders;
import com.example.batch.part4.model.Users;
import com.example.batch.part4.model.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author        : duckbill413
 * date          : 2023-02-07
 * description   :
 **/
@RequiredArgsConstructor
public class SaveUsersTasklet implements Tasklet {
    private final UsersRepository usersRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<Users> users = createUsers();

        Collections.shuffle(users);

        usersRepository.saveAll(users);
        return RepeatStatus.FINISHED;
    }

    private List<Users> createUsers(){
        List<Users> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Users user = new Users("user"+(i+1),
                    Collections.singletonList(Orders.builder()
                            .itemName("item " + i)
                            .price(i*6000)
                            .createdDate(LocalDate.now().minusDays(1))
                            .build()
                    ));
            users.add(user);
        }
        for (int i = 100; i < 200; i++) {
            Users user = new Users("user"+(i+1),
                    Collections.singletonList(Orders.builder()
                            .itemName("item " + i)
                            .price(i*2000)
                            .createdDate(LocalDate.now())
                            .build()
                    ));
            users.add(user);
        }
        for (int i = 200; i < 300; i++) {
            Users user = new Users("user"+(i+1),
                    Collections.singletonList(Orders.builder()
                            .itemName("item " + i)
                            .price(i*800)
                            .createdDate(LocalDate.now().plusDays(2))
                            .build()
                    ));
            users.add(user);
        }
        return users;
    }
}
