SET @house1 = UNHEX(REPLACE(UUID(), '-', ''));
SET @house2 = UNHEX(REPLACE(UUID(), '-', ''));
SET @house3 = UNHEX(REPLACE(UUID(), '-', ''));
SET @house4 = UNHEX(REPLACE(UUID(), '-', ''));
SET @house5 = UNHEX(REPLACE(UUID(), '-', ''));
SET @house6 = UNHEX(REPLACE(UUID(), '-', ''));
SET @house7 = UNHEX(REPLACE(UUID(), '-', ''));
SET @house8 = UNHEX(REPLACE(UUID(), '-', ''));
SET @house9 = UNHEX(REPLACE(UUID(), '-', ''));
SET @house10 = UNHEX(REPLACE(UUID(), '-', ''));

DELETE FROM house_user;
DELETE FROM house_devices;
DELETE FROM device;
DELETE FROM room;
DELETE FROM house;


INSERT INTO house (
    house_id, house_name, address, city, state, postal_code, admin_id, created_at
) VALUES
(@house1, 'Green Villa', '123 Green Street', 'Springfield', 'Illinois', '62701', 'venkat', NOW()),
(@house2, 'Blue Cottage', '456 Blue Avenue', 'Metropolis', 'New York', '10001', 'alice', NOW()),
(@house3, 'Red Mansion', '789 Red Boulevard', 'Gotham', 'New Jersey', '07001', 'bob', NOW()),
(@house4, 'Sunny House', '321 Sunshine Road', 'Star City', 'California', '90001', 'charlie', NOW()),
(@house5, 'Moonlight Manor', '654 Moonlight Lane', 'Central City', 'Ohio', '43001', 'david', NOW()),
(@house6, 'River View', '987 River Street', 'Springfield', 'Illinois', '62702', 'venkat', NOW()),
(@house7, 'Hilltop Home', '147 Hilltop Drive', 'Metropolis', 'New York', '10002', 'alice', NOW()),
(@house8, 'Oak Residence', '258 Oak Road', 'Gotham', 'New Jersey', '07002', 'bob', NOW()),
(@house9, 'Pine Villa', '369 Pine Avenue', 'Star City', 'California', '90002', 'charlie', NOW()),
(@house10, 'Maple House', '159 Maple Street', 'Central City', 'Ohio', '43002', 'david', NOW());


INSERT INTO house_user (house_user_id, house_id, user_id, created_at) VALUES
(UNHEX(REPLACE(UUID(), '-', '')), @house1, 'venkat',  NOW()),
(UNHEX(REPLACE(UUID(), '-', '')), @house1, 'alice',   NOW()),
(UNHEX(REPLACE(UUID(), '-', '')), @house2, 'bob',     NOW()),
(UNHEX(REPLACE(UUID(), '-', '')), @house2, 'charlie', NOW()),
(UNHEX(REPLACE(UUID(), '-', '')), @house3, 'david',   NOW()),
(UNHEX(REPLACE(UUID(), '-', '')), @house3, 'venkat',  NOW());

