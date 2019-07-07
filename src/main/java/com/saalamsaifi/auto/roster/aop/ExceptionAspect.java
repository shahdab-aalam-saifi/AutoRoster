package com.saalamsaifi.auto.roster.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class ExceptionAspect {

	@AfterThrowing(pointcut = "exceution(* com.saalamsaifi.auto.roster.*(..))", throwing = "ex")
	public void afterThrowingException(JoinPoint point, Throwable ex) {
		log.debug("sourceLocation: {} exception: {}", point.getSourceLocation(), ex.getMessage());
	}

}
