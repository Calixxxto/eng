package account.aop;

import commons.AuditUtils;
import commons.DataUtils;
import data.common.AuditedRecord;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class RepositoryAuditedRecordAspect {
	final Logger logger = LoggerFactory.getLogger("audit"); // "audit" is to be defined in log configuration

	/**
	 * For delete methods, log argument(s) being passed to method 
	 * @param jp
	 */
	@Before("execution(* account.repository..*.delete(..))")
	public void beforeDelete(JoinPoint jp) {
		Class<? extends AuditedRecord> auditedRecordType = getAuditedRecordTypeParameter(jp.getTarget());
		if (auditedRecordType == null) {
			return; 
		}
		Object[] args = jp.getArgs();
		String operation = jp.getSignature().getName();
		if (args != null && args.length == 1) {
			logDataOperation(auditedRecordType.getSimpleName(), operation,  args[0], true);
		}
	}
	
	/**
	 * For save* repository methods against AuditedRecord descendants, logs operation name, argument and result of save call 
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* account.repository..*.save*(..))")
	public Object aroundSave(ProceedingJoinPoint jp) throws Throwable {
		Object target = jp.getTarget();
		Object args[] = jp.getArgs();
		Object arg = (args == null || args.length !=1) ? "[None or multiple objects]" : args[0];   
		Object result = null;
		Class<? extends AuditedRecord> auditedRecordType = null;
		String operation = null ; 
		try {
			// get target object and check that it is a repository for auditable type of entities: 
			auditedRecordType = getAuditedRecordTypeParameter(target);
			// logging before operation: 
			if (auditedRecordType != null) {
				operation = jp.getSignature().getName();
				logDataOperation(auditedRecordType.getSimpleName(), operation, arg, true);
			}
		} finally {
			// target method invocation : 
			result = jp.proceed();
			
			// logging after operation: 
			try {		
				if (auditedRecordType != null && result != null) {
					logDataOperation(auditedRecordType.getSimpleName(), operation, result, false);
				}
			} catch (Throwable t) {
				logger.error("Audit logging failed", t);
			}
		}		
		return result ; 
	}	
	
	@SuppressWarnings({"unchecked" })
	private Class<? extends AuditedRecord> getAuditedRecordTypeParameter(Object target) {
		Class<?> entityClass = DataUtils.getRepositoryTypeParameter(target);
		if (entityClass != null && AuditedRecord.class.isAssignableFrom(entityClass)) {
			return (Class<? extends AuditedRecord>) entityClass;
		}
		return null; 		
	}
	
	
	/**
	 * 
	 * @param operation
	 * @param record
	 */
	private void logDataOperation( String auditedRecordTypeName, String operation, Object data, boolean before) {		
		AuditUtils.logUserAction("{} {} \nRecord type: {}\nData :\n{}\n--------",
				before ? "Before" : "After",	operation, 
						auditedRecordTypeName, data);
	}

}
