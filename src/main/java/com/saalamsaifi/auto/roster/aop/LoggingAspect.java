package com.saalamsaifi.auto.roster.aop;

import com.saalamsaifi.auto.roster.model.LogEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
  private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

  // @Before(value = "execution(* com.saalamsaifi.auto.roster..*(..))")
  public void before(JoinPoint point) {
    LogEvent event = new LogEvent();

    event.setType(point.getSignature().getDeclaringTypeName());
    event.setName(point.getSignature().getName());
    event.setModifier(point.getSignature().getModifiers());
    event.setArgs(point.getArgs());

    logger.debug("Executing {}", event);
  }
}
