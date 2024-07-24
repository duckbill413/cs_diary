package wh.duckbill.nplusone.batchsize.lazy;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPencilcase is a Querydsl query type for Pencilcase
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPencilcase extends EntityPathBase<Pencilcase> {

    private static final long serialVersionUID = -335044571L;

    public static final QPencilcase pencilcase = new QPencilcase("pencilcase");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<Pencil, QPencil> pencils = this.<Pencil, QPencil>createList("pencils", Pencil.class, QPencil.class, PathInits.DIRECT2);

    public QPencilcase(String variable) {
        super(Pencilcase.class, forVariable(variable));
    }

    public QPencilcase(Path<? extends Pencilcase> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPencilcase(PathMetadata metadata) {
        super(Pencilcase.class, metadata);
    }

}

