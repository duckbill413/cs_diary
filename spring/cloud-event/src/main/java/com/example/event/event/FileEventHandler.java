package com.example.event.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class FileEventHandler {
    @EventListener // EventListener 완료 구현
    public void onFileEventListener(FileEvent fileEvent) {
        log.info("file event receive type: {} data: {}", fileEvent.getType(), fileEvent.getData());
    }

    /** INFO
     * BEFORE_COMMIT: 커밋 직전에 수행됩니다. 트랜잭션 진입 전이 아니다.
     * AFTER_COMMIT: 커밋 직후 수행됩니다.
     * AFTER_ROLLBACK: 롤백 직후 수행됩니다.
     * AFTER_COMPLETION: 트랜잭션이 완료된 후 수행딥니다.
     *
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforeTransactionProcess(BeforeFileTransactionEvent before){
        before.callback();
    }
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterTransactionProcess(AfterFileTransactionEvent after){
        after.callback();
    }
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void completeTransactionProcess(AfterFileTransactionEvent after){
        after.complete();
    }
}
