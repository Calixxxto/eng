package commons;


import org.apache.commons.lang3.time.FastDateFormat;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.*;

public class JsonUtils {

    public static final FastDateFormat DATE_TIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    public static class SafeDateFormat extends DateFormat {

        private static final long serialVersionUID = 2465816231902990482L;

        public SafeDateFormat() {
            this.calendar = Calendar.getInstance();
            this.numberFormat = NumberFormat.getInstance();
        }

        @Override
        public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
            return DATE_TIME_FORMAT.format(date, toAppendTo, fieldPosition);
        }

        @Override
        public Date parse(String source, ParsePosition pos) {
            return DATE_TIME_FORMAT.parse(source, pos);
        }

    }

    /**
     * Wraps name=value pair into Map
     * @param name
     * @param value
     * @return
     */
	
	public static Map<String,Object> createSingleEntryMap(String name, Object value) {
		Map<String, Object> result = new HashMap<String,Object>();
		result.put(name, value);
		return result; 
	}

    /**
     * Wraps collection of name=value pairs into Map
     * @param names
     * @param values
     * @return
     */
	public static Map<String,Object> createEntryMap(String[] names, Object[] values) {
		Map<String, Object> result = new HashMap<String,Object>();		
		for (int i=0; i<names.length; i++) {
			result.put(names[i], values.length > i ? values[i] : null);
		}
		return result; 
	}
}
