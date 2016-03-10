mkdir /var/lib/postgresql/9.3/{instancia1,instancia2,instancia3}
sudo chown -R postgres:postgres /var/lib/postgresql/9.3/{instancia1,instancia2,instancia3}

sudo -u postgres /usr/lib/postgresql/9.3/bin/initdb -D /var/lib/postgresql/9.3/instancia1
sudo -u postgres /usr/lib/postgresql/9.3/bin/initdb -D /var/lib/postgresql/9.3/instancia2
sudo -u postgres /usr/lib/postgresql/9.3/bin/initdb -D /var/lib/postgresql/9.3/instancia3
#MUDAR A PORTA EM CADA INSTANCIA
sudo nano /var/lib/postgresql/9.3/instancia1/postgresql.conf
sudo nano /var/lib/postgresql/9.3/instancia2/postgresql.conf
sudo nano /var/lib/postgresql/9.3/instancia3/postgresql.conf

sudo -u postgres /usr/lib/postgresql/9.3/bin/postgres -D /var/lib/postgresql/9.3/instancia1
sudo -u postgres /usr/lib/postgresql/9.3/bin/postgres -D /var/lib/postgresql/9.3/instancia2
sudo -u postgres /usr/lib/postgresql/9.3/bin/postgres -D /var/lib/postgresql/9.3/instancia3


 sudo -u postgres psql -c "ALTER USER postgres WITH PASSWORD 'postgres';"
