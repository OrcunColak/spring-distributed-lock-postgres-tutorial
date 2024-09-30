# Read Me

The original idea is from  
https://erkanyasun.medium.com/postgresql-advisory-locks-a-robust-alternative-to-redis-locks-87ef05d9d2d9

# Exclusive and Shared Lock

From the exclusiveness point of view advisory locks can be shared or exclusive. Shared locks (initiated by the
pg_advisory_lock_shared function) don’t conflict with other shared locks, they conflict only with exclusive locks. At
the same time exclusive locks (initiated by the pg_advisory_lock function) conflict with any other lock with the same
key, both exclusive and shared.

# Session-level locks and Transactions-level locks

See https://blog.devgenius.io/what-are-postgres-advisory-locks-and-their-use-cases-71ace601e06b

These locks can be obtained on two different levels: on session and transaction levels.

Session-level locks (pg_advisory_lock function) do not depend on current transactions and are held until they are
unlocked manually (with pg_advisory_unlock function) or at the end of the session.

Transaction-level logs (pg_advisory_xact_log function) behave more familiar for those who use Postgres row locks — they
live until the end of
the transaction and don’t require manual unlocking.

And what does the “session” mean in this context? In Postgres the session is the same as a database connection. As
connections can be shared between several processes, there is a possible issue with session-level locks. When a process
that locked the resource dies before it called unlock, the session is not closed, because the connection is used by
other processes. It means that the lock will be held until the connection is closed, which can take a long time.

# Behavior control

See https://blog.devgenius.io/what-are-postgres-advisory-locks-and-their-use-cases-71ace601e06b

Another feature of Postgres advisory locks is a behavior control on conflict. It allows a different behavior when the
lock for the given identifier is already held.
It can wait until the resource is unlocked and available (using pg_advisory_lock function)
or
just return false and continue execution (using pg_try_advisory_lock, pg_try_advisory_lock_shared,
pg_try_advisory_xact_log functions).

