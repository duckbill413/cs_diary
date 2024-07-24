package wh.duckbill.nplusone.eager;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParty is a Querydsl query type for Party
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParty extends EntityPathBase<Party> {

    private static final long serialVersionUID = 1254485393L;

    public static final QParty party = new QParty("party");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<Person, QPerson> people = this.<Person, QPerson>createList("people", Person.class, QPerson.class, PathInits.DIRECT2);

    public QParty(String variable) {
        super(Party.class, forVariable(variable));
    }

    public QParty(Path<? extends Party> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParty(PathMetadata metadata) {
        super(Party.class, metadata);
    }

}

