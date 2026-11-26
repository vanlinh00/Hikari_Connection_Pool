# HikariCP Connection Pool Guide

HikariCP is a fast, lightweight JDBC connection pool for Java. Instead of opening and closing a brand-new database connection every time your app runs a query—which is slow and uses lots of resources—HikariCP keeps a ready-made group ("pool") of open connections that your app can borrow and return.

## Key Settings

- **maximum-pool-size (Max taxis allowed at the stand):**
  Suppose this is 10. Even if 50 people arrive at once, there can never be more than 10 taxis in this stand. A bigger number is not always better—too many taxis can crowd the database and slow everything down.

- **minimum-idle (Minimum waiting taxis when things are quiet):**
  Suppose this is 2. Even late at night when few passengers arrive, HikariCP tries to keep at least 2 ready-to-go taxis waiting so the next passenger does not wait for a driver to start up.

- **connection-timeout (How long a passenger waits for a taxi):**
  Suppose this is 20000 milliseconds (20 seconds). All 10 taxis are busy carrying passengers, and a new passenger steps up. If no taxi becomes free within 20 seconds, the system gives up and throws a timeout error.

- **idle-timeout (How long an extra taxi sits empty before leaving)-the empty connections leave (close):**
  Suppose maximum-pool-size is 10 and minimum-idle is 2. During a rush, 8 taxis were out, and now things are quiet again. Any extra taxis above 2 that sit empty for longer than idle-timeout can leave so they do not waste resources.

- **max-lifetime (Maximum total lifespan of a taxi before replacement):**
  Even if a taxi is busy and useful, it cannot stay in service forever. After max-lifetime (for example, 30 minutes), HikariCP retires that connection and opens a fresh one. This prevents stale connections or network/firewall drops from causing surprise errors.