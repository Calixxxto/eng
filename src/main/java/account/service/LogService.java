package account.service;

import com.google.common.base.Optional;
import commons.Const;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.lang3.SystemUtils.FILE_SEPARATOR;

@Service
public class LogService {

    @Value("${CATALINA_HOME}")
    private String tomcatPath;
    @Value("${log.logger.login.name}")
    private String loggerLoginName;
    @Value("${log.extension.base}")
    private String logExtension;
    @Value("${log.extension.archive}")
    private String logZipExtension;
    @Value("${log.path}")
    private String logsPath;

    /**
     *
     * @param date log date
     * @return optional.of log file if exists, optional.absent otherwise
     * @throws IOException
     */
    public Optional<File> getLogFile(Date date) throws IOException {
        String logFileName = generateLogFileName(date);
        if (logFileName != null) {
            File file = new File(logFileName);
            if (Files.exists(file.toPath())) {
                return Optional.of(file);
            }
        }
        return Optional.absent();
    }

    /**
     * @param date log date
     * @return true if log file exists, false otherwise
     */
    public boolean exists(Date date) {
        String logFileName = generateLogFileName(date);
        return logFileName != null && Files.exists(Paths.get(logFileName));
    }

    private String generateLogFileName(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Const.Log.DATE_FORMAT);
        String dateFormatted = dateFormat.format(date);
        String logName = loggerLoginName;
        String logExt = logExtension;
        if (!DateUtils.isSameDay(date, new Date())) {
            logName = logName + "." + dateFormatted;
            logExt = logExtension + "." + logZipExtension;
        }
        return StringUtils.join(tomcatPath, FILE_SEPARATOR, logsPath, FILE_SEPARATOR, logName, ".", logExt);
    }
}
