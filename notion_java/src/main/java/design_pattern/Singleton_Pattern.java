package design_pattern;

class Singleton {
    private static class singleInstanceHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
    public static synchronized Singleton getInstance(){
        return singleInstanceHolder.INSTANCE;
    }
}
public class Singleton_Pattern {
    public static void main(String[] args) {
        Singleton a = Singleton.getInstance();
        Singleton b = Singleton.getInstance();

        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        if (a==b) {
            System.out.println(true);
        }
    }
}

/**
싱글톤 패턴
싱글톤 패턴(Singleton pattern)은 하나의 클래스에 오직 하나의 인스턴스만 가지는 패턴 입니다.
데이터베이스 연결 모듈에 많이 사용합니다.

인스턴스르 다른 모듈들이 공유하며 사용하기 때문에 인스턴스를 생성할 때 드는 비용이 줄어드는 장점이 있습니다.
하지만 의존성이 높아지는다는 단점이 있습니다.
싱글톤 패턴은 TDD(Test Driven Development)를 할 때 걸림돌이 됩니다. TDD를 할 때는 단위 테스트를 주로 하는데,
단위 테스트는 테스트가 서로 독립적이어야 하며 테스트를 어떤 순서로든 실행할 우 있어야 합니다. 하지만 싱글톤 패턴은
하나의 인스턴스를 기반으로 구현하는 패턴이므로 '독립적인' 인스턴스를 가지기 어렵습니다.

싱글톤 패턴은 사용하기 쉽고 실용적이지만 모듈 간의 결합을 강하게 만들 수 있다는 단점이 있습니다. 이때 의존성 주입을
통해 모듈간의 결합을 조금 더 느슨하게 만들어 해결할 수 있습니다. 의존성을 주입하면 모듈을 쉽게 교체할 수 있는 구조가 되어
테스트하기 편리하며 마이그레이션 하기 수월합니다. 또한 의존성 방향이 일관되고, 모듈간의 관계가 더 명확해집니다.
**/