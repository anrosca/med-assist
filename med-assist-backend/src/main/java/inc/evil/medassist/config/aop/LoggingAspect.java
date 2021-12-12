package inc.evil.medassist.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Pointcut(value = "within(@org.springframework.stereotype.Service *)")
    public void serviceLayer() {
    }

    @Pointcut(value = "within(@org.springframework.stereotype.Repository *)")
    public void repoLayer() {
    }

    @Pointcut(value = "within(@org.springframework.stereotype.Controller *)")
    public void webLayer() {
    }

    @Around(value = "serviceLayer() || repoLayer() || webLayer() ")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("before :: {} ", pjp.toLongString());
        final Object returnValue = pjp.proceed();
        log.debug("after :: {} = {}",  pjp.toLongString(), returnValue);
        return returnValue;
    }
}
