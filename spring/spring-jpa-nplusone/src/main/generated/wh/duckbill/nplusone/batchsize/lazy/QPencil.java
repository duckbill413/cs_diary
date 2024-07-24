package wh.duckbill.nplusone.batchsize.lazy;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPencil is a Querydsl query type for Pencil
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPencil extends EntityPathBase<Pencil> {

    private static final long serialVersionUID = 1068754805L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPencil pencil = new QPencil("pencil");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QPencilcase pencilcase;

    public QPencil(String variable) {
        this(Pencil.class, forVariable(variable), INITS);
    }

    public QPencil(Path<? extends Pencil> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPencil(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPencil(PathMetadata metadata, PathInits inits) {
        this(Pencil.class, metadata, inits);
    }

    public QPencil(Class<? extends Pencil> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pencilcase = inits.isInitialized("pencilcase") ? new QPencilcase(forProperty("pencilcase")) : null;
    }

}

