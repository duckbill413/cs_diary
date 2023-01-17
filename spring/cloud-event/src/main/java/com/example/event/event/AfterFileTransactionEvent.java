package com.example.event.event;

public interface AfterFileTransactionEvent extends FileAbstractTransactionEvent {
    // commit 이 일어난 후
    void complete(); // 트랜잭션이 모두 완료 된 후 수행
}
