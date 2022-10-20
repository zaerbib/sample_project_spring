package com.tuto.rate.aop;

import com.tuto.rate.annotation.ToUpper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.stream.IntStream;

@Aspect
@Component
public class CurrencyCodeAudit {
    @Pointcut("execution(* com.tuto.rate.service.*Service.*(.., @com.tuto.rate.annotation.ToUpper (*),..))")
    public void methodPointcut() {}

    public Object codeAudit(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        Parameter[]  parameters = ((MethodSignature)pjp.getSignature()).getMethod().getParameters();

        return pjp.proceed(IntStream.range(0, args.length)
                .mapToObj(index -> (parameters[index].isAnnotationPresent(ToUpper.class)) ? (new String(args[index].toString().toUpperCase())) : (args[index]) )
                .toArray());
    }
}
