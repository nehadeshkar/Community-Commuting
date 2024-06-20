insert into trip 
(trip_Id_trip, creator_User_Id, vehicle_Id,
 ride_Date, ride_Time, ride_Status, no_Of_Seat, seats_filled, from_Loc, to_Loc) 
 values
 ('0230PUNA', 'RPDE03', 'NA30PU02', '2024-03-20','02:30:00', 'Planned',10, 5,  'PUNE', 'NAGPUR'),
 ('0930MUPU', 'RPDE01', 'PU30MU09', '2024-04-20', '09:30:00', 'Planned', 6, 1, 'MUMBAI', 'Pune'),
 ('0930MUGW', 'RPDE02', 'GW30MU10', '2024-04-20', '10:30:00', 'Planned', 6, 1, 'MUMBAI', 'GWALIOR');
 insert into booking
 (trip_Id_trip, number_Of_Seat, seeker_Id, status)
 values
 ('0230PUNA', 3, 'RESH00', 'Booked'),
 ('0930MUPU', 3, 'RESH01', 'Booked'),
 ('0930MUGW', 4, 'RESH02', 'Completed');
 
 insert into bill_Master 
 (bill_id, total_bill)
 values
 ('B1', 183.3), 
 ('B2', 200);