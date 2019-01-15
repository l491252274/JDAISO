package org.hibernate.dialect;

import java.sql.Types;

import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;

/**
 * @author Suchak.Jani w/ modifications by Eric Klimas This code is blatently
 *         ripped off from the Hibernate forums discussion on MS Access and
 *         Hibernate found here: <a href=
 *         "http://forums.hibernate.org/viewtopic.php?p=2178009&sid=876222db25ab13214a3729dbe1e494b6"
 *         > http://forums.hibernate.org/viewtopic.php?p=2178009&sid=876222d
 *         b25ab13214a3729dbe1e494b6</a>
 *         <p>
 *         The code has been modified by Eric to:
 *         <ol>
 *         <li>Work with the current version of hibernate since Dialect.NO_BATCH
 *         no longer exists</li>
 *         <li>Be placed in a different package
 *         (org.hibernate.unsupported.dialect) so nobody gets the idea that this
 *         is actively supported by hibernate or anybody for that matter. <b>USE
 *         AT YOUR OWN RISK...</b></li>
 *         </ol>
 */
public class MSAccessDialect extends Dialect
{

	public MSAccessDialect()
	{
		super();
		registerColumnType(Types.BIT, "BIT");
		registerColumnType(Types.BIGINT, "INTEGER");
		registerColumnType(Types.SMALLINT, "SMALLINT");
		registerColumnType(Types.TINYINT, "BYTE");
		registerColumnType(Types.INTEGER, "INTEGER");
		registerColumnType(Types.CHAR, "VARCHAR(1)");
		registerColumnType(Types.VARCHAR, "VARCHAR($l)");
		registerColumnType(Types.FLOAT, "DOUBLE");
		registerColumnType(Types.DOUBLE, "DOUBLE");
		registerColumnType(Types.DATE, "DATETIME");
		registerColumnType(Types.TIME, "DATETIME");
		registerColumnType(Types.TIMESTAMP, "DATETIME");
		registerColumnType(Types.VARBINARY, "VARBINARY($l)");
		registerColumnType(Types.NUMERIC, "NUMERIC");

		getDefaultProperties().setProperty(Environment.STATEMENT_BATCH_SIZE,
				"0");
	}

	public String getIdentityColumnString()
	{
		// return " counter ";
		return "not null auto_number";
	}

	public String getIdentitySelectString()
	{
		return "select @@IDENTITY";
	}

	/**
	 * Returns for update syntax for access, which is non-existant, so I *think*
	 * we return an empty string...
	 * 
	 * @return String an beautifully constructed empty string...
	 */
	public String getForUpdateString()
	{
		return "";
	}
}
