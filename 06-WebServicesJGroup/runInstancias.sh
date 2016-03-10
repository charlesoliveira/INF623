#su - postgres
#mkdir /var/lib/postgresql/{instancia1,instancia2,instancia3}
#chown -R postgres:postgres /usr/local/postgresql
#chown -R postgres:postgres /usr/local/postgresql
#/usr/lib/postgresql/9.3/bin/initdb -D /var/lib/postgresql/instancia1
#/usr/lib/postgresql/9.3/bin/initdb -D /var/lib/postgresql/instancia2
#/usr/lib/postgresql/9.3/bin/initdb -D /var/lib/postgresql/instancia3

 /usr/lib/postgresql/9.3/bin/postgres -D /var/lib/postgresql/instancia1
 /usr/lib/postgresql/9.3/bin/postgres -D /var/lib/postgresql/instancia2
 /usr/lib/postgresql/9.3/bin/postgres -D /var/lib/postgresql/instancia3
