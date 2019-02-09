package commons;

import org.springframework.core.GenericTypeResolver;
import org.springframework.data.repository.Repository;

public class DataUtils {
	
	/**
	 * check that target object is Spring Data repository that operates on AuditedRecord instance:
	 * @param target
	 * @return
	 */
	public static Class<?> getRepositoryTypeParameter(Object target) {
		if (!(target instanceof Repository)) {
			return null;
		}
		Class<?> repositoryTypeParams[] = GenericTypeResolver.resolveTypeArguments(target.getClass(), Repository.class);
		if (repositoryTypeParams == null || repositoryTypeParams.length == 0) {
			return null; 
		}
		return  repositoryTypeParams[0];
	}
}
