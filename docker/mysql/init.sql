-- Grant root access from all hosts
GRANT
ALL
PRIVILEGES
ON
*
.
*
TO
'root'@'%' WITH GRANT OPTION;
FLUSH
PRIVILEGES;
