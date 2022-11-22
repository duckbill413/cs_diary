package priority_queue;

import java.util.Comparator;
import java.util.Date;

public class Book implements Comparator<Book> {
    private long isbn;
    private String name;
    private Date updated;

    public Book() {
    }

    public Book(long isbn, String name, Date updated) {
        this.isbn = isbn;
        this.name = name;
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", name='" + name + '\'' +
                ", updated=" + updated +
                '}'+'\n';
    }

    @Override
    public int compare(Book o1, Book o2) {
        if (o1.isbn > o2.isbn)
            return 1;
        else if(o1.isbn == o2.isbn){
            if (o1.updated.before(o2.updated)) // o1가 o2보다 먼저 오면 True - 내림차순 before return 1 - 오름차순 after return 1
                return 1;
            else if(o1.updated == o2.updated){
                if (o1.name.compareTo(o2.name) > 0)
                    return 1;
                else return -1;
            }
            else return -1;
        }
        else return -1;
    }
}
