package data.hibernate;

import org.hibernate.dialect.PostgreSQL9Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class PostgreSqlDialectCustom extends PostgreSQL9Dialect {
	
	public PostgreSqlDialectCustom() {
		super();
		registerFunction("string_agg", new SQLFunctionTemplate( StandardBasicTypes.STRING, "string_agg(distinct(cast(?1 as text)), ?2)"));
		registerFunction("extract", new SQLFunctionTemplate( StandardBasicTypes.INTEGER, "extract(?1 from ?2)"));
		registerFunction("extract_month", new SQLFunctionTemplate( StandardBasicTypes.INTEGER, "extract(month from ?1)"));
		registerFunction("extract_year", new SQLFunctionTemplate( StandardBasicTypes.INTEGER, "extract(year from ?1)"));
		registerFunction("days_until", new SQLFunctionTemplate( StandardBasicTypes.INTEGER, "extract(day from ?1 - now())::int"));
		registerFunction("date_trunc", new SQLFunctionTemplate( StandardBasicTypes.DATE, "date_trunc(?1, ?2)"));
		registerFunction("trim_time", new SQLFunctionTemplate( StandardBasicTypes.DATE, "date_trunc('day', ?1)"));
	}

}
