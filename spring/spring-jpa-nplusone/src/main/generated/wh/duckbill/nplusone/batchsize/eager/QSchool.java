package wh.duckbill.nplusone.batchsize.eager;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchool is a Querydsl query type for School
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchool extends EntityPathBase<School> {

    private static final long serialVersionUID = 1007524924L;

    public static final QSchool school = new QSchool("school");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<Student, QStudent> students = this.<Student, QStudent>createList("students", Student.class, QStudent.class, PathInits.DIRECT2);

    public QSchool(String variable) {
        super(School.class, forVariable(variable));
    }

    public QSchool(Path<? extends School> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSchool(PathMetadata metadata) {
        super(School.class, metadata);
    }

}

