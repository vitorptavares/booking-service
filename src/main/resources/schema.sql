
create table property(
id BIGINT AUTO_INCREMENT NOT NULL ,
address VARCHAR(255) NOT NULL,
city VARCHAR(25) NOT NULL,
state VARCHAR(25) NOT NULL,
zipcode VARCHAR(25),
country VARCHAR (25) NOT NULL,
PRIMARY KEY (id));


CREATE TABLE guest(
id BIGINT AUTO_INCREMENT NOT NULL ,
name VARCHAR(50) NOT NULL,
phone VARCHAR(15) NOT NULL,
address VARCHAR(255) NOT NULL,
city VARCHAR(25) NOT NULL,
state VARCHAR(25) NOT NULL,
zipcode VARCHAR(25),
country VARCHAR(25) NOT NULL,
email VARCHAR(254) NOT NULL,
PRIMARY KEY (id));


create table booking(
id BIGINT AUTO_INCREMENT NOT NULL ,
arrival_date DATE NOT NULL,
departure_date DATE NOT NULL,
property_id BIGINT,
guest_id BIGINT,
status VARCHAR(10) NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (property_id) REFERENCES property(id),
FOREIGN KEY (guest_id) REFERENCES guest(id));

create table block(
id BIGINT AUTO_INCREMENT NOT NULL ,
status VARCHAR(10) NOT NULL,
role_block VARCHAR(10) NOT NULL,
booking_id BIGINT,
PRIMARY KEY (id),
FOREIGN KEY (booking_id) REFERENCES booking(id));


