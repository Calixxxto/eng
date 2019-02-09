package commons;

import data.domain.user.User;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AuditUtils {
	
	static final Logger logger = LoggerFactory.getLogger("audit");
	
	/**
	 * Logs formatted message preceding it with current user login. Parameters are serialized to strings using Apache Commons ReflectionToStringBuilder.
	 * @param message
	 * @param args
	 */
	public static void logUserAction(String message, Object... args) {
		User user = SecurityUtils.getLoggedinUser();
		String userName = user == null ? "UNKNOWN" : user.getLogin();
		List<String> argsStr = new ArrayList<String>();
		argsStr.add(userName);
		if (args != null && args.length > 0) {
			for (Object arg: args ) {
				argsStr.add(arg instanceof String ? (String)arg : ReflectionToStringBuilder.toString(arg, ToStringStyle.MULTI_LINE_STYLE));
			}
		}
		logger.info("User: {} \n" + message, argsStr.toArray());
	}
}
