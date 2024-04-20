package rf.aleksper.homework.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class ElapsedTime {

//    @Around("execution(* rf.aleksper.homework.services.*.*(..))")
//    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            long start = System.currentTimeMillis();
//            Object result = joinPoint.proceed();
//            long elapsedTime = System.currentTimeMillis() - start;
//                        StringBuffer sb = new StringBuffer();
//            sb.append(joinPoint.getTarget().getClass().getSimpleName());
//            sb.append(" - ");
//            sb.append(joinPoint.getSignature().getName());
//            sb.append(" # ");
//            sb.append(elapsedTime);
//            sb.append(" ms");
//            log.info(sb.toString());
//            return result;
//        } catch (Exception e) {
//            throw e;
//        }
//    }

    @Pointcut("@annotation(rf.aleksper.homework.aop.Timer)")
    public void timerMethods() {
    }

    @Pointcut("within(@rf.aleksper.homework.aop.Timer *)")
    public void timerClass() {
    }


    @Around("timerMethods() || timerClass()")
    public Object elapsedTime(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long start = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            String sb = joinPoint.getTarget().getClass().getSimpleName() +
                    " - " +
                    joinPoint.getSignature().getName() +
                    " # " +
                    elapsedTime +
                    " ms";
            log.info(sb);
            return result;
        } catch (Exception e) {
            throw e;
        }

    }
}
