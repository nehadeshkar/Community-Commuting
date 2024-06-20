create table trip 
	(trip_Id_trip varchar(30) , 
	creator_User_Id varchar (30),
	vehicle_Id varchar(30),
	ride_Date date,
	ride_Time time,
	ride_Status varchar (30),
	no_Of_Seat integer, 
	seats_filled integer,
	from_Loc varchar (30),
	to_Loc varchar(30),
	primary key (trip_Id_trip));
	

create table booking (
	booking_Id_booking integer NOT NULL AUTO_INCREMENT,
	trip_Id_trip varchar(30),
	number_Of_Seat integer,
	seeker_Id varchar(30),
	status varchar(30),
	primary key (booking_Id_booking),
	foreign key (trip_id_trip) references trip (trip_id_trip));
	
create table bill_Master(
	bill_id varchar(30) primary key,
	total_bill float);
	
create sequence "BILLSEQ"	
MINVALUE 1
MAXVALUE 9999999999
INCREMENT BY 1
START WITH 1
NOCACHE
NOCYCLE;