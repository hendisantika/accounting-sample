-- Grant root access from all hosts
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;

-- Grant full privileges to yu71 user on accounting_db
GRANT ALL PRIVILEGES ON accounting_db.* TO 'yu71'@'%';

FLUSH PRIVILEGES;
