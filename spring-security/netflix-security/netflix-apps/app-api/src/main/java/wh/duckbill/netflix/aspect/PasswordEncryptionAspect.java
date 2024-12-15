package wh.duckbill.netflix.aspect;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import wh.duckbill.netflix.annotation.PasswordEncryption;

import java.lang.reflect.Modifier;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class PasswordEncryptionAspect {
  private final PasswordEncoder passwordEncoder;

  @Around("execution(* wh.duckbill.netflix.controller..*.*(..))")
  public Object around(ProceedingJoinPoint pjp) throws Throwable {
    Arrays.stream(pjp.getArgs())
        .forEach(this::fieldEncryption);
    return pjp.proceed();
  }

  public void fieldEncryption(Object arg) {
    if (ObjectUtils.isEmpty(arg)) {
      return;
    }

    FieldUtils.getAllFieldsList(arg.getClass())
        .stream()
        .filter(filter -> !(Modifier.isFinal(filter.getModifiers())) && !(Modifier.isStatic(filter.getModifiers())))
        .forEach(field -> {
          try {
            boolean encryptionTarget = field.isAnnotationPresent(PasswordEncryption.class);
            if (!encryptionTarget) {
              return;
            }

            Object encryptionField = FieldUtils.readField(field, arg, true);
            if (!(encryptionField instanceof String)) {
              return;
            }

            String encrypted = passwordEncoder.encode((String) encryptionField);
            FieldUtils.writeField(field, arg, encrypted);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }
}
