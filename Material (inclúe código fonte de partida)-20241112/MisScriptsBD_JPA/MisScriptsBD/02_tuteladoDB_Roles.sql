DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'hib') THEN

      RAISE NOTICE 'Role "hib" already exists. Skipping.';
   ELSE
      CREATE ROLE hib;
      ALTER ROLE hib WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'hib123';
   END IF;
END
$do$;