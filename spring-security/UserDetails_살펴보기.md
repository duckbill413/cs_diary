# UserDetails 살펴보기
## 사용자 관리
- 사용자를 표현하는 `UserDetails`
- 권한을 표현하는 `GrantedAuthority`
  - `String getAuthority()`
- `UserDetailsService`, `UserDetailsManager` 를 활용하여 사용자 만들기, 암호 수정 등의 커스텀한 작업을 지원
- 다양한 유형의 `UserDetailsManager` (`InMemoryUserDetailsManager`, `JdbcUserDetailsManager` 등)

## 사용자 관리를 위한 인터페이스
- `UserDetailsService`, `UserDetailsManager` 인터페이스를 이용하여 사용자 관리를 수행함 (CRUD 기능)
  - `UserDetailsService`는 사용자 이름으로 사용자를 검색 (Read)
  - `UserDetailsManager`는 사용자 추가, 수정, 삭제 작업을 수행함 (Create, Update, Delete)
- `read`와 `write` 인터페이스를 분리하여 프레임워크의 유연성 향상
  - 사용자를 인증하는 기능이 필요한 경우 `UserDetalsService` 인터페이스를 구현
  - 추가, 수정, 삭제 등 더 많은 기능이 필요한 경우 `UserDetailsManager` 구현

---
1. `UserDetailsService` 소개
    - 유저 정보를 조회하는 메소드만 존재
    - `UserDetails loadUserByUsername(String username)`
2. `UserDetailsManager` 소개
    - 사용자 생성, 수정, 삭제, 암호변경, 사용자 존재 여부 확인 메소드를 제공
      - `void createUser`
      - `void updateUser`
      - `void deleteUser`
      - `void changePassword`
      - `boolean userExists`
    - `UserDetailsService`를 `extends`하기 때문에 `UserDetailsManager`를 구현하면 클라이언트는 `UserDetailsService` 까지 구현해야함
3. 이용 권리의 집합, `GrantedAuthority`
    - 사용자는 사용자가 수행할 수 있는 작업을 나타내는 이용 권리의 집합을 가짐
    - 사용자는 하나 이상의 권한을 가질 수 있으며 `GrantedAuthority` 라는 인터페이스를 통해 이를 구현

## UserDetails 알아보기
총 7개의 메소드가 존재 (4개의 default 메소드)
  - `Collection<? extends GrantedAuthority> getAuthorities()`
    - 애플리케이션 사용자가 수행할 수 있는 작업을 `GrantedAuthority` 인스턴스의 컬렉션으로 반환
  - `String getPassword()`
    - 사용자의 암호 반환
  - `String getUsername()`
    - 사용자의 사용자ID 반환

필요에 따라 사용자를 비활성화 할 수 있는 메소드 (기본값: `true)
- `boolean isAccountNonExpired()`
- `boolean isAccountNonLocked()`
- `boolean isCredentialsNonExpired()`
- `boolean isEnabled()`

계정만료, 계정잠금, 자격 증명 만료, 계정 비활성화 기능을 통해 사용자를 애플리케이션 수준에서 제어할 수 있음
