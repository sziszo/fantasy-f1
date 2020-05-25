package com.aklysoft.fantasyf1.service.core.orm;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Locale;

public final class SnakeCaseNamingStrategy implements PhysicalNamingStrategy {

	@Override
	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return name;
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return name;
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		if (name == null) return null;
		var tableName = name.getText();
		if (!tableName.endsWith("s")) {
			tableName = tableName.concat("s");
		}
		return Identifier.toIdentifier(tableName, name.isQuoted());
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return toSnakeCase(name);
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return toSnakeCase(name);
	}

	private static Identifier toSnakeCase(Identifier name) {
		if (name == null) return null;
		String snakeCase = toSnakeCase(name.getText());
		return Identifier.toIdentifier(snakeCase, name.isQuoted());
	}


	private static String toSnakeCase(String text) {
		var buf = new StringBuilder(text);
		for (int i = 1; i < buf.length() - 1; i++) {
			if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i))
					&& Character.isLowerCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toLowerCase(Locale.ROOT);
	}
}
