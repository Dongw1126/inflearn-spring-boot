package bccard.payzintern.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


// @Aspect 적어줘야 AOP로 쓸 수 있음
@Aspect
@Component
public class TimeTraceAop {
    // @Around로 어디에다 쓸지 타겟팅
    // 실행하는 패키지명 클래스명 등등 -> 현재는 하위에 다 적용
    @Around("execution(* bccard.payzintern..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        // joinPoint안에 어떤 메소드가 들어갔는지 이름을 얻을 수 있음
        System.out.println("START: " + joinPoint.toString());
        try {
            // proceed 하면 다음 메소드로 진행됨
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }

    }
}
