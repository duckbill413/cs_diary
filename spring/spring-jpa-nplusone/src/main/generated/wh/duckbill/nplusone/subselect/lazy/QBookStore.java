package wh.duckbill.nplusone.subselect.lazy;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookStore is a Querydsl query type for BookStore
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookStore extends EntityPathBase<BookStore> {

    private static final long serialVersionUID = -928782097L;

    public static final QBookStore bookStore = new QBookStore("bookStore");

    public final ListPath<Book, QBook> books = this.<Book, QBook>createList("books", Book.class, QBook.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QBookStore(String variable) {
        super(BookStore.class, forVariable(variable));
    }

    public QBookStore(Path<? extends BookStore> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBookStore(PathMetadata metadata) {
        super(BookStore.class, metadata);
    }

}

